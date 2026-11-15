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
import com.google.android.material.chip.Chip
import vn.edu.umt.tour.R

// A22 Bản đồ lịch trình: chọn ngày để xem các điểm dừng.
// Bản đồ thật và nút chỉ đường (Intent mở Google Maps) làm ở chức năng B8, tuần 9.
class BanDoLichTrinhFragment : Fragment() {

    private lateinit var ngayTV: TextView
    private lateinit var tieuDeTV: TextView
    private lateinit var diemDungRV: RecyclerView
    private val banDo = taoBanDoMau()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a22_ban_do_lich_trinh, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ngayTV = view.findViewById(R.id.ngayTV)
        tieuDeTV = view.findViewById(R.id.tieuDeTV)
        diemDungRV = view.findViewById(R.id.diemDungRV)
        diemDungRV.layoutManager = LinearLayoutManager(requireContext())

        view.findViewById<TextView>(R.id.tenTourTV).text = banDo[0].tieuDe

        // Mỗi chip ứng với một phần tử trong danh sách banDo: 0 = Tất cả, 1..3 = Ngày 1..3
        view.findViewById<Chip>(R.id.tatCaChip).setOnClickListener { hienNgay(0) }
        view.findViewById<Chip>(R.id.ngay1Chip).setOnClickListener { hienNgay(1) }
        view.findViewById<Chip>(R.id.ngay2Chip).setOnClickListener { hienNgay(2) }
        view.findViewById<Chip>(R.id.ngay3Chip).setOnClickListener { hienNgay(3) }
        hienNgay(2) // mặc định đang chọn Ngày 2 như thiết kế

        view.findViewById<ImageView>(R.id.dinhViIV).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a22_vi_tri_cua_toi, Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.chiDuongBtn).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a22_chi_duong, Toast.LENGTH_SHORT).show()
        }
    }

    // Đổi khối thông tin phía dưới theo ngày được chọn
    private fun hienNgay(viTri: Int) {
        val ngay = banDo[viTri]
        ngayTV.text = ngay.ten
        tieuDeTV.text = ngay.tieuDe
        diemDungRV.adapter = DiemDungAdapter(ngay.diemDung)
    }
}
