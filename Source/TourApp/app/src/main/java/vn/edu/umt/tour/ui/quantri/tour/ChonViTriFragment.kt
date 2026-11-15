package vn.edu.umt.tour.ui.quantri.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import vn.edu.umt.tour.R

// A24 Chọn vị trí trên bản đồ cho điểm đến (A12) và điểm tập trung (A14).
// Giai đoạn này bản đồ là hình giữ chỗ. Tuần 9 thay View banDoV bằng Google Maps:
// kéo bản đồ thì ghim đứng yên, dừng kéo thì Geocoder điền tên địa điểm (chức năng D8).
class ChonViTriFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a24_chon_vi_tri, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Vị trí đang ghim (dữ liệu mẫu, dùng chung tọa độ với A12 Sửa điểm đến)
        view.findViewById<TextView>(R.id.tenViTriTV).text = TEN_VI_TRI
        view.findViewById<TextView>(R.id.toaDoTV).text = TOA_DO_DANG_SUA

        // Tìm địa chỉ: bấm nút tìm trên bàn phím. Tìm thật bằng Geocoder làm sau.
        val timET = view.findViewById<EditText>(R.id.timET)
        timET.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Toast.makeText(requireContext(), timET.text.toString(), Toast.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        }

        // Về vị trí hiện tại của máy (cần quyền vị trí) làm sau
        view.findViewById<ImageView>(R.id.viTriCuaToiIV).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a24_vi_tri_cua_toi, Toast.LENGTH_SHORT).show()
        }

        // Dùng vị trí này: sau này gửi tọa độ về A12 hoặc A14 rồi đóng màn hình
        view.findViewById<Button>(R.id.dungViTriBtn).setOnClickListener {
            val thongBao = getString(R.string.a24_da_chon, TEN_VI_TRI, TOA_DO_DANG_SUA)
            Toast.makeText(requireContext(), thongBao, Toast.LENGTH_SHORT).show()
        }

        // Quay lại: tạm thời đóng màn hình xem thử
        view.findViewById<ImageView>(R.id.quayLaiIV).setOnClickListener {
            requireActivity().finish()
        }
    }
}
