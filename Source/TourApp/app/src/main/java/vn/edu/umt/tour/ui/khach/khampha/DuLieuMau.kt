package vn.edu.umt.tour.ui.khach.khampha

import java.text.SimpleDateFormat
import java.util.Locale

// Dữ liệu mẫu để dựng giao diện module B khi chưa có API.
// Khi nối API thì thay TourMau bằng lớp Tour trong package model/ (Thịnh làm).

// ma: mã tour dạng chuỗi ("T01"…), cùng một tour thì cùng mã ở mọi màn hình.
// Màn hình sau nhận mã này qua newInstance rồi tự tìm tour (xem timTourMau ở cuối file).
class TourMau(
    val ma: String,
    val ten: String,
    val moTa: String,
    val gia: String,
    val conCho: String,
    // Tim đầy hay rỗng; khi mở màn hình được tô lại theo bảng yeu_thich trong Room (B5)
    var yeuThich: Boolean,
    // true thì nhãn hiện màu xám (vd. "Hết chỗ"), false thì màu cam (vd. "Còn 1 chỗ")
    val nhanXam: Boolean = false,
    // Bốn trường dưới dùng để lọc, sắp xếp ở A02 (B2). Tour của A01, A21 không cần nên để mặc định.
    val diemDen: String = "",
    val giaTu: Long = 0,       // giá thấp nhất, đơn vị đồng
    val soNgay: Int = 0,
    val ngayDi: Long = 0       // ngày khởi hành gần nhất, mili giây
)

fun taoTourMau(): ArrayList<TourMau> {
    return arrayListOf(
        TourMau("T01", "Đà Lạt 3 ngày 2 đêm", "Đà Lạt, Lâm Đồng · 3 ngày 2 đêm", "Từ 2.500.000 ₫", "Còn 1 chỗ", false),
        TourMau("T02", "Nha Trang biển đảo 4 ngày", "Nha Trang, Khánh Hòa · 4 ngày 3 đêm", "Từ 3.900.000 ₫", "Còn 12 chỗ", false),
        TourMau("T03", "Phú Quốc nghỉ dưỡng 4 ngày", "Phú Quốc, Kiên Giang · 4 ngày 3 đêm", "Từ 5.200.000 ₫", "Còn 6 chỗ", false),
        TourMau("T04", "Hạ Long du thuyền 2 ngày", "Hạ Long, Quảng Ninh · 2 ngày 1 đêm", "Từ 3.100.000 ₫", "Hết chỗ", false)
    )
}

// A02: mọi tour để tìm, lọc, sắp xếp (B2). Tuần 9 thay bằng đọc collection "tour" trên Firestore.
fun taoTatCaTourMau(): ArrayList<TourMau> {
    return arrayListOf(
        tourTim("T01", "Đà Lạt 3 ngày 2 đêm", "Đà Lạt", 3, "20/11/2026", 2500000, 1),
        tourTim("T02", "Nha Trang biển đảo 4 ngày", "Nha Trang", 4, "25/11/2026", 3900000, 12),
        tourTim("T03", "Phú Quốc nghỉ dưỡng 4 ngày", "Phú Quốc", 4, "03/12/2026", 5200000, 6),
        tourTim("T04", "Hạ Long du thuyền 2 ngày", "Hạ Long", 2, "28/11/2026", 3100000, 0),
        tourTim("T05", "Đà Lạt săn mây 2 ngày 1 đêm", "Đà Lạt", 2, "22/11/2026", 1800000, 8),
        tourTim("T06", "Đà Lạt mùa hoa 4 ngày", "Đà Lạt", 4, "27/11/2026", 3200000, 0),
        tourTim("T07", "Đà Lạt, Nha Trang 5 ngày", "Đà Lạt", 5, "04/12/2026", 4600000, 20),
        tourTim("T08", "Đà Lạt cắm trại 2 ngày", "Đà Lạt", 2, "06/12/2026", 1500000, 15),
        tourTim("T09", "Đà Lạt, Mũi Né 4 ngày", "Đà Lạt", 4, "11/12/2026", 4100000, 3),
        tourTim("T10", "Hội An phố cổ 3 ngày", "Hội An", 3, "18/12/2026", 2900000, 9),
        tourTim("T11", "Sa Pa mùa lúa 3 ngày", "Sa Pa", 3, "08/01/2027", 3300000, 4),
        tourTim("T12", "Hạ Long, Ninh Bình 4 ngày", "Hạ Long", 4, "15/01/2027", 4800000, 10),
        tourTim("T13", "Sa Pa đón Tết 4 ngày", "Sa Pa", 4, "05/02/2027", 5900000, 2),
        tourTim("T14", "Phú Quốc Tết 5 ngày", "Phú Quốc", 5, "12/02/2027", 7500000, 5)
    )
}

// Tạo một tour cho A02. Chữ mô tả, giá, nhãn chỗ được ghép từ các trường gốc,
// nên chỉ cần sửa số liệu ở trên là chữ trên thẻ tự đúng theo.
private fun tourTim(
    ma: String, ten: String, diemDen: String, soNgay: Int,
    ngayDi: String, giaTu: Long, conCho: Int
): TourMau {
    val moTa = "$diemDen · $soNgay ngày ${soNgay - 1} đêm · ${ngayDi.substring(0, 5)}"
    val nhanCho = if (conCho == 0) "Hết chỗ" else "Còn $conCho chỗ"
    return TourMau(
        ma, ten, moTa, "Từ " + dinhDangTien(giaTu), nhanCho, false, conCho == 0,
        diemDen = diemDen, giaTu = giaTu, soNgay = soNgay, ngayDi = docNgay(ngayDi)
    )
}

