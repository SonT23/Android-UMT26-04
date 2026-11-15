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
