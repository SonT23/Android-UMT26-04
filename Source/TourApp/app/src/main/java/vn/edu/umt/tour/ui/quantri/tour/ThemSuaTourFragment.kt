package vn.edu.umt.tour.ui.quantri.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import vn.edu.umt.tour.R
import java.util.Collections

// A14 Thêm, sửa tour: ảnh, thông tin tour, hai công tắc, lịch trình nhiều ngày.
// Đã có: điền sẵn tour đang sửa, kiểm tra dữ liệu khi bấm Lưu.
// D5 Lịch trình: thêm ngày, sửa tiêu đề ngày, xóa ngày (đánh số lại), kéo thả đổi thứ tự.
// Chọn ảnh thật, lưu nháp khi mất mạng làm sau.
class ThemSuaTourFragment : Fragment() {

    private val ngay = taoNgayMau()
    private var adapter: NgayAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a14_them_sua_tour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tenTIL = view.findViewById<TextInputLayout>(R.id.tenTIL)
        val tenET = view.findViewById<TextInputEditText>(R.id.tenET)
        val soNgayET = view.findViewById<TextInputEditText>(R.id.soNgayET)
        val soDemTIL = view.findViewById<TextInputLayout>(R.id.soDemTIL)
        val soDemET = view.findViewById<TextInputEditText>(R.id.soDemET)

        // Điền sẵn tour đang sửa
        tenET.setText(TEN_TOUR_DANG_XEM)
        soNgayET.setText(SO_NGAY_DANG_SUA.toString())
        soDemET.setText(SO_DEM_DANG_SUA.toString())

