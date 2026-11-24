package vn.edu.umt.tour.ui.quantri.don

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import vn.edu.umt.tour.R

class DanhSachDonFragment : Fragment() {

    private lateinit var adapter: DonAdapter
    private val danhSachGoc = taoDanhSachDonMau()
    private val danhSachHienThi = mutableListOf<DonDatTour>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a16_danh_sach_don, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.danhSachDonRV)
        rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = DonAdapter(danhSachHienThi)
        rv.adapter = adapter

        danhSachHienThi.addAll(danhSachGoc)
        adapter.notifyDataSetChanged()

        adapter.onDonClick = { don ->
            moManHinhCon(XuLyDonFragment.newInstance(don.maDon))
        }

        val timKiemET = view.findViewById<EditText>(R.id.timKiemET)
        timKiemET.addTextChangedListener { text ->
            locDanhSach(text.toString(), layTrangThaiHienTai(view))
        }

        // Đổi chip nào cũng lọc lại. Listener này còn được gọi khi xoay máy
        // (Android tự chọn lại chip cũ), nên danh sách lọc lại đúng.
        view.findViewById<ChipGroup>(R.id.chipGroup).setOnCheckedStateChangeListener { _, _ ->
            locDanhSach(timKiemET.text.toString(), layTrangThaiHienTai(view))
        }
    }

    private fun layTrangThaiHienTai(view: View): String? {
        return when {
            view.findViewById<Chip>(R.id.chipChoXacNhan).isChecked -> "Chờ xác nhận"
            view.findViewById<Chip>(R.id.chipDaXacNhan).isChecked -> "Đã xác nhận"
            view.findViewById<Chip>(R.id.chipHoanThanh).isChecked -> "Hoàn thành"
            view.findViewById<Chip>(R.id.chipDaHuy).isChecked -> "Đã hủy"
            else -> null
        }
    }

    private fun locDanhSach(keyword: String, trangThai: String?) {
        danhSachHienThi.clear()
        danhSachHienThi.addAll(danhSachGoc.filter { don ->
            val matchKeyword = don.maDon.contains(keyword, true) ||
                    don.tenKhachHang.contains(keyword, true) ||
                    don.sdt.contains(keyword, true)
            matchKeyword && (trangThai == null || don.trangThai == trangThai)
        })
        adapter.notifyDataSetChanged()

        // Không còn đơn nào thì hiện chữ báo
        val khongCoDonTV = requireView().findViewById<TextView>(R.id.khongCoDonTV)
        if (danhSachHienThi.isEmpty()) {
            khongCoDonTV.visibility = View.VISIBLE
        } else {
            khongCoDonTV.visibility = View.GONE
        }
    }
}
