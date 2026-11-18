package vn.edu.umt.tour.ui.quantri.don.uie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import vn.edu.umt.tour.R

class DanhSachDonFragment : Fragment() {

    private lateinit var adapter: DonAdapter
    private val danhSachGoc = taoDanhSachDonMau()
    private val danhSachHienThi = mutableListOf<DonDatTour>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_e_a16_danh_sach_don, container, false)
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
            Toast.makeText(requireContext(), "Xử lý đơn: ${don.maDon}", Toast.LENGTH_SHORT).show()
        }

        val timKiemET = view.findViewById<EditText>(R.id.timKiemET)
        timKiemET.addTextChangedListener { text ->
            locDanhSach(text.toString(), layTrangThaiHienTai(view))
        }

        view.findViewById<Chip>(R.id.chipTatCa).setOnClickListener {
            locDanhSach(timKiemET.text.toString(), null)
        }
        view.findViewById<Chip>(R.id.chipChoXacNhan).setOnClickListener {
            locDanhSach(timKiemET.text.toString(), "Chờ xác nhận")
        }
        view.findViewById<Chip>(R.id.chipDaXacNhan).setOnClickListener {
            locDanhSach(timKiemET.text.toString(), "Đã xác nhận")
        }
        view.findViewById<Chip>(R.id.chipHoanThanh).setOnClickListener {
            locDanhSach(timKiemET.text.toString(), "Hoàn thành")
        }
        view.findViewById<Chip>(R.id.chipDaHuy).setOnClickListener {
            locDanhSach(timKiemET.text.toString(), "Đã hủy")
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
    }
}
