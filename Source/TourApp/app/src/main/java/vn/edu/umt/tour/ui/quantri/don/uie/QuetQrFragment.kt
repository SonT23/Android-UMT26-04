package vn.edu.umt.tour.ui.quantri.don.uie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import vn.edu.umt.tour.R

class QuetQrFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_e_a23_quet_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.btnNhapThuCong).setOnClickListener {
            Toast.makeText(requireContext(), "Mở nhập mã đơn thủ công", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<MaterialButton>(R.id.btnXemDanhSach).setOnClickListener {
            Toast.makeText(requireContext(), "Xem danh sách đã check-in", Toast.LENGTH_SHORT).show()
        }
    }
}
