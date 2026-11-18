package vn.edu.umt.tour.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import vn.edu.umt.tour.R

// A07 Đăng ký: họ tên, email, số điện thoại, mật khẩu, nhập lại mật khẩu, đồng ý điều khoản.
class DangKyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a07_dang_ky, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Nút quay lại: làm giống phím Back của máy
        view.findViewById<ImageView>(R.id.quayLaiIV).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Hiện sẵn trạng thái báo lỗi như trong Figma để xem thử (viền đỏ + dòng lỗi).
        // Quy tắc kiểm tra mật khẩu thật làm sau. Gõ vào ô thì lỗi tự tắt.
        val matKhauTIL = view.findViewById<TextInputLayout>(R.id.matKhauTIL)
        matKhauTIL.error = getString(R.string.a07_loi_mat_khau)
        view.findViewById<TextInputEditText>(R.id.matKhauET).doAfterTextChanged {
            matKhauTIL.error = null
        }

        view.findViewById<Button>(R.id.dangKyBtn).setOnClickListener {
            thongBao(R.string.a07_dang_ky)
        }
        // Sau này quay về A06 Đăng nhập
        view.findViewById<TextView>(R.id.dangNhapTV).setOnClickListener {
            thongBao(R.string.a07_dang_nhap)
        }
    }

    private fun thongBao(chuoi: Int) {
        Toast.makeText(requireContext(), chuoi, Toast.LENGTH_SHORT).show()
    }
}
