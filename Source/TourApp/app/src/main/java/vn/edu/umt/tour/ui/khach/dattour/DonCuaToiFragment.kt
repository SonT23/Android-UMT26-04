package vn.edu.umt.tour.ui.khach.dattour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

class DonCuaToiFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a08_don_cua_toi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvDonHang = view.findViewById<RecyclerView>(R.id.rv_don_hang)
        rvDonHang.layoutManager = LinearLayoutManager(requireContext())
        rvDonHang.adapter = DonAdapter(danhSachDonMau) { don ->
            moManHinhCon(ChiTietDonFragment.newInstance(don.maDon))
        }
    }
}