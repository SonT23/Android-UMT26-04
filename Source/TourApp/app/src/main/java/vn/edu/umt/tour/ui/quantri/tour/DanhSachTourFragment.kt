package vn.edu.umt.tour.ui.quantri.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import vn.edu.umt.tour.R

// A13 Danh sách tour: tìm theo tên, lọc bằng chip, danh sách thẻ tour, nút thêm tour.
// Lọc và tìm đang chạy trên dữ liệu mẫu; khi nối API thì thay danh sách mẫu bằng dữ liệu thật.
class DanhSachTourFragment : Fragment() {

    // tatCaTour: toàn bộ tour; hienThi: các tour đang hiện trên màn hình sau khi lọc và tìm
    private val tatCaTour = taoTourMau()
    private val hienThi = ArrayList<TourMau>()
    private lateinit var adapter: TourAdapter
    private lateinit var khongCoTV: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a13_danh_sach_tour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        khongCoTV = view.findViewById(R.id.khongCoTV)
        adapter = TourAdapter(hienThi)
        val tourRV = view.findViewById<RecyclerView>(R.id.tourRV)
        tourRV.layoutManager = LinearLayoutManager(requireContext())
        tourRV.adapter = adapter

        val timET = view.findViewById<EditText>(R.id.timET)
        val locCG = view.findViewById<ChipGroup>(R.id.locCG)

        // Đổi chip lọc thì lọc lại danh sách
        locCG.setOnCheckedStateChangeListener { group, checkedIds ->
            locLai(timET.text.toString(), locCG.checkedChipId)
        }

        // Bấm nút tìm trên bàn phím thì lọc theo tên
        timET.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                locLai(timET.text.toString(), locCG.checkedChipId)
                true
            } else {
                false
            }
        }

        // Bấm thẻ: sau này mở A14 Sửa tour. Bấm nút tùy chọn: sau này mở menu
        // Sửa (A14), Đợt khởi hành (A15), Ẩn hoặc Hiện.
        adapter.onTourClick = { tour ->
            Toast.makeText(requireContext(), tour.ten, Toast.LENGTH_SHORT).show()
        }
        adapter.onTuyChonClick = { tour ->
            val thongBao = getString(R.string.a13_menu_tour, tour.ten)
            Toast.makeText(requireContext(), thongBao, Toast.LENGTH_SHORT).show()
        }

        // Nút thêm trên thanh trên và nút nổi: sau này mở A14 ở chế độ thêm mới
        view.findViewById<ImageView>(R.id.themIV).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a13_them_tour, Toast.LENGTH_SHORT).show()
        }
        view.findViewById<ExtendedFloatingActionButton>(R.id.themTourFAB).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a13_them_tour, Toast.LENGTH_SHORT).show()
        }

        // Nút tùy chọn trên thanh trên: sau này mở A12 Điểm đến
        view.findViewById<ImageView>(R.id.tuyChonIV).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a13_menu_tren, Toast.LENGTH_SHORT).show()
        }

        // Lần đầu: hiện tất cả
        locLai("", R.id.tatCaChip)
    }

    // Chọn ra các tour hợp với chữ tìm và chip đang chọn, rồi báo adapter vẽ lại
    private fun locLai(chuTim: String, chipId: Int) {
        val tim = chuTim.trim().lowercase()
        hienThi.clear()
        for (tour in tatCaTour) {
            val hopChip = when (chipId) {
                R.id.dangHienChip -> tour.dangHien
                R.id.dangAnChip -> !tour.dangHien
                R.id.noiBatChip -> tour.noiBat
                else -> true
            }
            val hopTen = tim.isEmpty() || tour.ten.lowercase().contains(tim)
            if (hopChip && hopTen) {
                hienThi.add(tour)
            }
        }
        adapter.notifyDataSetChanged()

        if (hienThi.isEmpty()) {
            khongCoTV.visibility = View.VISIBLE
        } else {
            khongCoTV.visibility = View.GONE
        }
    }
}
