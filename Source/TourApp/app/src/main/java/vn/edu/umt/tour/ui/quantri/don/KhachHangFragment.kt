package vn.edu.umt.tour.ui.quantri.don

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import vn.edu.umt.tour.R

class KhachHangFragment : Fragment() {

    private lateinit var adapter: KhachHangAdapter
    private val danhSachGoc = taoDanhSachKhachHangMau()
    private val danhSachHienThi = mutableListOf<KhachHangQuanTri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a18_khach_hang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val soKhachTV = view.findViewById<TextView>(R.id.soKhachTV)
        val rv = view.findViewById<RecyclerView>(R.id.khachHangRV)
        rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = KhachHangAdapter(danhSachHienThi)
        rv.adapter = adapter

        danhSachHienThi.addAll(danhSachGoc)
        soKhachTV.text = "${danhSachGoc.size} khách hàng"
        adapter.notifyDataSetChanged()

        adapter.onKhachClick = { khach ->
            Toast.makeText(requireContext(), "Khách hàng: ${khach.ten}", Toast.LENGTH_SHORT).show()
        }

        val timKiemET = view.findViewById<EditText>(R.id.timKiemET)
        timKiemET.addTextChangedListener { text ->
            locDanhSach(text.toString(), layTrangThaiHienTai(view))
        }

        view.findViewById<Chip>(R.id.chipTatCa).setOnClickListener {
            locDanhSach(timKiemET.text.toString(), null)
        }
        view.findViewById<Chip>(R.id.chipHoatDong).setOnClickListener {
            locDanhSach(timKiemET.text.toString(), false)
        }
        view.findViewById<Chip>(R.id.chipBiKhoa).setOnClickListener {
            locDanhSach(timKiemET.text.toString(), true)
        }
    }

    private fun layTrangThaiHienTai(view: View): Boolean? {
        return when {
            view.findViewById<Chip>(R.id.chipHoatDong).isChecked -> false
            view.findViewById<Chip>(R.id.chipBiKhoa).isChecked -> true
            else -> null
        }
    }

    private fun locDanhSach(keyword: String, biKhoa: Boolean?) {
        danhSachHienThi.clear()
        danhSachHienThi.addAll(danhSachGoc.filter { khach ->
            val matchKeyword = khach.ten.contains(keyword, true) ||
                    khach.sdt.contains(keyword, true) ||
                    khach.email.contains(keyword, true)
            val matchTrangThai = when (biKhoa) {
                true -> khach.biBanTai
                false -> !khach.biBanTai
                null -> true
            }
            matchKeyword && matchTrangThai
        })
        adapter.notifyDataSetChanged()
    }
}
