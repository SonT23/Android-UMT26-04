package vn.edu.umt.tour.ui.khach.dattour

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import vn.edu.umt.tour.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ChiTietDonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a09_chi_tiet_don, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tìm đơn theo mã đơn truyền từ A08 (nếu mở từ XemThuActivity thì lấy đơn mẫu đầu tiên)
        val maDon = arguments?.getString(ARG_MA_DON)
        val don = danhSachDonMau.find { it.maDon == maDon } ?: danhSachDonMau.first()

        // Đổ dữ liệu lên giao diện
        view.findViewById<TextView>(R.id.tv_ma_don).text = don.maDon
        view.findViewById<TextView>(R.id.tv_ten_tour).text = don.tenTour
        view.findViewById<TextView>(R.id.tv_ngay_khoi_hanh).text = don.ngay
        view.findViewById<TextView>(R.id.tv_so_nguoi).text = don.soNguoi
        view.findViewById<TextView>(R.id.tv_tong_tien).text = don.tongTien

        val tvTrangThai = view.findViewById<TextView>(R.id.tv_trang_thai)
        when (don.trangThai) {
            0 -> {
                tvTrangThai.text = "Chờ xác nhận"
                tvTrangThai.setBackgroundResource(R.drawable.bg_nhan_xam)
            }
            1 -> {
                tvTrangThai.text = "Đã xác nhận"
                tvTrangThai.setBackgroundResource(R.drawable.bg_nhan_xanh_duong)
            }
            2 -> {
                tvTrangThai.text = "Hoàn thành"
                tvTrangThai.setBackgroundResource(R.drawable.bg_nhan_xanh)
            }
            3 -> {
                tvTrangThai.text = "Đã hủy"
                tvTrangThai.setBackgroundResource(R.drawable.bg_nhan_do)
            }
        }

        // Nút quay lại
        view.findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            quayLai()
        }

        // Demo 2 (C6): Thêm ngày khởi hành vào ứng dụng Lịch của máy
        view.findViewById<Button>(R.id.btn_them_lich).setOnClickListener {
            val (batDauMillis, ketThucMillis) = chuyenNgaySangKhoangThoiGian(don.ngay)

            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, "Khởi hành: ${don.tenTour}")
                putExtra(CalendarContract.Events.EVENT_LOCATION, don.diaDiem)
                putExtra(CalendarContract.Events.DESCRIPTION, "Mã đơn: ${don.maDon} - ${don.soNguoi}")
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, batDauMillis)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, ketThucMillis)
            }

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "Thiết bị chưa cài ứng dụng Lịch",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Đổi chuỗi ngày "dd/MM/yyyy" sang 07:00 - 18:00 cùng ngày (mili giây)
    private fun chuyenNgaySangKhoangThoiGian(ngayChuoi: String): Pair<Long, Long> {
        val cal = Calendar.getInstance()
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("vi", "VN"))
            val date = sdf.parse(ngayChuoi)
            if (date != null) {
                cal.time = date
            }
        } catch (_: Exception) {
            // Giữ ngày hiện tại nếu chuỗi ngày không đúng định dạng
        }

        cal.set(Calendar.HOUR_OF_DAY, 7)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        val batDau = cal.timeInMillis

        cal.set(Calendar.HOUR_OF_DAY, 18)
        val ketThuc = cal.timeInMillis

        return Pair(batDau, ketThuc)
    }

    companion object {
        private const val ARG_MA_DON = "ma_don"

        fun newInstance(maDon: String) = ChiTietDonFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_MA_DON, maDon)
            }
        }
    }
}