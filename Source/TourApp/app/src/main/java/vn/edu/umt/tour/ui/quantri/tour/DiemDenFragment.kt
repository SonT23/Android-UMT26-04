package vn.edu.umt.tour.ui.quantri.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// A12 Điểm đến: danh sách điểm đến của tour.
// Hiện dùng dữ liệu mẫu; thêm, sửa, xóa thật (D1) làm khi nối API.
class DiemDenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a12_diem_den, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val diemDen = taoDiemDenMau()
        val adapter = DiemDenAdapter(diemDen)
        val diemDenRV = view.findViewById<RecyclerView>(R.id.diemDenRV)
        diemDenRV.layoutManager = LinearLayoutManager(requireContext())
        diemDenRV.adapter = adapter

        // Bấm vào dòng hoặc nút tùy chọn: sau này mở bảng "Sửa điểm đến" (SuaDiemDenFragment)
        adapter.onDiemDenClick = { diem ->
            Toast.makeText(requireContext(), diem.ten, Toast.LENGTH_SHORT).show()
        }
        adapter.onTuyChonClick = { diem ->
            val thongBao = getString(R.string.a12_sua_xoa, diem.ten)
            Toast.makeText(requireContext(), thongBao, Toast.LENGTH_SHORT).show()
        }

        // Nút thêm: sau này mở bảng "Sửa điểm đến" với các ô trống
        view.findViewById<ImageView>(R.id.themIV).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a12_them, Toast.LENGTH_SHORT).show()
        }

        // Nút quay lại: tạm thời đóng màn hình xem thử.
        // Khi có QuanTriActivity thì đổi thành quay về màn hình trước.
        view.findViewById<ImageView>(R.id.quayLaiIV).setOnClickListener {
            requireActivity().finish()
        }
    }
}