// 2500000 → "2.500.000 ₫". Tạm viết ở đây, sau dùng util/DinhDang.kt của Trúc Đào.
fun dinhDangTien(so: Long): String {
    return String.format(Locale.US, "%,d", so).replace(',', '.') + " ₫"
}

// "20/11/2026" → số mili giây, cùng kiểu với ngày trong Firestore (Docs/Firestore.md)
private fun docNgay(ngay: String): Long {
    return SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(ngay)!!.time
}

fun taoDiemDenMau(): ArrayList<String> {
    return arrayListOf("Đà Lạt", "Nha Trang", "Hội An", "Phú Quốc", "Hạ Long", "Sa Pa")
}

// A03: lịch trình từng ngày. moRong = true khi người dùng bấm mở xem chi tiết ngày đó.
class LichTrinhMau(
    val ngay: String,
    val tieuDe: String,
    val moTa: String,
    var moRong: Boolean = false
)

fun taoLichTrinhMau(): ArrayList<LichTrinhMau> {
    return arrayListOf(
        LichTrinhMau("Ngày 1", "Khởi hành, nhận phòng",
            "06:00 đón khách tại TP.HCM. Chiều nhận phòng khách sạn, tối dạo chợ đêm Đà Lạt."),
        LichTrinhMau("Ngày 2", "Đồi chè và thác Datanla",
            "Sáng tham quan đồi chè Cầu Đất. Chiều trượt máng ở thác Datanla, tối tự do."),
        LichTrinhMau("Ngày 3", "Chợ Đà Lạt, trở về",
            "Sáng mua đặc sản ở chợ Đà Lạt, trả phòng lúc 11:00. Về đến TP.HCM khoảng 19:00.")
    )
}

// A03: đợt khởi hành. soCho = 0 nghĩa là hết chỗ.
class DotMau(
    val ngay: String,
    val gia: String,
    val soCho: Int
)

fun taoDotMau(): ArrayList<DotMau> {
    return arrayListOf(
        DotMau("20/11 đến 22/11/2026", "Người lớn 2.500.000 ₫, trẻ em 1.500.000 ₫", 1),
        DotMau("04/12 đến 06/12/2026", "Người lớn 2.700.000 ₫, trẻ em 1.620.000 ₫", 20),
        DotMau("30/10 đến 01/11/2026", "Người lớn 2.400.000 ₫, trẻ em 1.440.000 ₫", 0)
    )
}

// A21: một ô tour đã xem. Tạo từ bảng da_xem trong Room (B6), xem YeuThichFragment.
// thoiGian là chữ hiện dưới tên: "Hôm nay", "Hôm qua" hoặc ngày dạng dd/MM.
class DaXemMau(
    val ma: String,
    val ten: String,
    val thoiGian: String
)

// A22: các điểm dừng trên bản đồ, chia theo ngày
class DiemDungMau(
    val ten: String,
    val moTa: String
)

class NgayBanDoMau(
    val ten: String,
    val tieuDe: String,
    val diemDung: List<DiemDungMau>
)

// Phần tử 0 là "Tất cả" (gộp mọi điểm dừng), phần tử 1, 2, 3 là ngày 1, 2, 3
fun taoBanDoMau(): ArrayList<NgayBanDoMau> {
    val ngay1 = arrayListOf(
        DiemDungMau("Nhà văn hóa Thanh Niên (điểm tập trung)", "06:00, TP.HCM"),
        DiemDungMau("Khách sạn Ladalat", "14:00, cách điểm tập trung 300 km"),
        DiemDungMau("Chợ đêm Đà Lạt", "19:00, cách khách sạn 2 km")
    )
    val ngay2 = arrayListOf(
        DiemDungMau("Đồi chè Cầu Đất", "08:00, cách khách sạn 22 km"),
        DiemDungMau("Thác Datanla", "13:30, cách điểm trước 27 km"),
        DiemDungMau("Quảng trường Lâm Viên", "17:00, cách điểm trước 6 km")
    )
    val ngay3 = arrayListOf(
        DiemDungMau("Chợ Đà Lạt", "08:00, cách khách sạn 1 km"),
        DiemDungMau("Trả phòng khách sạn", "11:00"),
        DiemDungMau("Về đến TP.HCM", "khoảng 19:00")
    )
    val tatCa = ArrayList<DiemDungMau>()
    tatCa.addAll(ngay1)
    tatCa.addAll(ngay2)
    tatCa.addAll(ngay3)
    return arrayListOf(
        NgayBanDoMau("Cả hành trình", "Đà Lạt 3 ngày 2 đêm", tatCa),
        NgayBanDoMau("Ngày 1", "Khởi hành, nhận phòng", ngay1),
        NgayBanDoMau("Ngày 2", "Đồi chè và thác Datanla", ngay2),
        NgayBanDoMau("Ngày 3", "Chợ Đà Lạt, trở về", ngay3)
    )
}

// Tìm tour mẫu theo mã. A03, A22 dùng hàm này sau khi nhận mã qua newInstance.
// Tuần 9 thay bằng đọc document có mã này trong collection "tour" của Firestore.
fun timTourMau(ma: String): TourMau {
    val tatCa = taoTourMau() + taoTatCaTourMau()
    return tatCa.firstOrNull { it.ma == ma } ?: taoTourMau()[0]
}
