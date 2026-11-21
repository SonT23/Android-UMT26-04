package vn.edu.umt.tour.ui.khach.khampha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import vn.edu.umt.tour.R

// A02 Bộ lọc: điểm đến, khoảng giá, tháng khởi hành, số ngày, sắp xếp.
// Giai đoạn này chỉ dựng giao diện; áp dụng bộ lọc vào kết quả tìm làm khi nối API.
class BoLocFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_a02_bo_loc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Spinner điểm đến: dòng đầu là "Tất cả điểm đến", sau đó là các điểm đến mẫu
        val diemDen = arrayListOf(getString(R.string.a02_tat_ca_diem_den))
        diemDen.addAll(taoDiemDenMau())
        val diemDenSP = view.findViewById<Spinner>(R.id.diemDenSP)
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, diemDen)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                diemDenSP.adapter = adapter
            }
        diemDenSP.setSelection(1) // đang chọn "Đà Lạt" như thiết kế

        val giaTuET = view.findViewById<EditText>(R.id.giaTuET)
        val giaDenET = view.findViewById<EditText>(R.id.giaDenET)
        val thangCG = view.findViewById<ChipGroup>(R.id.thangCG)
        val soNgayCG = view.findViewById<ChipGroup>(R.id.soNgayCG)
        val sapXepCG = view.findViewById<ChipGroup>(R.id.sapXepCG)

        // Xóa lọc: đưa mọi mục về mặc định
        view.findViewById<Button>(R.id.xoaLocBtn).setOnClickListener {
            diemDenSP.setSelection(0)
            giaTuET.setText("")
            giaDenET.setText("")
            thangCG.clearCheck()
            soNgayCG.clearCheck()
            sapXepCG.check(R.id.giaTangChip)
        }

        // Áp dụng bộ lọc rồi quay về A02 (lọc thật trên danh sách làm khi nối Firestore)
        view.findViewById<Button>(R.id.xemBtn).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a02_da_ap_dung, Toast.LENGTH_SHORT).show()
            quayLai()
        }

        // Nút X: đóng bảng bộ lọc, quay về A02
        view.findViewById<ImageView>(R.id.dongIV).setOnClickListener { quayLai() }
    }
}
