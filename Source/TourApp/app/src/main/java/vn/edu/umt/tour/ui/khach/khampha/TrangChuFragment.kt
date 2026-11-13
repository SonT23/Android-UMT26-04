package vn.edu.umt.tour.ui.khach.khampha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// A01 Trang chủ: ô tìm nhanh, điểm đến cuộn ngang, tour nổi bật.
// Hiện dùng dữ liệu mẫu trong DuLieuMau.kt; gọi API làm ở tuần 7.
class TrangChuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a01_trang_chu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Điểm đến: danh sách ngang
        val diemDenRV = view.findViewById<RecyclerView>(R.id.diemDenRV)
        diemDenRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        diemDenRV.adapter = DiemDenAdapter(taoDiemDenMau())

        // Tour nổi bật: danh sách dọc
        val tours = taoTourMau()
        val adapter = TourNoiBatAdapter(tours)
        val tourRV = view.findViewById<RecyclerView>(R.id.tourRV)
        tourRV.layoutManager = LinearLayoutManager(requireContext())
        tourRV.adapter = adapter

        adapter.onTourClick = { tour ->
            // Sau này mở A03 Chi tiết tour
            Toast.makeText(requireContext(), tour.ten, Toast.LENGTH_SHORT).show()
        }
        adapter.onYeuThichClick = { viTri ->
            // Đổi trạng thái tim rồi báo adapter vẽ lại đúng dòng đó
            tours[viTri].yeuThich = !tours[viTri].yeuThich
            adapter.notifyItemChanged(viTri)
        }
    }
}
