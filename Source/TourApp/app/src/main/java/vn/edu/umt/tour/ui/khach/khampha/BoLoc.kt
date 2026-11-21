package vn.edu.umt.tour.ui.khach.khampha

import android.os.Bundle
import androidx.core.os.bundleOf
import java.text.Normalizer
import java.util.Calendar

// Bộ lọc của A02 (B2). Mỗi mục để giá trị mặc định nghĩa là không lọc theo mục đó.
class BoLoc(
    var diemDen: String = "",   // "" = mọi điểm đến
    var giaTu: Long = 0,        // 0 = không giới hạn dưới
    var giaDen: Long = 0,       // 0 = không giới hạn trên
    var thang: Int = 0,         // tháng khởi hành 1..12; 0 = mọi tháng
    var soNgay: Int = 0,        // 0 = mọi độ dài; 1 = 1–2 ngày; 3 = 3–4 ngày; 5 = từ 5 ngày
    var sapXep: Int = GIA_TANG
) {

    // Số mục đang lọc, hiện trên chip "Bộ lọc (2)". Sắp xếp không tính là lọc.
    fun soMucDangLoc(): Int {
        var dem = 0
        if (diemDen != "") dem++
        if (giaTu > 0 || giaDen > 0) dem++
        if (thang != 0) dem++
        if (soNgay != 0) dem++
        return dem
    }

    // Cất vào Bundle để gửi qua newInstance, setFragmentResult và onSaveInstanceState
    fun thanhBundle(): Bundle {
        return bundleOf(
            "diem_den" to diemDen,
            "gia_tu" to giaTu,
            "gia_den" to giaDen,
            "thang" to thang,
            "so_ngay" to soNgay,
            "sap_xep" to sapXep
        )
    }

    companion object {
        const val GIA_TANG = 0
        const val GIA_GIAM = 1
        const val NGAY_GAN_NHAT = 2

        // Lấy lại BoLoc từ Bundle đã cất bằng thanhBundle()
        fun tuBundle(b: Bundle): BoLoc {
            return BoLoc(
                b.getString("diem_den", ""),
                b.getLong("gia_tu"),
                b.getLong("gia_den"),
                b.getInt("thang"),
                b.getInt("so_ngay"),
                b.getInt("sap_xep")
            )
        }
    }
}

// Lọc rồi sắp xếp: giữ các tour khớp từ khóa và khớp mọi mục của bộ lọc.
// A02 gọi mỗi khi gõ chữ hoặc đổi bộ lọc; Bộ lọc gọi để đếm số tour cho nút "Xem N tour".
fun locTour(tatCa: List<TourMau>, tuKhoa: String, boLoc: BoLoc): ArrayList<TourMau> {
    val khoa = boDau(tuKhoa.trim())
    val ketQua = ArrayList<TourMau>()
    for (tour in tatCa) {
        // Từ khóa có trong tên tour hoặc tên điểm đến (không phân biệt hoa thường, có dấu hay không)
        if (khoa != "" && !boDau(tour.ten).contains(khoa) && !boDau(tour.diemDen).contains(khoa)) continue
        if (boLoc.diemDen != "" && tour.diemDen != boLoc.diemDen) continue
        if (boLoc.giaTu > 0 && tour.giaTu < boLoc.giaTu) continue
        if (boLoc.giaDen > 0 && tour.giaTu > boLoc.giaDen) continue
        if (boLoc.thang != 0 && layThang(tour.ngayDi) != boLoc.thang) continue
        if (!dungSoNgay(tour.soNgay, boLoc.soNgay)) continue
        ketQua.add(tour)
    }
    when (boLoc.sapXep) {
        BoLoc.GIA_TANG -> ketQua.sortBy { it.giaTu }
        BoLoc.GIA_GIAM -> ketQua.sortByDescending { it.giaTu }
        BoLoc.NGAY_GAN_NHAT -> ketQua.sortBy { it.ngayDi }
    }
    return ketQua
}

// Bỏ dấu và viết thường: "Đà Lạt" → "da lat", để gõ "da lat" hay "ĐÀ LẠT" đều tìm ra
fun boDau(chu: String): String {
    // NFD tách chữ có dấu thành chữ gốc + dấu riêng ("à" → "a" và dấu huyền), rồi xóa các dấu đó
    val tach = Normalizer.normalize(chu, Normalizer.Form.NFD)
    return tach.replace(Regex("\\p{Mn}"), "")
        .replace('đ', 'd')
        .replace('Đ', 'D')
        .lowercase()
}

// Tháng (1..12) của một ngày tính bằng mili giây. Calendar đếm tháng từ 0 nên phải cộng 1.
private fun layThang(ngay: Long): Int {
    val lich = Calendar.getInstance()
    lich.timeInMillis = ngay
    return lich.get(Calendar.MONTH) + 1
}

private fun dungSoNgay(soNgay: Int, loai: Int): Boolean {
    return when (loai) {
        1 -> soNgay <= 2
        3 -> soNgay == 3 || soNgay == 4
        5 -> soNgay >= 5
        else -> true
    }
}
