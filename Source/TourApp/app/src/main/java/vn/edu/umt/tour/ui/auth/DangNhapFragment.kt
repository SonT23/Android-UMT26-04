package vn.edu.umt.tour.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import vn.edu.umt.tour.R

// A06 Đăng nhập: email, mật khẩu, đăng nhập bằng vân tay, link sang Đăng ký và Quên mật khẩu.
// Giai đoạn giao diện: bấm nút chỉ hiện Toast. Kiểm tra dữ liệu và gọi API làm sau.
class DangNhapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a06_dang_nhap, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.dangNhapBtn).setOnClickListener {
            thongBao(R.string.a06_dang_nhap)
        }
        view.findViewById<Button>(R.id.vanTayBtn).setOnClickListener {
            thongBao(R.string.a06_dang_nhap_van_tay)
        }
        // Sau này mở A20 Quên mật khẩu
        view.findViewById<TextView>(R.id.quenMatKhauTV).setOnClickListener {
            thongBao(R.string.a06_quen_mat_khau)
        }
        // Sau này mở A07 Đăng ký
        view.findViewById<TextView>(R.id.dangKyTV).setOnClickListener {
            thongBao(R.string.a06_dang_ky)
        }
        // Sau này vào thẳng A01 Trang chủ với tư cách khách
        view.findViewById<TextView>(R.id.xemTourTV).setOnClickListener {
            thongBao(R.string.a06_xem_tour)
        }
    }

    private fun thongBao(chuoi: Int) {
        Toast.makeText(requireContext(), chuoi, Toast.LENGTH_SHORT).show()
    }
}
