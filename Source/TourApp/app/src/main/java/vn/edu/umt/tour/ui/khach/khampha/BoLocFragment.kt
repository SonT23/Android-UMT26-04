package vn.edu.umt.tour.ui.khach.khampha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import vn.edu.umt.tour.R

// A02 Bộ lọc (B2): điểm đến, khoảng giá, tháng khởi hành, số ngày, sắp xếp.
// Nhận bộ lọc A02 đang dùng qua newInstance, bấm "Xem N tour" thì gửi bộ lọc mới về A02.
class BoLocFragment : Fragment() {

    private val tatCa = taoTatCaTourMau()
    private var tuKhoa = ""
    private lateinit var diemDen: ArrayList<String>

    private lateinit var diemDenSP: Spinner
    private lateinit var giaTuET: EditText
    private lateinit var giaDenET: EditText
    private lateinit var giaDenTIL: TextInputLayout
    private lateinit var thangCG: ChipGroup
    private lateinit var soNgayCG: ChipGroup
    private lateinit var sapXepCG: ChipGroup
    private lateinit var xemBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_a02_bo_loc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diemDenSP = view.findViewById(R.id.diemDenSP)
        giaTuET = view.findViewById(R.id.giaTuET)
        giaDenET = view.findViewById(R.id.giaDenET)
        giaDenTIL = view.findViewById(R.id.giaDenTIL)
        thangCG = view.findViewById(R.id.thangCG)
        soNgayCG = view.findViewById(R.id.soNgayCG)
        sapXepCG = view.findViewById(R.id.sapXepCG)
        xemBtn = view.findViewById(R.id.xemBtn)

        // Spinner điểm đến: dòng đầu là "Tất cả điểm đến", sau đó là các điểm đến mẫu
        diemDen = arrayListOf(getString(R.string.a02_tat_ca_diem_den))
        diemDen.addAll(taoDiemDenMau())
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, diemDen)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        diemDenSP.adapter = adapter

        // Hiện lại bộ lọc A02 đang dùng. Mở từ danh sách Xem thử thì không có, dùng bộ lọc trống.
        val dangDung = if (arguments != null) BoLoc.tuBundle(requireArguments()) else BoLoc()
        tuKhoa = arguments?.getString(TU_KHOA) ?: ""
        hienBoLoc(dangDung)

        // Đổi mục nào cũng đếm lại số tour trên nút "Xem N tour"
        diemDenSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                demTour()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        // doAfterTextChanged là cách viết ngắn của TextWatcher.afterTextChanged
        giaTuET.doAfterTextChanged { demTour() }
        giaDenET.doAfterTextChanged {
            giaDenTIL.error = null
            demTour()
        }
        thangCG.setOnCheckedStateChangeListener { _, _ -> demTour() }
        soNgayCG.setOnCheckedStateChangeListener { _, _ -> demTour() }
        sapXepCG.setOnCheckedStateChangeListener { _, _ -> demTour() }
        demTour()

        // Xóa lọc: đưa mọi mục về mặc định
        view.findViewById<Button>(R.id.xoaLocBtn).setOnClickListener {
            hienBoLoc(BoLoc())
        }

        // Xem: kiểm tra khoảng giá, gửi bộ lọc mới về A02 rồi đóng bảng
        xemBtn.setOnClickListener {
            val boLoc = docBoLoc()
            if (boLoc.giaDen > 0 && boLoc.giaTu > boLoc.giaDen) {
                giaDenTIL.error = getString(R.string.a02_loi_khoang_gia)
                return@setOnClickListener
            }
            setFragmentResult(KET_QUA, boLoc.thanhBundle())
            quayLai()
        }

        // Nút X: đóng bảng, giữ nguyên bộ lọc cũ
        view.findViewById<ImageView>(R.id.dongIV).setOnClickListener { quayLai() }
    }

    // Đặt các ô, các chip theo một bộ lọc
    private fun hienBoLoc(boLoc: BoLoc) {
        val viTri = diemDen.indexOf(boLoc.diemDen)
        diemDenSP.setSelection(if (viTri > 0) viTri else 0)
        giaTuET.setText(if (boLoc.giaTu > 0) boLoc.giaTu.toString() else "")
        giaDenET.setText(if (boLoc.giaDen > 0) boLoc.giaDen.toString() else "")

        thangCG.clearCheck()
        when (boLoc.thang) {
            11 -> thangCG.check(R.id.thang11Chip)
            12 -> thangCG.check(R.id.thang12Chip)
            1 -> thangCG.check(R.id.thang1Chip)
            2 -> thangCG.check(R.id.thang2Chip)
        }

        soNgayCG.clearCheck()
        when (boLoc.soNgay) {
            1 -> soNgayCG.check(R.id.ngay12Chip)
            3 -> soNgayCG.check(R.id.ngay34Chip)
            5 -> soNgayCG.check(R.id.ngay5Chip)
        }

        when (boLoc.sapXep) {
            BoLoc.GIA_GIAM -> sapXepCG.check(R.id.giaGiamChip)
            BoLoc.NGAY_GAN_NHAT -> sapXepCG.check(R.id.ganNhatChip)
            else -> sapXepCG.check(R.id.giaTangChip)
        }
    }

    // Đọc các ô, các chip đang chọn thành một BoLoc
    private fun docBoLoc(): BoLoc {
        val boLoc = BoLoc()
        // Dòng 0 là "Tất cả điểm đến" nên không lọc
        if (diemDenSP.selectedItemPosition > 0) {
            boLoc.diemDen = diemDen[diemDenSP.selectedItemPosition]
        }
        boLoc.giaTu = giaTuET.text.toString().toLongOrNull() ?: 0
        boLoc.giaDen = giaDenET.text.toString().toLongOrNull() ?: 0
        boLoc.thang = when (thangCG.checkedChipId) {
            R.id.thang11Chip -> 11
            R.id.thang12Chip -> 12
            R.id.thang1Chip -> 1
            R.id.thang2Chip -> 2
            else -> 0
        }
        boLoc.soNgay = when (soNgayCG.checkedChipId) {
            R.id.ngay12Chip -> 1
            R.id.ngay34Chip -> 3
            R.id.ngay5Chip -> 5
            else -> 0
        }
        boLoc.sapXep = when (sapXepCG.checkedChipId) {
            R.id.giaGiamChip -> BoLoc.GIA_GIAM
            R.id.ganNhatChip -> BoLoc.NGAY_GAN_NHAT
            else -> BoLoc.GIA_TANG
        }
        return boLoc
    }

    // Nút "Xem N tour": đếm trước số tour sẽ hiện nếu áp dụng bộ lọc này
    private fun demTour() {
        val soTour = locTour(tatCa, tuKhoa, docBoLoc()).size
        xemBtn.text = getString(R.string.a02_xem_n_tour, soTour)
    }

    companion object {
        // A02 nghe kết quả bằng setFragmentResultListener(BoLocFragment.KET_QUA)
        const val KET_QUA = "bo_loc"
        private const val TU_KHOA = "tu_khoa"

        // A02 mở bảng này bằng: BoLocFragment.newInstance(boLoc, tuKhoa)
        fun newInstance(boLoc: BoLoc, tuKhoa: String) = BoLocFragment().apply {
            val goi = boLoc.thanhBundle()
            goi.putString(TU_KHOA, tuKhoa)
            arguments = goi
        }
    }
}
