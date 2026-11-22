package vn.edu.umt.tour.ui.khach.khampha

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
    val nhanXam: Boolean = false
)

fun taoTourMau(): ArrayList<TourMau> {
    return arrayListOf(
        TourMau("T01", "Đà Lạt 3 ngày 2 đêm", "Đà Lạt, Lâm Đồng · 3 ngày 2 đêm", "Từ 2.500.000 ₫", "Còn 1 chỗ", false),
        TourMau("T02", "Nha Trang biển đảo 4 ngày", "Nha Trang, Khánh Hòa · 4 ngày 3 đêm", "Từ 3.900.000 ₫", "Còn 12 chỗ", false),
        TourMau("T03", "Phú Quốc nghỉ dưỡng 4 ngày", "Phú Quốc, Kiên Giang · 4 ngày 3 đêm", "Từ 5.200.000 ₫", "Còn 6 chỗ", false),
        TourMau("T04", "Hạ Long du thuyền 2 ngày", "Hạ Long, Quảng Ninh · 2 ngày 1 đêm", "Từ 3.100.000 ₫", "Hết chỗ", false)
    )
}

// A02: kết quả tìm "Đà Lạt"
fun taoKetQuaTimMau(): ArrayList<TourMau> {
    return arrayListOf(
        TourMau("T01", "Đà Lạt 3 ngày 2 đêm", "Đà Lạt · 3 ngày 2 đêm · 20/11", "Từ 2.500.000 ₫", "Còn 1 chỗ", false),
        TourMau("T05", "Đà Lạt săn mây 2 ngày 1 đêm", "Đà Lạt · 2 ngày 1 đêm · 22/11", "Từ 1.800.000 ₫", "Còn 8 chỗ", false),
        TourMau("T06", "Đà Lạt mùa hoa 4 ngày", "Đà Lạt · 4 ngày 3 đêm · 27/11", "Từ 3.200.000 ₫", "Hết chỗ", false, true),
        TourMau("T07", "Đà Lạt, Nha Trang 5 ngày", "Đà Lạt · 5 ngày 4 đêm · 04/12", "Từ 4.600.000 ₫", "Còn 20 chỗ", false),
        TourMau("T08", "Đà Lạt cắm trại 2 ngày", "Đà Lạt · 2 ngày 1 đêm · 06/12", "Từ 1.500.000 ₫", "Còn 15 chỗ", false),
        TourMau("T09", "Đà Lạt, Mũi Né 4 ngày", "Đà Lạt · 4 ngày 3 đêm · 11/12", "Từ 4.100.000 ₫", "Còn 3 chỗ", false)
    )
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

// Tour mẫu Hội An, Sa Pa: chưa nằm trong danh sách nào, nhưng timTourMau vẫn tìm được theo mã
fun taoTourKhacMau(): ArrayList<TourMau> {
    return arrayListOf(
        TourMau("T10", "Hội An phố cổ 3 ngày", "Hội An, Quảng Nam · 3 ngày 2 đêm", "Từ 2.900.000 ₫", "Còn 9 chỗ", false),
        TourMau("T11", "Sa Pa mùa lúa 3 ngày", "Sa Pa, Lào Cai · 3 ngày 2 đêm", "Từ 3.300.000 ₫", "Còn 4 chỗ", false)
    )
}

// Tìm tour mẫu theo mã. A03, A22 dùng hàm này sau khi nhận mã qua newInstance.
// Tuần 9 thay bằng đọc document có mã này trong collection "tour" của Firestore.
fun timTourMau(ma: String): TourMau {
    val tatCa = taoTourMau() + taoKetQuaTimMau() + taoTourKhacMau()
    return tatCa.firstOrNull { it.ma == ma } ?: taoTourMau()[0]
}
