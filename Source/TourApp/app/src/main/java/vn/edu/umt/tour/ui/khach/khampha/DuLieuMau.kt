package vn.edu.umt.tour.ui.khach.khampha

// Dữ liệu mẫu để dựng giao diện module B khi chưa có API.
// Khi nối API thì thay TourMau bằng lớp Tour trong package model/ (Thịnh làm).

class TourMau(
    val ten: String,
    val moTa: String,
    val gia: String,
    val conCho: String,
    var yeuThich: Boolean
)

fun taoTourMau(): ArrayList<TourMau> {
    return arrayListOf(
        TourMau("Đà Lạt 3 ngày 2 đêm", "Đà Lạt, Lâm Đồng · 3 ngày 2 đêm", "Từ 2.500.000 ₫", "Còn 1 chỗ", true),
        TourMau("Nha Trang biển đảo 4 ngày", "Nha Trang, Khánh Hòa · 4 ngày 3 đêm", "Từ 3.900.000 ₫", "Còn 12 chỗ", false),
        TourMau("Phú Quốc nghỉ dưỡng 4 ngày", "Phú Quốc, Kiên Giang · 4 ngày 3 đêm", "Từ 5.200.000 ₫", "Còn 6 chỗ", false),
        TourMau("Hạ Long du thuyền 2 ngày", "Hạ Long, Quảng Ninh · 2 ngày 1 đêm", "Từ 3.100.000 ₫", "Hết chỗ", false)
    )
}

fun taoDiemDenMau(): ArrayList<String> {
    return arrayListOf("Đà Lạt", "Nha Trang", "Hội An", "Phú Quốc", "Hạ Long", "Sa Pa")
}
