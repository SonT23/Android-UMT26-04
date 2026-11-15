package vn.edu.umt.tour.ui.khach.khampha

// Dữ liệu mẫu để dựng giao diện module B khi chưa có API.
// Khi nối API thì thay TourMau bằng lớp Tour trong package model/ (Thịnh làm).

class TourMau(
    val ten: String,
    val moTa: String,
    val gia: String,
    val conCho: String,
    var yeuThich: Boolean,
    // true thì nhãn hiện màu xám (vd. "Hết chỗ"), false thì màu cam (vd. "Còn 1 chỗ")
    val nhanXam: Boolean = false
)

fun taoTourMau(): ArrayList<TourMau> {
    return arrayListOf(
        TourMau("Đà Lạt 3 ngày 2 đêm", "Đà Lạt, Lâm Đồng · 3 ngày 2 đêm", "Từ 2.500.000 ₫", "Còn 1 chỗ", true),
        TourMau("Nha Trang biển đảo 4 ngày", "Nha Trang, Khánh Hòa · 4 ngày 3 đêm", "Từ 3.900.000 ₫", "Còn 12 chỗ", false),
        TourMau("Phú Quốc nghỉ dưỡng 4 ngày", "Phú Quốc, Kiên Giang · 4 ngày 3 đêm", "Từ 5.200.000 ₫", "Còn 6 chỗ", false),
        TourMau("Hạ Long du thuyền 2 ngày", "Hạ Long, Quảng Ninh · 2 ngày 1 đêm", "Từ 3.100.000 ₫", "Hết chỗ", false)
    )
}

// A02: kết quả tìm "Đà Lạt"
fun taoKetQuaTimMau(): ArrayList<TourMau> {
    return arrayListOf(
        TourMau("Đà Lạt 3 ngày 2 đêm", "Đà Lạt · 3 ngày 2 đêm · 20/11", "Từ 2.500.000 ₫", "Còn 1 chỗ", true),
        TourMau("Đà Lạt săn mây 2 ngày 1 đêm", "Đà Lạt · 2 ngày 1 đêm · 22/11", "Từ 1.800.000 ₫", "Còn 8 chỗ", false),
        TourMau("Đà Lạt mùa hoa 4 ngày", "Đà Lạt · 4 ngày 3 đêm · 27/11", "Từ 3.200.000 ₫", "Hết chỗ", false, true),
        TourMau("Đà Lạt, Nha Trang 5 ngày", "Đà Lạt · 5 ngày 4 đêm · 04/12", "Từ 4.600.000 ₫", "Còn 20 chỗ", false),
        TourMau("Đà Lạt cắm trại 2 ngày", "Đà Lạt · 2 ngày 1 đêm · 06/12", "Từ 1.500.000 ₫", "Còn 15 chỗ", false),
        TourMau("Đà Lạt, Mũi Né 4 ngày", "Đà Lạt · 4 ngày 3 đêm · 11/12", "Từ 4.100.000 ₫", "Còn 3 chỗ", false)
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

// A21: tour đã lưu yêu thích. Nhãn xám ghi ngày lưu.
fun taoYeuThichMau(): ArrayList<TourMau> {
    return arrayListOf(
        TourMau("Đà Lạt 3 ngày 2 đêm", "Đà Lạt · 3 ngày 2 đêm", "Từ 2.500.000 ₫", "Lưu ngày 10/11", true, true),
        TourMau("Phú Quốc nghỉ dưỡng 4 ngày", "Phú Quốc · 4 ngày 3 đêm", "Từ 5.200.000 ₫", "Lưu ngày 08/11", true, true),
        TourMau("Hạ Long du thuyền 2 ngày", "Hạ Long · 2 ngày 1 đêm", "Từ 3.100.000 ₫", "Lưu ngày 02/11", true, true)
    )
}

// A21: tour đã xem gần đây
class DaXemMau(
    val ten: String,
    val thoiGian: String
)

fun taoDaXemMau(): ArrayList<DaXemMau> {
    return arrayListOf(
        DaXemMau("Nha Trang biển đảo", "Hôm nay"),
        DaXemMau("Hội An phố cổ", "Hôm qua"),
        DaXemMau("Sa Pa mùa lúa", "09/11"),
        DaXemMau("Đà Lạt 3 ngày 2 đêm", "08/11"),
        DaXemMau("Phú Quốc nghỉ dưỡng", "05/11"),
        DaXemMau("Hạ Long du thuyền", "02/11")
    )
}

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
