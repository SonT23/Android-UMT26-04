package vn.edu.umt.tour.ui.khach.khampha

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch
import vn.edu.umt.tour.R
import vn.edu.umt.tour.data.local.AppDatabase
import vn.edu.umt.tour.data.local.LichSuTim
import vn.edu.umt.tour.data.local.LichSuTimDao

// A02 Tìm tour (B2): gõ để tìm theo tên tour, điểm đến; lọc và sắp xếp qua bảng Bộ lọc.
// B4: gợi ý khi gõ (AutoCompleteTextView) và lịch sử tìm lưu trong Room (bảng lich_su_tim).
// Hiện tìm trên dữ liệu mẫu; tuần 9 đổi taoTatCaTourMau() thành đọc Firestore, phần lọc giữ nguyên.
class TimTourFragment : Fragment() {

    // Hai danh sách và bộ lọc nằm trong Fragment nên còn nguyên khi mở Bộ lọc hay A03 rồi quay lại
    private val tatCa = taoTatCaTourMau()        // mọi tour
    private val tours = ArrayList<TourMau>()     // các tour đang hiện sau khi lọc
    private var boLoc = BoLoc()

    private lateinit var adapter: TourAdapter
    private lateinit var timET: AutoCompleteTextView
    private lateinit var goiYAdapter: ArrayAdapter<String>
    private lateinit var lichSuDao: LichSuTimDao
    private lateinit var lichSuHSV: HorizontalScrollView
    private lateinit var lichSuCG: ChipGroup
    private lateinit var soKetQuaTV: TextView
    private lateinit var khongCoTV: TextView
    private lateinit var sapXepTV: TextView
    private lateinit var boLocChip: Chip
    private lateinit var diemDenChip: Chip
    private lateinit var thangChip: Chip
    private lateinit var soNgayChip: Chip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Xoay máy thì Fragment bị tạo lại: lấy lại bộ lọc đã cất trong onSaveInstanceState
        val daCat = savedInstanceState?.getBundle(BO_LOC)
        if (daCat != null) {
            boLoc = BoLoc.tuBundle(daCat)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(BO_LOC, boLoc.thanhBundle())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a02_tim_tour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timET = view.findViewById(R.id.timET)
        soKetQuaTV = view.findViewById(R.id.soKetQuaTV)
        khongCoTV = view.findViewById(R.id.khongCoTV)
        sapXepTV = view.findViewById(R.id.sapXepTV)
        boLocChip = view.findViewById(R.id.boLocChip)
        diemDenChip = view.findViewById(R.id.diemDenChip)
        thangChip = view.findViewById(R.id.thangChip)
        soNgayChip = view.findViewById(R.id.soNgayChip)
        lichSuHSV = view.findViewById(R.id.lichSuHSV)
        lichSuCG = view.findViewById(R.id.lichSuCG)

        // Danh sách kết quả
        adapter = TourAdapter(tours)
        val tourRV = view.findViewById<RecyclerView>(R.id.tourRV)
        tourRV.layoutManager = LinearLayoutManager(requireContext())
        tourRV.adapter = adapter
        locLai()

        // Bấm vào một tour: mở A03 Chi tiết tour, truyền mã tour
        adapter.onTourClick = { tour ->
            moManHinhCon(ChiTietTourFragment.newInstance(tour.ma))
        }
        // B5: tô tim theo dữ liệu đã lưu trong Room, bấm tim thì lưu hoặc xóa trong Room
        val dao = AppDatabase.layDatabase(requireContext()).yeuThichDao()
        viewLifecycleOwner.lifecycleScope.launch {
            danhDauYeuThich(dao, tatCa)
            adapter.notifyDataSetChanged()
        }
        adapter.onYeuThichClick = { viTri ->
            val tour = tours[viTri]
            tour.yeuThich = !tour.yeuThich
            adapter.notifyItemChanged(viTri)
            viewLifecycleOwner.lifecycleScope.launch { luuYeuThich(dao, tour) }
        }

        // Gõ chữ đến đâu lọc đến đó (TextWatcher, bài A08); ô trống thì hiện lại lịch sử tìm
        timET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                locLai()
                anHienLichSu()
            }
        })

        // ----- B4: gợi ý khi gõ và lịch sử tìm -----
        // Danh sách gợi ý của AutoCompleteTextView: lịch sử tìm + tên điểm đến + tên tour
        goiYAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, ArrayList<String>())
        timET.setAdapter(goiYAdapter)
        lichSuDao = AppDatabase.layDatabase(requireContext()).lichSuTimDao()
        napLichSu()

        // Bấm nút tìm trên bàn phím ảo (hoặc phím Enter của bàn phím máy tính khi chạy máy ảo):
        // lưu từ khóa vào lịch sử
        timET.setOnEditorActionListener { _, actionId, event ->
            val bamEnter = event != null && event.keyCode == KeyEvent.KEYCODE_ENTER &&
                event.action == KeyEvent.ACTION_DOWN
            if (actionId == EditorInfo.IME_ACTION_SEARCH || bamEnter) {
                luuTuKhoa(timET.text.toString())
                anBanPhim()
                true
            } else {
                false
            }
        }
        // Chọn một dòng gợi ý: ô tìm đã tự điền dòng đó, chỉ cần lưu vào lịch sử
        timET.setOnItemClickListener { _, _, _, _ ->
            luuTuKhoa(timET.text.toString())
            anBanPhim()
        }

        // Nút X xóa chữ trong ô tìm (TextWatcher tự lọc lại)
        view.findViewById<ImageView>(R.id.xoaIV).setOnClickListener {
            timET.setText("")
        }

        // Bấm chip nào hay chữ sắp xếp cũng mở bảng Bộ lọc, gửi kèm bộ lọc và từ khóa đang dùng
        val moBoLoc = View.OnClickListener {
            moManHinhCon(BoLocFragment.newInstance(boLoc, timET.text.toString()))
        }
        boLocChip.setOnClickListener(moBoLoc)
        diemDenChip.setOnClickListener(moBoLoc)
        thangChip.setOnClickListener(moBoLoc)
        soNgayChip.setOnClickListener(moBoLoc)
        sapXepTV.setOnClickListener(moBoLoc)

        // Nhận bộ lọc mới khi bảng Bộ lọc bấm "Xem N tour"
        setFragmentResultListener(BoLocFragment.KET_QUA) { _, ketQua ->
            boLoc = BoLoc.tuBundle(ketQua)
            locLai()
        }
    }

    // B4: đọc lịch sử tìm từ Room, vẽ hàng chip lịch sử và làm mới danh sách gợi ý
    private fun napLichSu() {
        viewLifecycleOwner.lifecycleScope.launch {
            val lichSu = lichSuDao.layGanDay()

            // Mỗi từ khóa là một chip tạo từ item_a02_lich_su.xml
            lichSuCG.removeAllViews()
            for (tuKhoa in lichSu) {
                val chip = layoutInflater.inflate(R.layout.item_a02_lich_su, lichSuCG, false) as Chip
                chip.text = tuKhoa
                // Bấm chip: tìm lại từ này. setText(…, false) để không bật danh sách gợi ý.
                chip.setOnClickListener {
                    timET.setText(tuKhoa, false)
                    timET.setSelection(tuKhoa.length)
                    luuTuKhoa(tuKhoa)
                    anBanPhim()
                }
                // Bấm dấu x: xóa từ này khỏi lịch sử
                chip.setOnCloseIconClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        lichSuDao.xoa(tuKhoa)
                        napLichSu()
                    }
                }
                lichSuCG.addView(chip)
            }
            anHienLichSu()

            // Gợi ý: lịch sử trước, rồi tên điểm đến, tên tour; bỏ dòng trùng
            val goiY = ArrayList<String>()
            goiY.addAll(lichSu)
            goiY.addAll(taoDiemDenMau())
            for (tour in tatCa) {
                goiY.add(tour.ten)
            }
            goiYAdapter.clear()
            goiYAdapter.addAll(goiY.distinct())
        }
    }

    // Lưu từ khóa vào bảng lich_su_tim (bỏ qua ô trống), giữ 10 từ mới nhất, rồi vẽ lại
    private fun luuTuKhoa(chu: String) {
        val tuKhoa = chu.trim()
        if (tuKhoa == "") return
        viewLifecycleOwner.lifecycleScope.launch {
            lichSuDao.them(LichSuTim(tuKhoa, System.currentTimeMillis()))
            lichSuDao.chiGiu10()
            napLichSu()
        }
    }

    // Hàng lịch sử chỉ hiện khi ô tìm trống và đã có từ khóa nào đó
    private fun anHienLichSu() {
        if (timET.text.isEmpty() && lichSuCG.childCount > 0) {
            lichSuHSV.visibility = View.VISIBLE
        } else {
            lichSuHSV.visibility = View.GONE
        }
    }

    // Ẩn bàn phím sau khi tìm để thấy hết danh sách kết quả
    private fun anBanPhim() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(timET.windowToken, 0)
        timET.clearFocus()
    }

    // Lọc lại theo từ khóa và bộ lọc, vẽ lại danh sách, đổi chữ trên các chip
    private fun locLai() {
        tours.clear()
        tours.addAll(locTour(tatCa, timET.text.toString(), boLoc))
        adapter.notifyDataSetChanged()

        soKetQuaTV.text = getString(R.string.a02_so_tour, tours.size)
        khongCoTV.visibility = if (tours.isEmpty()) View.VISIBLE else View.GONE

        val soMuc = boLoc.soMucDangLoc()
        boLocChip.text = if (soMuc > 0) getString(R.string.a02_bo_loc_so, soMuc) else getString(R.string.a02_bo_loc)
        diemDenChip.text = if (boLoc.diemDen != "") boLoc.diemDen else getString(R.string.a02_chip_diem_den)
        thangChip.text = getString(
            when (boLoc.thang) {
                11 -> R.string.a02_thang_11
                12 -> R.string.a02_thang_12
                1 -> R.string.a02_thang_1
                2 -> R.string.a02_thang_2
                else -> R.string.a02_chip_thang
            }
        )
        soNgayChip.text = getString(
            when (boLoc.soNgay) {
                1 -> R.string.a02_1_2_ngay
                3 -> R.string.a02_3_4_ngay
                5 -> R.string.a02_tu_5_ngay
                else -> R.string.a02_chip_so_ngay
            }
        )
        sapXepTV.text = getString(
            when (boLoc.sapXep) {
                BoLoc.GIA_GIAM -> R.string.a02_gia_giam_dan
                BoLoc.NGAY_GAN_NHAT -> R.string.a02_ngay_gan_nhat
                else -> R.string.a02_gia_tang_dan
            }
        )
    }

    companion object {
        private const val BO_LOC = "bo_loc"
    }
}
