package vn.edu.umt.tour.ui.khach.dattour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import vn.edu.umt.tour.R
import vn.edu.umt.tour.util.dinhDangTien

class DatThanhCongFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a05_dat_thanh_cong, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null) {
            val maDon = args.getString(ARG_MA_DON, "DH00124")
            val tenTour = args.getString(ARG_TEN_TOUR, "")
            val ngayKhoiHanh = args.getString(ARG_NGAY_KHOI_HANH, "")
            val soNguoiLon = args.getInt(ARG_SO_NGUOI_LON, 1)
            val soTreEm = args.getInt(ARG_SO_TRE_EM, 0)
            val tongTien = args.getLong(ARG_TONG_TIEN, 0L)

            view.findViewById<TextView>(R.id.tv_ma_don).text = "Mã đơn: $maDon"
            if (tenTour.isNotBlank()) {
                view.findViewById<TextView>(R.id.tv_ten_tour).text = tenTour
            }
            if (ngayKhoiHanh.isNotBlank()) {
                view.findViewById<TextView>(R.id.tv_ngay_khoi_hanh).text = ngayKhoiHanh
            }
            view.findViewById<TextView>(R.id.tv_so_nguoi).text = if (soTreEm > 0) {
                "$soNguoiLon người lớn, $soTreEm trẻ em"
            } else {
                "$soNguoiLon người lớn"
            }
            view.findViewById<TextView>(R.id.tv_tong_tien).text = dinhDangTien(tongTien)
        }

        // Nối A05 -> A08 (Xem đơn của tôi)
        view.findViewById<Button>(R.id.btn_xem_don).setOnClickListener {
            moManHinhCon(DonCuaToiFragment())
        }

        // Nút đóng và Về trang chủ
        view.findViewById<ImageView>(R.id.btn_close).setOnClickListener {
            quayLai()
        }
        view.findViewById<Button>(R.id.btn_ve_trang_chu).setOnClickListener {
            quayLai()
        }
    }

    companion object {
        private const val ARG_MA_DON = "ma_don"
        private const val ARG_TEN_TOUR = "ten_tour"
        private const val ARG_NGAY_KHOI_HANH = "ngay_khoi_hanh"
        private const val ARG_SO_NGUOI_LON = "so_nguoi_lon"
        private const val ARG_SO_TRE_EM = "so_tre_em"
        private const val ARG_TONG_TIEN = "tong_tien"

        fun newInstance(
            tenTour: String,
            ngayKhoiHanh: String,
            soNguoiLon: Int,
            soTreEm: Int,
            tongTien: Long,
            maDon: String = "DH00124"
        ) = DatThanhCongFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_MA_DON, maDon)
                putString(ARG_TEN_TOUR, tenTour)
                putString(ARG_NGAY_KHOI_HANH, ngayKhoiHanh)
                putInt(ARG_SO_NGUOI_LON, soNguoiLon)
                putInt(ARG_SO_TRE_EM, soTreEm)
                putLong(ARG_TONG_TIEN, tongTien)
            }
        }
    }
}