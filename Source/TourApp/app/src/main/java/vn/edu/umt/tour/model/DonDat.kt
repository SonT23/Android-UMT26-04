package vn.edu.umt.tour.model

data class DonDat(
    val id: String = "",
    val maKhach: String = "",
    val tenKhach: String = "",
    val soDienThoai: String = "",
    val maTour: String = "",
    val tenTour: String = "",
    val maDot: String = "",
    val ngayDi: Long = 0L,
    val soNguoiLon: Long = 0L,
    val soTreEm: Long = 0L,
    val tongTien: Long = 0L,
    val daThanhToan: Long = 0L,
    val ghiChu: String = "",
    val trangThai: String = CHO_XAC_NHAN,
    val datLuc: Long = 0L,
    val daCheckIn: Boolean = false,
    val checkInLuc: Long = 0L
) {
    companion object {
        const val CHO_XAC_NHAN = "CHO_XAC_NHAN"
        const val DA_XAC_NHAN = "DA_XAC_NHAN"
        const val HOAN_THANH = "HOAN_THANH"
        const val DA_HUY = "DA_HUY"
    }
}