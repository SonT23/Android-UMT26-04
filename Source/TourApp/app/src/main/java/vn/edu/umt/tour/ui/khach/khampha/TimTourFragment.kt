package vn.edu.umt.tour.ui.khach.khampha

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import vn.edu.umt.tour.R
import vn.edu.umt.tour.data.local.AppDatabase

// A02 Tìm tour (B2): gõ để tìm theo tên tour, điểm đến; lọc và sắp xếp qua bảng Bộ lọc.
// Hiện tìm trên dữ liệu mẫu; tuần 9 đổi taoTatCaTourMau() thành đọc Firestore, phần lọc giữ nguyên.
class TimTourFragment : Fragment() {

    // Hai danh sách và bộ lọc nằm trong Fragment nên còn nguyên khi mở Bộ lọc hay A03 rồi quay lại
    private val tatCa = taoTatCaTourMau()        // mọi tour
    private val tours = ArrayList<TourMau>()     // các tour đang hiện sau khi lọc
    private var boLoc = BoLoc()

    private lateinit var adapter: TourAdapter
    private lateinit var timET: EditText
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

        // Gõ chữ đến đâu lọc đến đó (TextWatcher, bài A08)
        timET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                locLai()
            }
        })

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
