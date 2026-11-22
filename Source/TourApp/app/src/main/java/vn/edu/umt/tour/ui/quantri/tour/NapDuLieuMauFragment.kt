package vn.edu.umt.tour.ui.quantri.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import vn.edu.umt.tour.R
import vn.edu.umt.tour.data.DuLieuMauFirestore

// Tiện ích cho cả nhóm (chỉ có trong danh sách Xem thử): bấm một nút để nạp dữ liệu mẫu lên Firestore.
// Chạy lại nhiều lần không bị trùng vì mọi document dùng id cố định (xem data/DuLieuMauFirestore.kt).
class NapDuLieuMauFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nap_du_lieu_mau, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val napBtn = view.findViewById<Button>(R.id.napBtn)
        val dangNapPB = view.findViewById<ProgressBar>(R.id.dangNapPB)
        val ketQuaTV = view.findViewById<TextView>(R.id.ketQuaTV)
        view.findViewById<TextView>(R.id.moTaTV).text =
            getString(R.string.nap_mo_ta, DuLieuMauFirestore.soLuong)

        napBtn.setOnClickListener {
            napBtn.isEnabled = false
            dangNapPB.visibility = View.VISIBLE
            ketQuaTV.text = ""
            DuLieuMauFirestore.nap { loi ->
                // Màn hình có thể đã đóng trong lúc chờ mạng
                if (view.isAttachedToWindow) {
                    napBtn.isEnabled = true
                    dangNapPB.visibility = View.GONE
                    ketQuaTV.text = if (loi == null) {
                        getString(R.string.nap_xong)
                    } else {
                        getString(R.string.nap_loi, loi.localizedMessage ?: loi.javaClass.simpleName)
                    }
                }
            }
        }
    }
}
