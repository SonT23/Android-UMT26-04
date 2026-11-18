package vn.edu.umt.tour.ui.quantri.don.uie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import vn.edu.umt.tour.R

class XuLyDonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_e_a17_xu_ly_don, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val don = taoDanhSachDonMau().first()

        view.findViewById<TextView>(R.id.maDonTV).text = don.maDon
        view.findViewById<TextView>(R.id.ngayDatTV).text = "Tạo lúc ${don.ngayDat}"
        view.findViewById<TextView>(R.id.trangThaiTV).text = don.trangThai
        view.findViewById<TextView>(R.id.tenKhachTV).text = don.tenKhachHang
        view.findViewById<TextView>(R.id.avatarTV).text =
            don.tenKhachHang.trim().split(" ").lastOrNull()?.take(1)?.uppercase() ?: "?"
        view.findViewById<TextView>(R.id.sdtTV).text = don.sdt
        view.findViewById<TextView>(R.id.emailTV).text = don.email
        view.findViewById<TextView>(R.id.tenTourTV).text = don.tenTour
        view.findViewById<TextView>(R.id.ngayKhoiHanhTV).text = "Khởi hành ${don.ngayKhoiHanh} · 07:00"
        view.findViewById<TextView>(R.id.soNguoiTV).text =
            "${don.soNguoiLon} người lớn · ${don.soTreEm} trẻ em"
        view.findViewById<TextView>(R.id.tongTienTV).text = don.tongTien
        view.findViewById<TextView>(R.id.daThanhToanTV).text = don.daThanhToan

        view.findViewById<MaterialButton>(R.id.btnXacNhan).setOnClickListener {
            Toast.makeText(requireContext(), "Đã xác nhận đơn ${don.maDon}", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<MaterialButton>(R.id.btnHuy).setOnClickListener {
            Toast.makeText(requireContext(), "Đã hủy đơn ${don.maDon}", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<MaterialButton>(R.id.btnGoiDien).setOnClickListener {
            Toast.makeText(requireContext(), "Đang mở cuộc gọi tới ${don.sdt}", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<MaterialButton>(R.id.btnNhanTin).setOnClickListener {
            Toast.makeText(requireContext(), "Đang nhắn tin tới ${don.sdt}", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<MaterialButton>(R.id.btnGhiNhanThanhToan).setOnClickListener {
            val soTien = view.findViewById<EditText>(R.id.soTienET).text.toString()
            Toast.makeText(requireContext(), "Ghi nhận thanh toán: ${soTien}đ", Toast.LENGTH_SHORT).show()
        }
    }
}
