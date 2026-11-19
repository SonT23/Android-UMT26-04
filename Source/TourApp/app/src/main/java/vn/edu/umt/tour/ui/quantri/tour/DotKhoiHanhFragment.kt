package vn.edu.umt.tour.ui.quantri.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import vn.edu.umt.tour.R

// A15 Đợt khởi hành của một tour: lọc theo trạng thái, đóng hoặc hủy đợt, thêm đợt.
// Đóng và hủy đang đổi trạng thái trên dữ liệu mẫu; khi nối API thì gọi API rồi mới đổi.
class DotKhoiHanhFragment : Fragment() {

    private val tatCaDot = taoDotMau()
    private val hienThi = ArrayList<DotMau>()
    private lateinit var adapter: DotAdapter
    private lateinit var locCG: ChipGroup
    private lateinit var khongCoTV: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a15_dot_khoi_hanh, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tenTourTV).text = TEN_TOUR_DANG_XEM
        khongCoTV = view.findViewById(R.id.khongCoTV)

        adapter = DotAdapter(hienThi)
        val dotRV = view.findViewById<RecyclerView>(R.id.dotRV)
        dotRV.layoutManager = LinearLayoutManager(requireContext())
        dotRV.adapter = adapter

        locCG = view.findViewById(R.id.locCG)
        locCG.setOnCheckedStateChangeListener { group, checkedIds ->
            locLai()
        }

        // Sửa: sau này mở bảng sửa đợt (ngày đi, ngày về, giá, số chỗ)
        adapter.onSuaClick = { viTri ->
            val thongBao = getString(R.string.a15_sua_dot, hienThi[viTri].ngay)
            Toast.makeText(requireContext(), thongBao, Toast.LENGTH_SHORT).show()
        }
        // Đóng đợt: không nhận thêm khách
        adapter.onDongClick = { viTri ->
            hienThi[viTri].trangThai = DOT_DONG
            locLai()
        }
        // Hủy đợt: chỉ có ở đợt chưa có ai đặt
        adapter.onHuyClick = { viTri ->
            hienThi[viTri].trangThai = DOT_HUY
            locLai()
        }

        // Thêm đợt: sau này mở bảng thêm đợt có MaterialDatePicker chọn khoảng ngày
        view.findViewById<ExtendedFloatingActionButton>(R.id.themDotFAB).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a15_them_dot, Toast.LENGTH_SHORT).show()
        }
        // Xuất danh sách khách của đợt ra file CSV (chức năng E7) làm sau
        view.findViewById<ImageView>(R.id.xuatIV).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a15_xuat_danh_sach, Toast.LENGTH_SHORT).show()
        }
        // Quay lại: tạm thời đóng màn hình xem thử
        view.findViewById<ImageView>(R.id.quayLaiIV).setOnClickListener {
            requireActivity().finish()
        }

        locLai()
    }

    // Chọn các đợt hợp với chip đang chọn rồi vẽ lại danh sách
    private fun locLai() {
        hienThi.clear()
        for (dot in tatCaDot) {
            val hop = when (locCG.checkedChipId) {
                R.id.dangMoChip -> dot.trangThai == DOT_MO
                R.id.daDongChip -> dot.trangThai == DOT_DONG
                R.id.daHuyChip -> dot.trangThai == DOT_HUY
                else -> true
            }
            if (hop) {
                hienThi.add(dot)
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
