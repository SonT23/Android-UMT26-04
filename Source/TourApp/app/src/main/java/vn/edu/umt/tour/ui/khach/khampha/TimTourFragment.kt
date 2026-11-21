package vn.edu.umt.tour.ui.khach.khampha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import vn.edu.umt.tour.R

// A02 Tìm tour: ô tìm, hàng chip lọc, danh sách kết quả.
// Hiện dùng dữ liệu mẫu; tìm thật, lọc, sắp xếp và tải thêm khi cuộn làm khi nối API.
class TimTourFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a02_tim_tour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Danh sách kết quả
        val tours = taoKetQuaTimMau()
        val adapter = TourAdapter(tours)
        val tourRV = view.findViewById<RecyclerView>(R.id.tourRV)
        tourRV.layoutManager = LinearLayoutManager(requireContext())
        tourRV.adapter = adapter

        val soKetQuaTV = view.findViewById<TextView>(R.id.soKetQuaTV)
        soKetQuaTV.text = getString(R.string.a02_so_tour, tours.size)

        // Bấm vào một tour: mở A03 Chi tiết tour, truyền mã tour
        adapter.onTourClick = { tour ->
            moManHinhCon(ChiTietTourFragment.newInstance(tour.ma))
        }
        adapter.onYeuThichClick = { viTri ->
            tours[viTri].yeuThich = !tours[viTri].yeuThich
            adapter.notifyItemChanged(viTri)
        }

        // Nút X xóa chữ trong ô tìm
        val timET = view.findViewById<EditText>(R.id.timET)
        view.findViewById<ImageView>(R.id.xoaIV).setOnClickListener {
            timET.setText("")
        }

        // Chip "Bộ lọc": mở bảng bộ lọc
        view.findViewById<Chip>(R.id.boLocChip).setOnClickListener {
            moManHinhCon(BoLocFragment())
        }
    }
}
