package vn.edu.umt.tour.ui.khach.khampha

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import vn.edu.umt.tour.data.local.AppDatabase
import vn.edu.umt.tour.data.local.DaXem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// A03 Chi tiết tour: ảnh, tên, giá, lịch trình từng ngày, các đợt khởi hành.
// Nhận mã tour qua newInstance rồi tìm trong dữ liệu mẫu; tuần 9 đổi sang đọc Firestore theo mã.
class ChiTietTourFragment : Fragment() {

    private var yeuThich = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a03_chi_tiet_tour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lấy mã tour màn hình trước gửi sang, rồi tìm tour theo mã
        val maTour = requireArguments().getString(MA_TOUR)!!
        val tour = timTourMau(maTour)
        view.findViewById<TextView>(R.id.tenTV).text = tour.ten
        view.findViewById<TextView>(R.id.diaDiemTV).text = tour.moTa
        view.findViewById<TextView>(R.id.giaTV).text = tour.gia
        view.findViewById<TextView>(R.id.soAnhTV).text = getString(R.string.a03_so_anh, 1, 5)

        // B6: ghi tour này vào bảng da_xem (xem lại thì cập nhật thời điểm), chỉ giữ 20 tour mới nhất
        val daXemDao = AppDatabase.layDatabase(requireContext()).daXemDao()
        viewLifecycleOwner.lifecycleScope.launch {
            daXemDao.them(DaXem(maTour, tour.ten, System.currentTimeMillis()))
            daXemDao.chiGiu20()
        }

        // Lịch trình từng ngày
        val lichTrinhRV = view.findViewById<RecyclerView>(R.id.lichTrinhRV)
        lichTrinhRV.layoutManager = LinearLayoutManager(requireContext())
        lichTrinhRV.adapter = LichTrinhAdapter(taoLichTrinhMau())

        // Đợt khởi hành
        val dotAdapter = DotKhoiHanhAdapter(taoDotMau())
        val dotRV = view.findViewById<RecyclerView>(R.id.dotRV)
        dotRV.layoutManager = LinearLayoutManager(requireContext())
        dotRV.adapter = dotAdapter
        dotAdapter.onDatClick = { dot ->
            // Sau này mở A04 Đặt tour (module C của Thịnh)
            Toast.makeText(requireContext(), dot.ngay, Toast.LENGTH_SHORT).show()
        }

        // B5 nút tim: đọc trạng thái từ Room khi mở màn hình, bấm thì lưu hoặc xóa trong Room
        val yeuThichIV = view.findViewById<ImageView>(R.id.yeuThichIV)
        val dao = AppDatabase.layDatabase(requireContext()).yeuThichDao()
        viewLifecycleOwner.lifecycleScope.launch {
            yeuThich = dao.daThich(maTour)
            veTim(yeuThichIV)
        }
        yeuThichIV.setOnClickListener {
            yeuThich = !yeuThich
            veTim(yeuThichIV)
            tour.yeuThich = yeuThich
            viewLifecycleOwner.lifecycleScope.launch { luuYeuThich(dao, tour) }
        }

        // B7 Chia sẻ tour: gửi tên, nơi đi, giá cho app khác (Zalo, Messenger, Gmail…)
        view.findViewById<ImageView>(R.id.chiaSeIV).setOnClickListener {
            val noiDung = getString(R.string.a03_noi_dung_chia_se, tour.ten, tour.moTa, tour.gia)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, tour.ten)
            intent.putExtra(Intent.EXTRA_TEXT, noiDung)
            // createChooser luôn hiện bảng chọn app, kể cả khi người dùng đã đặt app mặc định
            startActivity(Intent.createChooser(intent, getString(R.string.a03_chia_se_qua)))
        }

        // Mở A22 Bản đồ lịch trình của tour này
        view.findViewById<Button>(R.id.banDoBtn).setOnClickListener {
            moManHinhCon(BanDoLichTrinhFragment.newInstance(maTour))
        }

        // Nút quay lại: về màn hình trước (A01, A02 hoặc A21)
        view.findViewById<ImageView>(R.id.quayLaiIV).setOnClickListener { quayLai() }
    }

    // Tim đầy khi đã thích, tim rỗng khi chưa
    private fun veTim(yeuThichIV: ImageView) {
        if (yeuThich) {
            yeuThichIV.setImageResource(R.drawable.ic_favorite)
        } else {
            yeuThichIV.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    companion object {
        private const val MA_TOUR = "ma_tour"

        // Màn hình khác mở A03 bằng: ChiTietTourFragment.newInstance(maTour)
        fun newInstance(maTour: String) = ChiTietTourFragment().apply {
            arguments = bundleOf(MA_TOUR to maTour)
        }
    }
}
