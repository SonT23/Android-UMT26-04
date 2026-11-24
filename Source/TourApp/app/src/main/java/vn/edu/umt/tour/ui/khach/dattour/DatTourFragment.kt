package vn.edu.umt.tour.ui.khach.dattour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import vn.edu.umt.tour.R
import vn.edu.umt.tour.util.dinhDangTien
import vn.edu.umt.tour.util.laSoDienThoaiHopLe

class DatTourFragment : Fragment() {

    private var soNguoiLon = 1
    private var soTreEm = 0
    private var soChoConLai = 5
    private val giaNguoiLon = 2_500_000L
    private val giaTreEm = 1_500_000L

    private var tenTourHienTai = "Khám phá Vịnh Hạ Long"
    private var ngayKhoiHanhHienTai = "20/11/2026"

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

        // Khôi phục số người khi xoay màn hình
        if (savedInstanceState != null) {
            soNguoiLon = savedInstanceState.getInt(KEY_SO_NGUOI_LON, 1)
            soTreEm = savedInstanceState.getInt(KEY_SO_TRE_EM, 0)
        }

        // Nhận maTour, maDot từ A03 (nếu mở trực tiếp từ XemThuActivity thì dùng dữ liệu mẫu)
        val maTour = arguments?.getString(ARG_MA_TOUR)
        val maDot = arguments?.getString(ARG_MA_DOT)
        if (!maTour.isNullOrBlank()) {
            tenTourHienTai = "Tour $maTour"
        }
        if (!maDot.isNullOrBlank()) {
            ngayKhoiHanhHienTai = "Đợt $maDot"
        }

        // Hiển thị thông tin tour lên thẻ trên cùng
        view.findViewById<TextView>(R.id.tv_ten_tour).text = tenTourHienTai
        view.findViewById<TextView>(R.id.tv_ngay_khoi_hanh).text = "Khởi hành: $ngayKhoiHanhHienTai"
        view.findViewById<TextView>(R.id.tv_con_cho).text = "Còn $soChoConLai chỗ"

        // Nút quay lại
        view.findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            quayLai()
        }

        // Ánh xạ các thành phần số người & tiền
        val btnTruNL = view.findViewById<ImageView>(R.id.btn_tru_nl)
        val btnCongNL = view.findViewById<ImageView>(R.id.btn_cong_nl)
        val btnTruTE = view.findViewById<ImageView>(R.id.btn_tru_te)
        val btnCongTE = view.findViewById<ImageView>(R.id.btn_cong_te)

        tvSoNguoiLon = view.findViewById(R.id.tv_sl_nl)
        tvSoTreEm = view.findViewById(R.id.tv_sl_te)
        tvTamTinh = view.findViewById(R.id.tv_tam_tinh)
        tvTongTien = view.findViewById(R.id.tv_tong_tien)

        // Định dạng đơn giá và giảm giá bằng util/DinhDang.kt
        view.findViewById<TextView>(R.id.tv_gia_nl).text = dinhDangTien(giaNguoiLon)
        view.findViewById<TextView>(R.id.tv_gia_te).text = dinhDangTien(giaTreEm)
        view.findViewById<TextView>(R.id.tv_giam_gia).text = dinhDangTien(0L)

        // Nút cộng / trừ kèm chặn vượt số chỗ còn lại
        btnTruNL.setOnClickListener {
            if (soNguoiLon > 1) {
                soNguoiLon--
                capNhatGiaoDien()
            }
        }

        btnCongNL.setOnClickListener {
            if (soNguoiLon + soTreEm < soChoConLai) {
                soNguoiLon++
                capNhatGiaoDien()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Chỉ còn $soChoConLai chỗ trống cho đợt này",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnTruTE.setOnClickListener {
            if (soTreEm > 0) {
                soTreEm--
                capNhatGiaoDien()
            }
        }

        btnCongTE.setOnClickListener {
            if (soNguoiLon + soTreEm < soChoConLai) {
                soTreEm++
                capNhatGiaoDien()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Chỉ còn $soChoConLai chỗ trống cho đợt này",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Kiểm tra dữ liệu và xác nhận đặt tour
        val tilHoTen = view.findViewById<TextInputLayout>(R.id.til_ho_ten)
        val edtHoTen = view.findViewById<TextInputEditText>(R.id.edt_ho_ten)
        val tilSoDienThoai = view.findViewById<TextInputLayout>(R.id.til_so_dien_thoai)
        val edtSoDienThoai = view.findViewById<TextInputEditText>(R.id.edt_so_dien_thoai)
        val btnXacNhan = view.findViewById<Button>(R.id.btn_xac_nhan_dat_tour)

        btnXacNhan.setOnClickListener {
            val hoTen = edtHoTen.text?.toString()?.trim().orEmpty()
            val sdt = edtSoDienThoai.text?.toString()?.trim().orEmpty()

            tilHoTen.error = null
            tilSoDienThoai.error = null

            var hopLe = true
            if (hoTen.isEmpty()) {
                tilHoTen.error = "Vui lòng nhập họ và tên"
                hopLe = false
            }
            if (!sdt.laSoDienThoaiHopLe()) {
                tilSoDienThoai.error = "Số điện thoại phải gồm 10 chữ số, bắt đầu bằng 0"
                hopLe = false
            }
            if (!hopLe) return@setOnClickListener

            val tongTien = tinhTongTien()
            val chuoiSoNguoi = if (soTreEm > 0) {
                "$soNguoiLon người lớn, $soTreEm trẻ em"
            } else {
                "$soNguoiLon người lớn"
            }

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Xác nhận đặt tour")
                .setMessage(
                    "Tour: $tenTourHienTai\n" +
                            "Số người: $chuoiSoNguoi\n" +
                            "Tổng tiền: ${dinhDangTien(tongTien)}\n\n" +
                            "Bạn có chắc chắn muốn đặt tour này?"
                )
                .setNegativeButton("Hủy", null)
                .setPositiveButton("Xác nhận") { _, _ ->
                    moManHinhCon(
                        DatThanhCongFragment.newInstance(
                            tenTour = tenTourHienTai,
                            ngayKhoiHanh = ngayKhoiHanhHienTai,
                            soNguoiLon = soNguoiLon,
                            soTreEm = soTreEm,
                            tongTien = tongTien
                        )
                    )
                }
                .show()
        }

        capNhatGiaoDien()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SO_NGUOI_LON, soNguoiLon)
        outState.putInt(KEY_SO_TRE_EM, soTreEm)
    }

    private fun tinhTongTien(): Long {
        return (soNguoiLon * giaNguoiLon) + (soTreEm * giaTreEm)
    }

    private fun capNhatGiaoDien() {
        tvSoNguoiLon.text = soNguoiLon.toString()
        tvSoTreEm.text = soTreEm.toString()

        val tienChuoi = dinhDangTien(tinhTongTien())
        tvTamTinh.text = tienChuoi
        tvTongTien.text = tienChuoi
    }

    companion object {
        private const val ARG_MA_TOUR = "ma_tour"
        private const val ARG_MA_DOT = "ma_dot"
        private const val KEY_SO_NGUOI_LON = "so_nguoi_lon"
        private const val KEY_SO_TRE_EM = "so_tre_em"

        fun newInstance(maTour: String, maDot: String) = DatTourFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_MA_TOUR, maTour)
                putString(ARG_MA_DOT, maDot)
            }
        }
    }
}