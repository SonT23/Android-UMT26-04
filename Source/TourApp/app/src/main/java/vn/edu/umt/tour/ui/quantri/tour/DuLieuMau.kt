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

// A12 Sửa điểm đến: danh sách tỉnh thành cho Spinner
fun taoTinhMau(): ArrayList<String> {
    return arrayListOf(
        "Hà Nội", "Quảng Ninh", "Lào Cai", "Quảng Nam", "Khánh Hòa",
        "Lâm Đồng", "TP. Hồ Chí Minh", "Kiên Giang"
    )
}

// A12 Sửa điểm đến: điểm đến đang sửa (Đà Lạt) như trong thiết kế
const val TEN_DANG_SUA = "Đà Lạt"
const val TINH_DANG_SUA = "Lâm Đồng"
const val MO_TA_DANG_SUA = "Thành phố cao nguyên, khí hậu mát quanh năm."
const val TOA_DO_DANG_SUA = "11.9404, 108.4583"

// A13: một tour trong danh sách quản trị.
// dangHien = true thì khách thấy tour; noiBat = true thì tour hiện ở trang chủ; soDot là số đợt đang mở.
class TourMau(
    val ten: String,
    val moTa: String,
    var dangHien: Boolean,
    val noiBat: Boolean,
    val soDot: Int
)

fun taoTourMau(): ArrayList<TourMau> {
    return arrayListOf(
        TourMau("Đà Lạt 3 ngày 2 đêm", "Đà Lạt, 3 ngày 2 đêm", true, true, 2),
        TourMau("Nha Trang biển đảo 4 ngày", "Nha Trang, 4 ngày 3 đêm", true, false, 2),
        TourMau("Phú Quốc nghỉ dưỡng 4 ngày", "Phú Quốc, 4 ngày 3 đêm", false, false, 1),
        TourMau("Hạ Long du thuyền 2 ngày 1 đêm", "Hạ Long, 2 ngày 1 đêm", true, false, 1),
        TourMau("Sa Pa mùa lúa 3 ngày", "Sa Pa, 3 ngày 2 đêm", false, false, 0)
    )
}
