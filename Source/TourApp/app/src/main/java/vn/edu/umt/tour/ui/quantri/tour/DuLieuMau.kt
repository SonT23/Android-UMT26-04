package vn.edu.umt.tour.ui.quantri.tour

// Dữ liệu mẫu để dựng giao diện module D khi chưa có API.
// Tuần 9 thay các lớp ...Mau bằng lớp trong package model/ và đọc, ghi Firestore.

// A12: một điểm đến. ma giống id document trên Firestore ("DD01"...).
// soTour là số tour đang dùng điểm đến này.
class DiemDenMau(
    val ma: String,
    var ten: String,
    var tinh: String,
    var moTa: String,
    var toaDo: String,
    val soTour: Int
)

fun taoDiemDenMau(): ArrayList<DiemDenMau> {
    return arrayListOf(
        DiemDenMau("DD01", "Đà Lạt", "Lâm Đồng", "Thành phố cao nguyên, khí hậu mát quanh năm.", "11.9404, 108.4583", 2),
        DiemDenMau("DD02", "Nha Trang", "Khánh Hòa", "Thành phố biển, nhiều đảo đẹp.", "12.2388, 109.1967", 1),
        DiemDenMau("DD03", "Hội An", "Quảng Nam", "Phố cổ bên sông Hoài.", "15.8801, 108.3380", 1),
        DiemDenMau("DD04", "Phú Quốc", "Kiên Giang", "Đảo ngọc, biển xanh cát trắng.", "10.2899, 103.9840", 1),
        DiemDenMau("DD05", "Hạ Long", "Quảng Ninh", "Vịnh di sản với hàng nghìn đảo đá.", "20.9101, 107.1839", 1),
        DiemDenMau("DD06", "Sa Pa", "Lào Cai", "Ruộng bậc thang và núi Fansipan.", "22.3364, 103.8438", 0)
    )
}

// D1: danh sách điểm đến dùng chung trong lúc app chạy, để A12 và A12 Sửa cùng thấy một dữ liệu
// (thêm, sửa, xóa ở A12 không mất khi mở màn hình khác rồi quay lại).
// Tuần 9 thay bằng đọc, ghi collection diem_den trên Firestore.
object KhoDiemDenMau {
    val ds: ArrayList<DiemDenMau> = taoDiemDenMau()

    fun tim(ma: String): DiemDenMau? = ds.find { it.ma == ma }

    // Mã cho điểm đến mới: DD + số lớn nhất hiện có + 1, vd. DD07
    fun maMoi(): String {
        val soLonNhat = ds.maxOfOrNull { it.ma.removePrefix("DD").toIntOrNull() ?: 0 } ?: 0
        return "DD" + (soLonNhat + 1).toString().padStart(2, '0')
    }
}

// A12 Sửa điểm đến: danh sách tỉnh thành cho Spinner
fun taoTinhMau(): ArrayList<String> {
    return arrayListOf(
        "Hà Nội", "Quảng Ninh", "Lào Cai", "Quảng Nam", "Khánh Hòa",
        "Lâm Đồng", "TP. Hồ Chí Minh", "Kiên Giang"
    )
}

// A24: tọa độ đang ghim như trong thiết kế (Đà Lạt)
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

// A15: trạng thái của một đợt khởi hành
const val DOT_MO = "mo"
const val DOT_DONG = "dong"
const val DOT_HUY = "huy"

// A15: một đợt khởi hành của tour. daDat là số chỗ đã đặt trên tongCho chỗ.
class DotMau(
    val ngay: String,
    val gia: String,
    val daDat: Int,
    val tongCho: Int,
    var trangThai: String
)

// Tour đang xem ở A15
const val TEN_TOUR_DANG_XEM = "Đà Lạt 3 ngày 2 đêm"

fun taoDotMau(): ArrayList<DotMau> {
    return arrayListOf(
        DotMau("20/11 đến 22/11/2026", "Người lớn 2.500.000 ₫, trẻ em 1.500.000 ₫", 3, 4, DOT_MO),
        DotMau("04/12 đến 06/12/2026", "Người lớn 2.700.000 ₫, trẻ em 1.620.000 ₫", 0, 20, DOT_MO),
        DotMau("30/10 đến 01/11/2026", "Người lớn 2.400.000 ₫, trẻ em 1.440.000 ₫", 20, 20, DOT_DONG),
        DotMau("18/12 đến 20/12/2026", "Người lớn 2.800.000 ₫, trẻ em 1.680.000 ₫", 0, 16, DOT_HUY)
    )
}

// A14: tour đang sửa như trong thiết kế
const val SO_NGAY_DANG_SUA = 3
const val SO_DEM_DANG_SUA = 2
const val DIEM_DEN_DANG_SUA = "Đà Lạt"

// A14: một ngày trong lịch trình. thuTu bắt đầu từ 1.
// D5 sửa tiêu đề và đánh số lại khi xóa, kéo thả nên hai trường là var.
class NgayMau(
    var thuTu: Int,
    var tieuDe: String
)

// A24: vị trí đang ghim trên bản đồ như trong thiết kế
const val TEN_VI_TRI = "Đà Lạt, Lâm Đồng"

fun taoNgayMau(): ArrayList<NgayMau> {
    return arrayListOf(
        NgayMau(1, "Khởi hành, nhận phòng"),
        NgayMau(2, "Đồi chè và thác Datanla")
    )
}
