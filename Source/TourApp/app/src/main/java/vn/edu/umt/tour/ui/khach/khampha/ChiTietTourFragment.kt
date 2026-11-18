package vn.edu.umt.tour.ui.khach.khampha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// A03 Chi tiết tour: ảnh, tên, giá, lịch trình từng ngày, các đợt khởi hành.
// Hiện dùng dữ liệu mẫu của tour "Đà Lạt 3 ngày 2 đêm"; nhận id tour qua Intent và gọi API làm sau.
class ChiTietTourFragment : Fragment() {

    private var yeuThich = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a03_chi_tiet_tour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Thông tin chung của tour (lấy tour đầu tiên trong dữ liệu mẫu)
        val tour = taoTourMau()[0]
        view.findViewById<TextView>(R.id.tenTV).text = tour.ten
        view.findViewById<TextView>(R.id.diaDiemTV).text = tour.moTa
        view.findViewById<TextView>(R.id.giaTV).text = tour.gia
        view.findViewById<TextView>(R.id.soAnhTV).text = getString(R.string.a03_so_anh, 1, 5)

        // Lịch trình từng ngày
        val lichTrinhRV = view.findViewById<RecyclerView>(R.id.lichTrinhRV)
        lichTrinhRV.layoutManager = LinearLayoutManager(requireContext())
        lichTrinhRV.adapter = LichTrinhAdapter(taoLichTrinhMau())

        // Đợt khởi hành
        val dotAdapter = DotKhoiHanhAdapter(taoDotMau())
        val dotRV = view.findViewById<RecyclerView>(R.id.dotRV)
        dotRV.layoutManager = LinearLayoutManager(requireContext())
        dotRV.adapter = dotAdapter
        dotAdapter.onDatClick = { dot ->
            // Sau này mở A04 Đặt tour (module C của Thịnh)
            Toast.makeText(requireContext(), dot.ngay, Toast.LENGTH_SHORT).show()
        }

        // Nút tim: bấm để đổi qua lại giữa đã lưu và chưa lưu
        val yeuThichIV = view.findViewById<ImageView>(R.id.yeuThichIV)
        yeuThichIV.setOnClickListener {
            yeuThich = !yeuThich
            if (yeuThich) {
                yeuThichIV.setImageResource(R.drawable.ic_favorite)
            } else {
                yeuThichIV.setImageResource(R.drawable.ic_favorite_border)
            }
        }

        // Chia sẻ (B7) và bản đồ lịch trình (A22) làm sau
        view.findViewById<ImageView>(R.id.chiaSeIV).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a03_chia_se, Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.banDoBtn).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a03_xem_ban_do, Toast.LENGTH_SHORT).show()
        }
    }
}
