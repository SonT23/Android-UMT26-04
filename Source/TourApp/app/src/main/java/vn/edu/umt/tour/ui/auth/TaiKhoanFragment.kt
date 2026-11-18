package vn.edu.umt.tour.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import vn.edu.umt.tour.R

// A10 Tài khoản: ảnh đại diện, thông tin cá nhân, menu Đổi mật khẩu / Cài đặt / Đăng xuất.
// Hiện dùng dữ liệu mẫu trong DuLieuMau.kt; lấy thông tin người dùng thật làm sau.
class TaiKhoanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a10_tai_khoan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ảnh đại diện là chữ cái đầu của tên (chữ cuối trong họ tên), vd. "Nguyễn Văn An" -> "A"
        val ten = DuLieuMau.HO_TEN.trim().substringAfterLast(' ')
        view.findViewById<TextView>(R.id.anhDaiDienTV).text = ten.take(1).uppercase()

        view.findViewById<TextView>(R.id.hoTenTV).text = DuLieuMau.HO_TEN
        view.findViewById<TextView>(R.id.emailTV).text = DuLieuMau.EMAIL
        view.findViewById<TextInputEditText>(R.id.hoTenET).setText(DuLieuMau.HO_TEN)
        view.findViewById<TextInputEditText>(R.id.soDienThoaiET).setText(DuLieuMau.SO_DIEN_THOAI)

        // Chụp ảnh đại diện làm sau
        view.findViewById<ImageView>(R.id.doiAnhIV).setOnClickListener {
            thongBao(R.string.a10_doi_anh)
        }
        view.findViewById<Button>(R.id.luuBtn).setOnClickListener {
            thongBao(R.string.a10_luu_thay_doi)
        }
        view.findViewById<LinearLayout>(R.id.doiMatKhauLL).setOnClickListener {
            thongBao(R.string.a10_doi_mat_khau)
        }
        // Nút bánh răng và dòng "Cài đặt" sau này cùng mở A19 Cài đặt
        view.findViewById<ImageView>(R.id.caiDatIV).setOnClickListener {
            thongBao(R.string.a10_cai_dat)
        }
        view.findViewById<LinearLayout>(R.id.caiDatLL).setOnClickListener {
            thongBao(R.string.a10_cai_dat)
        }
        // Sau này xóa phiên đăng nhập rồi về A06 Đăng nhập
        view.findViewById<LinearLayout>(R.id.dangXuatLL).setOnClickListener {
            thongBao(R.string.a10_dang_xuat)
        }
    }

    private fun thongBao(chuoi: Int) {
        Toast.makeText(requireContext(), chuoi, Toast.LENGTH_SHORT).show()
    }
}
