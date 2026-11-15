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
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import vn.edu.umt.tour.R

// A12 Sửa điểm đến: tên, tỉnh thành, mô tả, vị trí trên bản đồ.
// Giai đoạn này chỉ dựng giao diện và kiểm tra ô tên; lưu thật làm khi nối API,
// nút "Chọn trên bản đồ" sau này mở A24 Chọn vị trí.
class SuaDiemDenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_a12_sua_diem_den, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tenTIL = view.findViewById<TextInputLayout>(R.id.tenTIL)
        val tenET = view.findViewById<TextInputEditText>(R.id.tenET)
        val moTaET = view.findViewById<TextInputEditText>(R.id.moTaET)
        val toaDoTV = view.findViewById<TextView>(R.id.toaDoTV)

        // Điền sẵn dữ liệu của điểm đến đang sửa
        tenET.setText(TEN_DANG_SUA)
        moTaET.setText(MO_TA_DANG_SUA)
        toaDoTV.text = TOA_DO_DANG_SUA

        // Spinner tỉnh thành: chọn sẵn tỉnh của điểm đến đang sửa
        val tinh = taoTinhMau()
        val tinhSP = view.findViewById<Spinner>(R.id.tinhSP)
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tinh)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                tinhSP.adapter = adapter
            }
        tinhSP.setSelection(tinh.indexOf(TINH_DANG_SUA))

        view.findViewById<Button>(R.id.chonBanDoBtn).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a12_chon_tren_ban_do, Toast.LENGTH_SHORT).show()
        }

        // Lưu: tên không được để trống, nếu trống thì báo lỗi ngay dưới ô
        view.findViewById<Button>(R.id.luuBtn).setOnClickListener {
            val ten = tenET.text.toString().trim()
            if (ten.isEmpty()) {
                tenTIL.error = getString(R.string.a12_loi_ten)
            } else {
                tenTIL.error = null
                val thongBao = getString(R.string.a12_da_luu, ten)
                Toast.makeText(requireContext(), thongBao, Toast.LENGTH_SHORT).show()
            }
        }

        // Hủy: tạm thời đóng màn hình xem thử
        view.findViewById<Button>(R.id.huyBtn).setOnClickListener {
            requireActivity().finish()
        }
    }
}
