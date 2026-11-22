package vn.edu.umt.tour.ui.quantri.don

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import vn.edu.umt.tour.R

class QuetQrFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a23_quet_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.btnDong).setOnClickListener { quayLai() }

        view.findViewById<MaterialButton>(R.id.btnNhapThuCong).setOnClickListener {
            val maDonET = EditText(requireContext()).apply {
                inputType = InputType.TYPE_CLASS_TEXT
                hint = getString(R.string.a23_hint_ma_don)
            }
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.a23_nhap_ma_don)
                .setView(maDonET)
                .setNegativeButton(R.string.e_btn_huy, null)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    val maDon = maDonET.text.toString().trim()
                    if (maDon.isEmpty()) {
                        Toast.makeText(requireContext(), R.string.a23_ma_don_trong, Toast.LENGTH_SHORT).show()
                    } else {
                        moManHinhCon(XuLyDonFragment.newInstance(maDon))
                    }
                }
                .show()
        }
        view.findViewById<MaterialButton>(R.id.btnXemDanhSach).setOnClickListener {
            Toast.makeText(requireContext(), "Xem danh sách đã check-in", Toast.LENGTH_SHORT).show()
        }
    }
}
