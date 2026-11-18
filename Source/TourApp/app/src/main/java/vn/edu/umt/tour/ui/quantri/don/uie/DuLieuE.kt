package vn.edu.umt.tour.ui.quantri.don.uie

// Dữ liệu mẫu để dựng giao diện module E khi chưa có API.

data class DonDatTour(
    val maDon: String,
    val tenKhachHang: String,
    val sdt: String,
    val email: String,
    val tenTour: String,
    val ngayKhoiHanh: String,
    val soNguoiLon: Int,
    val soTreEm: Int,
    val tongTien: String,
    val daThanhToan: String = "0đ",
    val trangThai: String // "Chờ xác nhận", "Đã xác nhận", "Hoàn thành", "Đã hủy"
) {
    val soKhach: Int get() = soNguoiLon + soTreEm
    val ngayDat: String get() = "09:12 · 10/12/2026"
}

data class KhachHangQuanTri(
    val id: String,
    val ten: String,
    val sdt: String,
    val email: String,
    val soTourDaDat: Int,
    var biBanTai: Boolean = false
) {
    val kyTuDau: String get() = ten.trim().split(" ").lastOrNull()?.take(1)?.uppercase() ?: "?"
}

data class TourBanChayMau(
    val ten: String,
    val soDon: Int,
    val doanhThu: String
)

fun taoDanhSachDonMau(): MutableList<DonDatTour> {
    return mutableListOf(
        DonDatTour("DH00124", "Nguyễn Văn A", "0905 123 456", "a.nguyen@email.com",
            "Đà Nẵng 3N2Đ", "12/12/2026", 3, 1, "8.400.000đ", "0đ", "Chờ xác nhận"),
        DonDatTour("DH00125", "Trần Thị B", "0912 334 455", "b.tran@email.com",
            "Phú Quốc 4N3Đ", "20/12/2026", 5, 1, "12.600.000đ", "0đ", "Chờ xác nhận"),
        DonDatTour("DH00123", "Lê Văn C", "0987 222 111", "c.le@email.com",
            "Hà Giang 3N2Đ", "10/12/2026", 2, 1, "6.900.000đ", "0đ", "Đã xác nhận"),
        DonDatTour("DH00122", "Phạm Thị D", "0909 888 777", "d.pham@email.com",
            "Nha Trang 4N3Đ", "18/12/2026", 3, 1, "9.800.000đ", "0đ", "Đã xác nhận"),
        DonDatTour("DH00121", "Hoàng Văn E", "0933 456 789", "e.hoang@email.com",
            "Đà Lạt 3N2Đ", "05/12/2026", 2, 0, "5.400.000đ", "5.400.000đ", "Hoàn thành"),
        DonDatTour("DH00120", "Đỗ Thị F", "0966 111 222", "f.do@email.com",
            "Đà Nẵng 3N2Đ", "12/12/2026", 3, 1, "8.400.000đ", "0đ", "Đã hủy")
    )
}

fun taoDanhSachKhachHangMau(): MutableList<KhachHangQuanTri> {
    return mutableListOf(
        KhachHangQuanTri("KH001", "Nguyễn Văn A", "0905 123 456", "a.nguyen@email.com", 3),
        KhachHangQuanTri("KH002", "Trần Thị B", "0912 334 455", "b.tran@email.com", 5),
        KhachHangQuanTri("KH003", "Lê Văn C", "0987 222 111", "c.le@email.com", 2),
        KhachHangQuanTri("KH004", "Phạm Thị D", "0909 888 777", "d.pham@email.com", 4),
        KhachHangQuanTri("KH005", "Hoàng Văn E", "0933 456 789", "e.hoang@email.com", 1, biBanTai = true),
        KhachHangQuanTri("KH006", "Đỗ Thị F", "0966 111 222", "f.do@email.com", 3),
        KhachHangQuanTri("KH007", "Vũ Văn G", "0977 333 444", "g.vu@email.com", 2, biBanTai = true),
        KhachHangQuanTri("KH008", "Bùi Thị H", "0918 777 666", "h.bui@email.com", 6),
        KhachHangQuanTri("KH009", "Ngô Văn K", "0944 555 333", "k.ngo@email.com", 4)
    )
}

fun taoDanhSachTourBanChayMau(): List<TourBanChayMau> {
    return listOf(
        TourBanChayMau("Đà Nẵng 3N2Đ", 42, "58.900.000đ"),
        TourBanChayMau("Phú Quốc 4N3Đ", 37, "61.200.000đ"),
        TourBanChayMau("Hà Giang 3N2Đ", 29, "40.600.000đ"),
        TourBanChayMau("Nha Trang 4N3Đ", 24, "33.600.000đ"),
        TourBanChayMau("Đà Lạt 3N2Đ", 18, "21.400.000đ")
    )
}
