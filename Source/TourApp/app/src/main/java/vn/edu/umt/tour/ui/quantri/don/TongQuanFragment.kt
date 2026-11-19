package vn.edu.umt.tour.ui.quantri.don

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

class TongQuanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a11_tong_quan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val danhSachDon = taoDanhSachDonMau()
        val soChoXacNhan = danhSachDon.count { it.trangThai == "Chờ xác nhận" }
        val soDaXacNhan = danhSachDon.count { it.trangThai == "Đã xác nhận" }

        view.findViewById<TextView>(R.id.soChoXacNhanTV).text = soChoXacNhan.toString()
        view.findViewById<TextView>(R.id.soDaXacNhanTV).text = soDaXacNhan.toString()
        view.findViewById<TextView>(R.id.soDoanhThuTV).text = "152.400.000đ"
        view.findViewById<TextView>(R.id.soDotKhoiHanhTV).text = "6"

        val rv = view.findViewById<RecyclerView>(R.id.tourBanChayRV)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = TourBanChayAdapter(taoDanhSachTourBanChayMau())

        view.findViewById<View>(R.id.btnThongBao).setOnClickListener {
            Toast.makeText(requireContext(), "Thông báo", Toast.LENGTH_SHORT).show()
        }
    }
}
