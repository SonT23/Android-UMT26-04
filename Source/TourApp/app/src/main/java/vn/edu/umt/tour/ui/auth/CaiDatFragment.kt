package vn.edu.umt.tour.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import vn.edu.umt.tour.R

// A19 Cài đặt: ngôn ngữ, giao diện sáng / tối, thông báo, mở khóa bằng vân tay.
class CaiDatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a19_cai_dat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.quayLaiIV).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Chọn ngôn ngữ làm sau
        view.findViewById<LinearLayout>(R.id.ngonNguLL).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a19_ngon_ngu, Toast.LENGTH_SHORT).show()
        }

        // Giao diện Sáng / Tối / Theo máy: đổi ngay cho cả app.
        // Chưa lưu lại, tắt app mở lại sẽ về "Theo máy". Lưu lựa chọn làm sau.
        val giaoDienRG = view.findViewById<RadioGroup>(R.id.giaoDienRG)
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_NO -> giaoDienRG.check(R.id.sangRB)
            AppCompatDelegate.MODE_NIGHT_YES -> giaoDienRG.check(R.id.toiRB)
            else -> giaoDienRG.check(R.id.theoMayRB)
        }
        giaoDienRG.setOnCheckedChangeListener { _, idDuocChon ->
            val cheDo = when (idDuocChon) {
                R.id.sangRB -> AppCompatDelegate.MODE_NIGHT_NO
                R.id.toiRB -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            AppCompatDelegate.setDefaultNightMode(cheDo)
        }

        // Ba công tắc thông báo và vân tay hiện chỉ bật / tắt trên giao diện, chức năng thật làm sau.
    }
}
