package vn.edu.umt.tour.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import vn.edu.umt.tour.R

// A20 Quên mật khẩu, bước 2 trong 3 bước (Email → Mã OTP → Mật khẩu mới): nhập mã 6 số.
// Gửi mã, kiểm tra mã và đếm ngược gửi lại làm cùng chức năng OTP sau này.
class QuenMatKhauFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a20_quen_mat_khau, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.quayLaiIV).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        view.findViewById<TextView>(R.id.daGuiToiTV).text =
            getString(R.string.a20_da_gui_toi, DuLieuMau.EMAIL)

        // Số giây còn lại trước khi được gửi lại mã (mẫu: 45 giây -> "00:45")
        val giayCho = 45
        view.findViewById<TextView>(R.id.guiLaiTV).text =
            getString(R.string.a20_gui_lai_sau, giayCho / 60, giayCho % 60)

        // 6 ô nhập mã: gõ xong một chữ số thì tự chuyển sang ô kế tiếp
        val oMa = listOf(R.id.ma1ET, R.id.ma2ET, R.id.ma3ET, R.id.ma4ET, R.id.ma5ET, R.id.ma6ET)
            .map { id -> view.findViewById<EditText>(id) }
        for (i in 0 until oMa.size - 1) {
            oMa[i].doAfterTextChanged { chu ->
                if (chu?.length == 1) {
                    oMa[i + 1].requestFocus()
                }
            }
        }

        view.findViewById<Button>(R.id.xacNhanBtn).setOnClickListener {
            val ma = oMa.joinToString("") { o -> o.text.toString() }
            Toast.makeText(requireContext(), getString(R.string.a20_ma_da_nhap, ma), Toast.LENGTH_SHORT).show()
        }
    }
}
