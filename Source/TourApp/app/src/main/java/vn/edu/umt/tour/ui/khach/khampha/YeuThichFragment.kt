package vn.edu.umt.tour.ui.khach.khampha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import vn.edu.umt.tour.data.local.AppDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// A21 Yêu thích và đã xem: hai tab, danh sách tour đã lưu (B5), các tour đã xem gần đây (B6).
// Cả hai đọc từ Room nên vẫn xem được khi mất mạng.
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

        // ----- Danh sách yêu thích (B5): đọc từ bảng yeu_thich trong Room -----
        val yeuThich = ArrayList<TourMau>()
        val yeuThichAdapter = TourAdapter(yeuThich)
        val yeuThichRV = view.findViewById<RecyclerView>(R.id.yeuThichRV)
        yeuThichRV.layoutManager = LinearLayoutManager(requireContext())
        yeuThichRV.adapter = yeuThichAdapter
        val chuaCoYeuThichTV = view.findViewById<TextView>(R.id.chuaCoYeuThichTV)
        yeuThichTabTV.text = getString(R.string.a21_tab_yeu_thich, 0)

        val dao = AppDatabase.layDatabase(requireContext()).yeuThichDao()
        val dinhDangNgay = SimpleDateFormat("dd/MM", Locale.getDefault())
        viewLifecycleOwner.lifecycleScope.launch {
            // Đổi từng dòng YeuThich trong Room thành TourMau để dùng lại TourAdapter của A02
            for (yt in dao.layTatCa()) {
                val ngayLuu = getString(R.string.a21_luu_ngay, dinhDangNgay.format(Date(yt.luuLuc)))
                yeuThich.add(TourMau(yt.maTour, yt.ten, yt.moTa, yt.gia, ngayLuu, true, true))
            }
            yeuThichAdapter.notifyItemRangeInserted(0, yeuThich.size)
            yeuThichTabTV.text = getString(R.string.a21_tab_yeu_thich, yeuThich.size)
            if (yeuThich.isEmpty()) {
                chuaCoYeuThichTV.visibility = View.VISIBLE
            }
        }

        yeuThichAdapter.onTourClick = { tour ->
            moManHinhCon(ChiTietTourFragment.newInstance(tour.ma))
        }
        // Bấm tim ở đây nghĩa là bỏ khỏi yêu thích: xóa khỏi danh sách, báo adapter, xóa trong Room
        yeuThichAdapter.onYeuThichClick = { viTri ->
            val tour = yeuThich.removeAt(viTri)
            yeuThichAdapter.notifyItemRemoved(viTri)
            viewLifecycleOwner.lifecycleScope.launch { dao.xoa(tour.ma) }
            yeuThichTabTV.text = getString(R.string.a21_tab_yeu_thich, yeuThich.size)
            if (yeuThich.isEmpty()) {
                chuaCoYeuThichTV.visibility = View.VISIBLE
            }
        }

        // ----- Tour đã xem (B6): dải ngang ở tab 1 và lưới ở tab 2 dùng chung một danh sách -----
        val daXem = ArrayList<DaXemMau>()
        val daXemAdapter = DaXemAdapter(daXem)
        val daXemRV = view.findViewById<RecyclerView>(R.id.daXemRV)
        daXemRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        daXemRV.adapter = daXemAdapter

        val luoiAdapter = DaXemAdapter(daXem)
        daXemLuoiRV.layoutManager = GridLayoutManager(requireContext(), 3)
        daXemLuoiRV.adapter = luoiAdapter

        daXemAdapter.onClick = { tour ->
            moManHinhCon(ChiTietTourFragment.newInstance(tour.ma))
        }
        luoiAdapter.onClick = daXemAdapter.onClick

        val chuaXemTV = view.findViewById<TextView>(R.id.chuaXemTV)
        val daXemDao = AppDatabase.layDatabase(requireContext()).daXemDao()
        viewLifecycleOwner.lifecycleScope.launch {
            // Đổi từng dòng DaXem trong Room thành DaXemMau để dùng lại DaXemAdapter
            for (dx in daXemDao.layGanDay()) {
                daXem.add(DaXemMau(dx.maTour, dx.ten, nhanThoiGian(dx.xemLuc)))
            }
            daXemAdapter.notifyItemRangeInserted(0, daXem.size)
            luoiAdapter.notifyItemRangeInserted(0, daXem.size)
            if (daXem.isEmpty()) {
                chuaXemTV.visibility = View.VISIBLE
            }
        }

        // Xóa lịch sử: xóa trên màn hình và xóa hết bảng da_xem
        view.findViewById<TextView>(R.id.xoaLichSuTV).setOnClickListener {
            val soLuong = daXem.size
            daXem.clear()
            daXemAdapter.notifyItemRangeRemoved(0, soLuong)
            luoiAdapter.notifyItemRangeRemoved(0, soLuong)
            chuaXemTV.visibility = View.VISIBLE
            viewLifecycleOwner.lifecycleScope.launch { daXemDao.xoaHet() }
        }

        // ----- Chuyển tab -----
        view.findViewById<LinearLayout>(R.id.yeuThichTabLL).setOnClickListener { chonTab(true) }
        view.findViewById<LinearLayout>(R.id.daXemTabLL).setOnClickListener { chonTab(false) }
    }

    // Chữ dưới tên tour đã xem: "Hôm nay", "Hôm qua", còn lại là ngày dạng dd/MM
    private fun nhanThoiGian(xemLuc: Long): String {
        val dinhDang = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val ngayXem = dinhDang.format(Date(xemLuc))
        val homNay = dinhDang.format(Date())
        val homQua = dinhDang.format(Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000))
        if (ngayXem == homNay) {
            return getString(R.string.a21_hom_nay)
        }
        if (ngayXem == homQua) {
            return getString(R.string.a21_hom_qua)
        }
        return SimpleDateFormat("dd/MM", Locale.getDefault()).format(Date(xemLuc))
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
