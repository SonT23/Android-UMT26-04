package vn.edu.umt.tour.ui.khach.khampha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// A21 Yêu thích và đã xem: hai tab, danh sách tour đã lưu, các tour đã xem gần đây.
// Hiện dùng dữ liệu mẫu; lưu bằng Room (B5, B6) làm sau.
class YeuThichFragment : Fragment() {

    private lateinit var yeuThichTabTV: TextView
    private lateinit var daXemTabTV: TextView
    private lateinit var yeuThichGachV: View
    private lateinit var daXemGachV: View
    private lateinit var yeuThichSV: ScrollView
    private lateinit var daXemLuoiRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a21_yeu_thich, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yeuThichTabTV = view.findViewById(R.id.yeuThichTabTV)
        daXemTabTV = view.findViewById(R.id.daXemTabTV)
        yeuThichGachV = view.findViewById(R.id.yeuThichGachV)
        daXemGachV = view.findViewById(R.id.daXemGachV)
        yeuThichSV = view.findViewById(R.id.yeuThichSV)
        daXemLuoiRV = view.findViewById(R.id.daXemLuoiRV)

        // ----- Danh sách yêu thích -----
        val yeuThich = taoYeuThichMau()
        val yeuThichAdapter = TourAdapter(yeuThich)
        val yeuThichRV = view.findViewById<RecyclerView>(R.id.yeuThichRV)
        yeuThichRV.layoutManager = LinearLayoutManager(requireContext())
        yeuThichRV.adapter = yeuThichAdapter
        val chuaCoYeuThichTV = view.findViewById<TextView>(R.id.chuaCoYeuThichTV)
        yeuThichTabTV.text = getString(R.string.a21_tab_yeu_thich, yeuThich.size)

        yeuThichAdapter.onTourClick = { tour ->
            Toast.makeText(requireContext(), tour.ten, Toast.LENGTH_SHORT).show()
        }
        // Bấm tim ở đây nghĩa là bỏ khỏi yêu thích: xóa khỏi danh sách rồi báo adapter
        yeuThichAdapter.onYeuThichClick = { viTri ->
            yeuThich.removeAt(viTri)
            yeuThichAdapter.notifyItemRemoved(viTri)
            yeuThichTabTV.text = getString(R.string.a21_tab_yeu_thich, yeuThich.size)
            if (yeuThich.isEmpty()) {
                chuaCoYeuThichTV.visibility = View.VISIBLE
            }
        }

        // ----- Tour đã xem: dải ngang ở tab 1 và lưới ở tab 2 dùng chung một danh sách -----
        val daXem = taoDaXemMau()
        val daXemAdapter = DaXemAdapter(daXem)
        val daXemRV = view.findViewById<RecyclerView>(R.id.daXemRV)
        daXemRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        daXemRV.adapter = daXemAdapter

        val luoiAdapter = DaXemAdapter(daXem)
        daXemLuoiRV.layoutManager = GridLayoutManager(requireContext(), 3)
        daXemLuoiRV.adapter = luoiAdapter

        daXemAdapter.onClick = { tour ->
            Toast.makeText(requireContext(), tour.ten, Toast.LENGTH_SHORT).show()
        }
        luoiAdapter.onClick = daXemAdapter.onClick

        val chuaXemTV = view.findViewById<TextView>(R.id.chuaXemTV)
        view.findViewById<TextView>(R.id.xoaLichSuTV).setOnClickListener {
            val soLuong = daXem.size
            daXem.clear()
            daXemAdapter.notifyItemRangeRemoved(0, soLuong)
            luoiAdapter.notifyItemRangeRemoved(0, soLuong)
            chuaXemTV.visibility = View.VISIBLE
        }

        // ----- Chuyển tab -----
        view.findViewById<LinearLayout>(R.id.yeuThichTabLL).setOnClickListener { chonTab(true) }
        view.findViewById<LinearLayout>(R.id.daXemTabLL).setOnClickListener { chonTab(false) }
    }

    // Hiện nội dung của tab được chọn, đổi màu chữ và gạch chân
    private fun chonTab(tabYeuThich: Boolean) {
        val mauChon = ContextCompat.getColor(requireContext(), R.color.primary)
        val mauThuong = ContextCompat.getColor(requireContext(), R.color.on_surface_variant)
        if (tabYeuThich) {
            yeuThichSV.visibility = View.VISIBLE
            daXemLuoiRV.visibility = View.GONE
            yeuThichGachV.visibility = View.VISIBLE
            daXemGachV.visibility = View.INVISIBLE
            yeuThichTabTV.setTextColor(mauChon)
            daXemTabTV.setTextColor(mauThuong)
        } else {
            yeuThichSV.visibility = View.GONE
            daXemLuoiRV.visibility = View.VISIBLE
            yeuThichGachV.visibility = View.INVISIBLE
            daXemGachV.visibility = View.VISIBLE
            yeuThichTabTV.setTextColor(mauThuong)
            daXemTabTV.setTextColor(mauChon)
        }
    }
}
