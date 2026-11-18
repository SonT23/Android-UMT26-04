package vn.edu.umt.tour.ui.khach.dattour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

class DonCuaToiFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a08_don_cua_toi, container, false)

        // 1. Tạo danh sách dữ liệu mẫu để xem trước
        val danhSachMau = listOf(
            DonHang("#HD12345", "Khám phá Vịnh Hạ Long", "20/11/2026", "2 người lớn", "5,000,000 đ", 1),
            DonHang("#HD12346", "Phố cổ Hội An", "25/11/2026", "4 người lớn", "12,000,000 đ", 0),
            DonHang("#HD12347", "Sapa sương mù", "10/10/2026", "2 người lớn", "4,500,000 đ", 2),
            DonHang("#HD12348", "Biển Phú Quốc", "01/09/2026", "3 người lớn", "15,000,000 đ", 3)
        )

        // 2. Gắn Adapter vào RecyclerView
        val rvDonHang = view.findViewById<RecyclerView>(R.id.rv_don_hang)
        rvDonHang.layoutManager = LinearLayoutManager(requireContext())
        rvDonHang.adapter = DonAdapter(danhSachMau)

        return view
    }
}