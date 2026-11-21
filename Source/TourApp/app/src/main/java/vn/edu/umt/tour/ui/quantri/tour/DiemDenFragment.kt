package vn.edu.umt.tour.ui.quantri.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import vn.edu.umt.tour.R

// A12 Điểm đến (D1): danh sách điểm đến, thêm, sửa, xóa.
// Thêm và sửa mở SuaDiemDenFragment; bấm Lưu ở đó thì kết quả trả về đây bằng Fragment Result,
// màn hình này cập nhật danh sách rồi báo adapter đúng dòng thay đổi.
// Dữ liệu đang lấy từ KhoDiemDenMau (DuLieuMau.kt); tuần 9 đổi sang Firestore.
class DiemDenFragment : Fragment() {

    private val diemDen = KhoDiemDenMau.ds
    private var adapter: DiemDenAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Nhận kết quả từ SuaDiemDenFragment. Kết quả được giao khi màn hình này hiện lại,
        // lúc đó onViewCreated đã chạy nên adapter đã có.
        setFragmentResultListener(SuaDiemDenFragment.KET_QUA) { _, bundle ->
            val ma = bundle.getString(SuaDiemDenFragment.MA).orEmpty()
            val ten = bundle.getString(SuaDiemDenFragment.TEN).orEmpty()
            val tinh = bundle.getString(SuaDiemDenFragment.TINH).orEmpty()
            val moTa = bundle.getString(SuaDiemDenFragment.MO_TA).orEmpty()
            val toaDo = bundle.getString(SuaDiemDenFragment.TOA_DO).orEmpty()

            val viTri = diemDen.indexOfFirst { it.ma == ma }
            if (viTri == -1) {
                // Điểm đến mới: thêm vào đầu danh sách để thấy ngay
                diemDen.add(0, DiemDenMau(KhoDiemDenMau.maMoi(), ten, tinh, moTa, toaDo, 0))
                adapter?.notifyItemInserted(0)
                view?.findViewById<RecyclerView>(R.id.diemDenRV)?.scrollToPosition(0)
                Toast.makeText(requireContext(), getString(R.string.a12_da_them, ten), Toast.LENGTH_SHORT).show()
            } else {
                val diem = diemDen[viTri]
                diem.ten = ten
                diem.tinh = tinh
                diem.moTa = moTa
                diem.toaDo = toaDo
                adapter?.notifyItemChanged(viTri)
                Toast.makeText(requireContext(), getString(R.string.a12_da_luu, ten), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a12_diem_den, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DiemDenAdapter(diemDen)
        this.adapter = adapter
        val diemDenRV = view.findViewById<RecyclerView>(R.id.diemDenRV)
        diemDenRV.layoutManager = LinearLayoutManager(requireContext())
        diemDenRV.adapter = adapter

        // Bấm vào dòng: mở màn hình sửa với dữ liệu của điểm đến đó
        adapter.onDiemDenClick = { diem -> moManHinhSua(diem.ma) }

        // Nút tùy chọn của dòng: menu Sửa / Xóa
        adapter.onTuyChonClick = { nut, viTri ->
            val menu = PopupMenu(requireContext(), nut)
            menu.menuInflater.inflate(R.menu.menu_a12_diem_den, menu.menu)
            menu.setOnMenuItemClickListener { muc ->
                when (muc.itemId) {
                    R.id.suaMenu -> moManHinhSua(diemDen[viTri].ma)
                    R.id.xoaMenu -> hoiXoa(viTri)
                }
                true
            }
            menu.show()
        }

        // Nút thêm: mở màn hình sửa với các ô trống
        view.findViewById<ImageView>(R.id.themIV).setOnClickListener {
            moManHinhSua(null)
        }

        // Quay lại: như bấm nút Back của máy (README, mục "Nút Quay lại")
        view.findViewById<ImageView>(R.id.quayLaiIV).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    // ma = null là thêm mới
    private fun moManHinhSua(ma: String?) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.khung, SuaDiemDenFragment.newInstance(ma))
            .addToBackStack(null)
            .commit()
    }

    // Xóa: điểm đến đang có tour thì không cho xóa; còn lại hỏi lại trước khi xóa
    private fun hoiXoa(viTri: Int) {
        val diem = diemDen[viTri]
        if (diem.soTour > 0) {
            val thongBao = getString(R.string.a12_khong_xoa_duoc, diem.ten, diem.soTour)
            Toast.makeText(requireContext(), thongBao, Toast.LENGTH_SHORT).show()
            return
        }
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.a12_xoa_hoi, diem.ten))
            .setMessage(R.string.a12_xoa_giai_thich)
            .setNegativeButton(R.string.a12_xoa_khong, null)
            .setPositiveButton(R.string.a12_xoa_dong_y) { _, _ ->
                // Tìm lại vị trí theo mã, phòng khi danh sách đã đổi trong lúc hộp thoại đang mở
                val viTriHienTai = diemDen.indexOfFirst { it.ma == diem.ma }
                if (viTriHienTai != -1) {
                    diemDen.removeAt(viTriHienTai)
                    adapter?.notifyItemRemoved(viTriHienTai)
                    Toast.makeText(requireContext(), getString(R.string.a12_da_xoa, diem.ten), Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }
}
