package vn.edu.umt.tour.ui.quantri.tour

// Dữ liệu mẫu để dựng giao diện module D khi chưa có API.
// Khi nối API thì thay các lớp ...Mau bằng lớp trong package model/ và lời gọi trong data/api/.

// A12: một điểm đến. soTour là số tour đang dùng điểm đến này.
class DiemDenMau(
    val ten: String,
    val tinh: String,
    val soTour: Int
)

fun taoDiemDenMau(): ArrayList<DiemDenMau> {
    return arrayListOf(
        DiemDenMau("Đà Lạt", "Lâm Đồng", 2),
        DiemDenMau("Nha Trang", "Khánh Hòa", 1),
        DiemDenMau("Hội An", "Quảng Nam", 1),
        DiemDenMau("Phú Quốc", "Kiên Giang", 1),
        DiemDenMau("Hạ Long", "Quảng Ninh", 1),
        DiemDenMau("Sa Pa", "Lào Cai", 0)
    )
}

