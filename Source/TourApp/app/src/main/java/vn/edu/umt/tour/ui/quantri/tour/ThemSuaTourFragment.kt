package vn.edu.umt.tour.ui.quantri.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import vn.edu.umt.tour.R

// A14 Thêm, sửa tour: ảnh, thông tin tour, hai công tắc, lịch trình nhiều ngày.
// Giai đoạn này: điền sẵn tour đang sửa, kiểm tra dữ liệu khi bấm Lưu, thêm ngày vào lịch trình.
// Chọn ảnh thật, lưu nháp khi mất mạng, kéo thả đổi thứ tự ngày làm sau.
class ThemSuaTourFragment : Fragment() {

    private val ngay = taoNgayMau()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a14_them_sua_tour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tenTIL = view.findViewById<TextInputLayout>(R.id.tenTIL)
        val tenET = view.findViewById<TextInputEditText>(R.id.tenET)
        val soNgayET = view.findViewById<TextInputEditText>(R.id.soNgayET)
        val soDemTIL = view.findViewById<TextInputLayout>(R.id.soDemTIL)
        val soDemET = view.findViewById<TextInputEditText>(R.id.soDemET)

        // Điền sẵn tour đang sửa
        tenET.setText(TEN_TOUR_DANG_XEM)
        soNgayET.setText(SO_NGAY_DANG_SUA.toString())
        soDemET.setText(SO_DEM_DANG_SUA.toString())

        // Spinner điểm đến: lấy tên các điểm đến mẫu của A12
        val tenDiemDen = ArrayList<String>()
        for (diem in taoDiemDenMau()) {
            tenDiemDen.add(diem.ten)
        }
        val diemDenSP = view.findViewById<Spinner>(R.id.diemDenSP)
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tenDiemDen)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                diemDenSP.adapter = adapter
            }
        diemDenSP.setSelection(tenDiemDen.indexOf(DIEM_DEN_DANG_SUA))

        // Lịch trình
        val adapter = NgayAdapter(ngay)
        val ngayRV = view.findViewById<RecyclerView>(R.id.ngayRV)
        ngayRV.layoutManager = LinearLayoutManager(requireContext())
        ngayRV.adapter = adapter
        adapter.onTuyChonClick = { mot ->
            val thongBao = getString(R.string.a14_ngay_so, mot.thuTu) + ": " + mot.tieuDe
            Toast.makeText(requireContext(), thongBao, Toast.LENGTH_SHORT).show()
        }

        // Thêm ngày: thêm một phần tử vào cuối danh sách rồi báo adapter có dòng mới
        view.findViewById<Button>(R.id.themNgayBtn).setOnClickListener {
            ngay.add(NgayMau(ngay.size + 1, getString(R.string.a14_ngay_moi)))
            adapter.notifyItemInserted(ngay.size - 1)
        }

        // Thêm ảnh: chọn ảnh từ máy hoặc chụp bằng camera làm sau
        view.findViewById<LinearLayout>(R.id.themAnhLL).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a14_them_anh, Toast.LENGTH_SHORT).show()
        }

        // Lưu: tên không được trống, số đêm không được lớn hơn số ngày
        view.findViewById<TextView>(R.id.luuTV).setOnClickListener {
            val ten = tenET.text.toString().trim()
            // toIntOrNull trả về null khi ô trống, khi đó coi như 0
            val soNgay = soNgayET.text.toString().toIntOrNull() ?: 0
            val soDem = soDemET.text.toString().toIntOrNull() ?: 0
            var hopLe = true

            if (ten.isEmpty()) {
                tenTIL.error = getString(R.string.a14_loi_ten)
                hopLe = false
            } else {
                tenTIL.error = null
            }

            if (soDem > soNgay) {
                soDemTIL.error = getString(R.string.a14_loi_so_dem)
                hopLe = false
            } else {
                soDemTIL.error = null
            }

            if (hopLe) {
                val thongBao = getString(R.string.a14_da_luu, ten)
                Toast.makeText(requireContext(), thongBao, Toast.LENGTH_SHORT).show()
            }
        }

        // Quay lại: tạm thời đóng màn hình xem thử
        view.findViewById<ImageView>(R.id.quayLaiIV).setOnClickListener {
            requireActivity().finish()
        }
    }
}
