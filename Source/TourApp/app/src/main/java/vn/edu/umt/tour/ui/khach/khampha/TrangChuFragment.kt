package vn.edu.umt.tour.ui.khach.khampha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import vn.edu.umt.tour.data.local.AppDatabase
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

        // Bấm vào thẻ tour: mở A03 Chi tiết tour, truyền mã tour
        adapter.onTourClick = { tour ->
            moManHinhCon(ChiTietTourFragment.newInstance(tour.ma))
        }
        // B5: tô tim theo dữ liệu đã lưu trong Room, bấm tim thì lưu hoặc xóa trong Room
        val dao = AppDatabase.layDatabase(requireContext()).yeuThichDao()
        viewLifecycleOwner.lifecycleScope.launch {
            danhDauYeuThich(dao, tours)
            adapter.notifyItemRangeChanged(0, tours.size)
        }
        adapter.onYeuThichClick = { viTri ->
            val tour = tours[viTri]
            tour.yeuThich = !tour.yeuThich
            adapter.notifyItemChanged(viTri)
            viewLifecycleOwner.lifecycleScope.launch { luuYeuThich(dao, tour) }
        }
    }
}
