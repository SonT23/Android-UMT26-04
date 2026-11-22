package vn.edu.umt.tour.ui.quantri.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import vn.edu.umt.tour.R

// A12 Sửa điểm đến (D1): dùng cho cả thêm mới và sửa.
// newInstance(null) là thêm mới (các ô trống); newInstance("DD01") là sửa điểm đến DD01.
// Bấm Lưu: gửi dữ liệu về A12 bằng setFragmentResult rồi đóng màn hình; A12 tự cập nhật danh sách.
// Nút "Chọn trên bản đồ" sau này mở A24 Chọn vị trí (D8).
class SuaDiemDenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_a12_sua_diem_den, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mã null hoặc không tìm thấy thì coi là thêm mới
        val ma = arguments?.getString(MA)
        val dangSua = ma?.let { KhoDiemDenMau.tim(it) }

        val tenTIL = view.findViewById<TextInputLayout>(R.id.tenTIL)
        val tenET = view.findViewById<TextInputEditText>(R.id.tenET)
        val moTaET = view.findViewById<TextInputEditText>(R.id.moTaET)
        val toaDoTV = view.findViewById<TextView>(R.id.toaDoTV)

        // Spinner tỉnh thành. Tỉnh của điểm đến đang sửa chưa có trong danh sách thì thêm vào.
        val tinh = taoTinhMau()
        if (dangSua != null && dangSua.tinh !in tinh) tinh.add(dangSua.tinh)
        val tinhSP = view.findViewById<Spinner>(R.id.tinhSP)
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tinh)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                tinhSP.adapter = adapter
            }

        if (dangSua == null) {
            view.findViewById<TextView>(R.id.tieuDeTV).setText(R.string.a12_them_tieu_de)
            toaDoTV.setText(R.string.a12_chua_chon_vi_tri)
        } else {
            // savedInstanceState khác null là màn hình được tạo lại (xoay máy):
            // ô nhập tự giữ chữ người dùng đang gõ, không điền đè dữ liệu cũ.
            if (savedInstanceState == null) {
                tenET.setText(dangSua.ten)
                moTaET.setText(dangSua.moTa)
                tinhSP.setSelection(tinh.indexOf(dangSua.tinh))
            }
            toaDoTV.text = dangSua.toaDo
        }

        view.findViewById<Button>(R.id.chonBanDoBtn).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a12_chon_tren_ban_do, Toast.LENGTH_SHORT).show()
        }

        // Lưu: tên không trống, không trùng tên trong cùng tỉnh; hợp lệ thì trả kết quả về A12
        view.findViewById<Button>(R.id.luuBtn).setOnClickListener {
            val ten = tenET.text.toString().trim()
            val tinhChon = tinhSP.selectedItem.toString()
            val trungTen = KhoDiemDenMau.ds.any {
                it.ma != ma && it.tinh == tinhChon && it.ten.equals(ten, ignoreCase = true)
            }
            when {
                ten.isEmpty() -> tenTIL.error = getString(R.string.a12_loi_ten)
                trungTen -> tenTIL.error = getString(R.string.a12_loi_trung_ten)
                else -> {
                    tenTIL.error = null
                    val toaDo = if (dangSua == null) "" else dangSua.toaDo
                    setFragmentResult(
                        KET_QUA,
                        bundleOf(
                            MA to dangSua?.ma.orEmpty(),
                            TEN to ten,
                            TINH to tinhChon,
                            MO_TA to moTaET.text.toString().trim(),
                            TOA_DO to toaDo
                        )
                    )
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        }

        // Hủy: đóng màn hình, không lưu gì (README, mục "Nút Quay lại")
        view.findViewById<Button>(R.id.huyBtn).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        // Khóa của Fragment Result gửi về A12
        const val KET_QUA = "a12_sua_diem_den_ket_qua"
        const val MA = "ma_diem_den" // cũng là khóa trong arguments; chuỗi rỗng là thêm mới
        const val TEN = "ten"
        const val TINH = "tinh"
        const val MO_TA = "mo_ta"
        const val TOA_DO = "toa_do"

        fun newInstance(maDiemDen: String?) = SuaDiemDenFragment().apply {
            arguments = bundleOf(MA to maDiemDen)
        }
    }
}