        // Spinner điểm đến: lấy tên các điểm đến mẫu của A12
        val tenDiemDen = ArrayList<String>()
        for (diem in taoDiemDenMau()) {
            tenDiemDen.add(diem.ten)
        }
        val diemDenSP = view.findViewById<Spinner>(R.id.diemDenSP)
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tenDiemDen)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                diemDenSP.adapter = adapter
            }
        diemDenSP.setSelection(tenDiemDen.indexOf(DIEM_DEN_DANG_SUA))

        // Lịch trình
        val adapter = NgayAdapter(ngay)
        val ngayRV = view.findViewById<RecyclerView>(R.id.ngayRV)
        ngayRV.layoutManager = LinearLayoutManager(requireContext())
        ngayRV.adapter = adapter
        this.adapter = adapter

        // Nút tùy chọn của ngày: menu Sửa / Xóa
        adapter.onTuyChonClick = { nut, viTri ->
            val menu = PopupMenu(requireContext(), nut)
            menu.menuInflater.inflate(R.menu.menu_a14_ngay, menu.menu)
            menu.setOnMenuItemClickListener { muc ->
                when (muc.itemId) {
                    R.id.suaNgayMenu -> suaNgay(viTri)
                    R.id.xoaNgayMenu -> hoiXoaNgay(viTri)
                }
                true
            }
            menu.show()
        }

        // Kéo thả đổi thứ tự: chạm giữ biểu tượng kéo ở đầu dòng rồi kéo lên, xuống
        val keoTha = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val tu = viewHolder.bindingAdapterPosition
                val den = target.bindingAdapterPosition
                if (tu == RecyclerView.NO_POSITION || den == RecyclerView.NO_POSITION) return false
                Collections.swap(ngay, tu, den)
                adapter.notifyItemMoved(tu, den)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Không dùng vuốt ngang
            }

            // Chỉ kéo khi chạm biểu tượng kéo, không kéo bằng cách giữ lâu cả dòng
            override fun isLongPressDragEnabled(): Boolean = false

            // Thả tay: đánh số lại cả lịch trình theo thứ tự mới
            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                danhSoLai(0)
                recyclerView.post { adapter.notifyItemRangeChanged(0, ngay.size) }
            }
        })
        keoTha.attachToRecyclerView(ngayRV)
        adapter.onBatDauKeo = { viewHolder -> keoTha.startDrag(viewHolder) }

        // Thêm ngày: thêm một phần tử vào cuối danh sách rồi báo adapter có dòng mới
        view.findViewById<Button>(R.id.themNgayBtn).setOnClickListener {
            ngay.add(NgayMau(ngay.size + 1, getString(R.string.a14_ngay_moi)))
            adapter.notifyItemInserted(ngay.size - 1)
        }

        // Thêm ảnh: chọn ảnh từ máy hoặc chụp bằng camera làm sau
        view.findViewById<LinearLayout>(R.id.themAnhLL).setOnClickListener {
            Toast.makeText(requireContext(), R.string.a14_them_anh, Toast.LENGTH_SHORT).show()
        }

        // Lưu: tên không được trống, số đêm không được lớn hơn số ngày
        view.findViewById<TextView>(R.id.luuTV).setOnClickListener {
            val ten = tenET.text.toString().trim()
            // toIntOrNull trả về null khi ô trống, khi đó coi như 0
            val soNgay = soNgayET.text.toString().toIntOrNull() ?: 0
            val soDem = soDemET.text.toString().toIntOrNull() ?: 0
            var hopLe = true

            if (ten.isEmpty()) {
                tenTIL.error = getString(R.string.a14_loi_ten)
                hopLe = false
            } else {
                tenTIL.error = null
            }

            if (soDem > soNgay) {
                soDemTIL.error = getString(R.string.a14_loi_so_dem)
                hopLe = false
            } else {
                soDemTIL.error = null
            }

            if (hopLe) {
                val thongBao = getString(R.string.a14_da_luu, ten)
                Toast.makeText(requireContext(), thongBao, Toast.LENGTH_SHORT).show()
            }
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

    // Đánh số lại thuTu từ vị trí tuViTri tới cuối danh sách (ngày đầu là 1)
    private fun danhSoLai(tuViTri: Int) {
        for (i in tuViTri until ngay.size) {
            ngay[i].thuTu = i + 1
        }
    }

    // Sửa: hộp thoại có ô nhập tiêu đề, điền sẵn tiêu đề cũ
    private fun suaNgay(viTri: Int) {
        val mot = ngay[viTri]
        val noiDung = layoutInflater.inflate(R.layout.dialog_a14_sua_ngay, null)
        val tieuDeTIL = noiDung.findViewById<TextInputLayout>(R.id.tieuDeTIL)
        val tieuDeET = noiDung.findViewById<TextInputEditText>(R.id.tieuDeET)
        tieuDeET.setText(mot.tieuDe)
        tieuDeET.setSelection(mot.tieuDe.length)

        val hopThoai = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.a14_sua_ngay, mot.thuTu))
            .setView(noiDung)
            .setNegativeButton(R.string.a14_huy, null)
            .setPositiveButton(R.string.a14_luu_ngay, null)
            .show()

        // Gắn xử lý nút Lưu sau khi show() để tiêu đề trống thì báo lỗi mà không đóng hộp thoại
        hopThoai.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val tieuDe = tieuDeET.text.toString().trim()
            if (tieuDe.isEmpty()) {
                tieuDeTIL.error = getString(R.string.a14_loi_tieu_de_ngay)
            } else {
                mot.tieuDe = tieuDe
                adapter?.notifyItemChanged(ngay.indexOf(mot))
                hopThoai.dismiss()
            }
        }
    }

    // Xóa: hỏi lại, xóa xong đánh số lại các ngày phía sau. Lịch trình phải còn ít nhất một ngày.
    private fun hoiXoaNgay(viTri: Int) {
        if (ngay.size <= 1) {
            Toast.makeText(requireContext(), R.string.a14_can_mot_ngay, Toast.LENGTH_SHORT).show()
            return
        }
        val mot = ngay[viTri]
        val soNgayBiXoa = mot.thuTu
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.a14_xoa_ngay_hoi, soNgayBiXoa))
            .setMessage(getString(R.string.a14_xoa_ngay_giai_thich, mot.tieuDe))
            .setNegativeButton(R.string.a14_huy, null)
            .setPositiveButton(R.string.a14_xoa) { _, _ -> xoaNgay(mot, soNgayBiXoa) }
            .show()
    }

    private fun xoaNgay(mot: NgayMau, soNgayBiXoa: Int) {
        val viTri = ngay.indexOf(mot)
        if (viTri == -1) return
        ngay.removeAt(viTri)
        danhSoLai(viTri)
        adapter?.notifyItemRemoved(viTri)
        adapter?.notifyItemRangeChanged(viTri, ngay.size - viTri)
        val thongBao = getString(R.string.a14_da_xoa_ngay, soNgayBiXoa)
        Toast.makeText(requireContext(), thongBao, Toast.LENGTH_SHORT).show()
    }
}
