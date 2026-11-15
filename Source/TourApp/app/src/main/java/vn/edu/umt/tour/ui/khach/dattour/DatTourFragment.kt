package vn.edu.umt.tour.ui.khach.dattour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.DecimalFormat
import vn.edu.umt.tour.R

class DatTourFragment : Fragment() {

    // Khai báo biến lưu số lượng và giá tiền
    private var soNguoiLon = 1
    private var soTreEm = 0
    private val giaNguoiLon = 2500000
    private val giaTreEm = 1500000

    private lateinit var tvSoNguoiLon: TextView
    private lateinit var tvSoTreEm: TextView
    private lateinit var tvTamTinh: TextView
    private lateinit var tvTongTien: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a04_dat_tour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ánh xạ các thành phần giao diện
        val btnTruNL = view.findViewById<ImageView>(R.id.btn_tru_nl)
        val btnCongNL = view.findViewById<ImageView>(R.id.btn_cong_nl)
        val btnTruTE = view.findViewById<ImageView>(R.id.btn_tru_te)
        val btnCongTE = view.findViewById<ImageView>(R.id.btn_cong_te)

        tvSoNguoiLon = view.findViewById(R.id.tv_sl_nl)
        tvSoTreEm = view.findViewById(R.id.tv_sl_te)
        tvTamTinh = view.findViewById(R.id.tv_tam_tinh)
        tvTongTien = view.findViewById(R.id.tv_tong_tien)

        // Gắn sự kiện cho các nút
        btnTruNL.setOnClickListener {
            if (soNguoiLon > 1) { // Không cho người lớn nhỏ hơn 1
                soNguoiLon--
                capNhatGiaoDien()
            }
        }

        btnCongNL.setOnClickListener {
            soNguoiLon++
            capNhatGiaoDien()
        }

        btnTruTE.setOnClickListener {
            if (soTreEm > 0) { // Không cho trẻ em nhỏ hơn 0
                soTreEm--
                capNhatGiaoDien()
            }
        }

        btnCongTE.setOnClickListener {
            soTreEm++
            capNhatGiaoDien()
        }

        // Cập nhật lần đầu khi mở màn hình
        capNhatGiaoDien()
    }

    private fun capNhatGiaoDien() {
        // Cập nhật số lượng trên màn hình
        tvSoNguoiLon.text = soNguoiLon.toString()
        tvSoTreEm.text = soTreEm.toString()

        // Tính tiền
        val tongTien = (soNguoiLon * giaNguoiLon) + (soTreEm * giaTreEm)

        // Định dạng tiền tệ có dấu phẩy (VD: 2,500,000)
        val formatter = DecimalFormat("#,###")
        val tienChuoi = formatter.format(tongTien) + " đ"

        // Cập nhật chữ tiền
        tvTamTinh.text = tienChuoi
        tvTongTien.text = tienChuoi
    }
}