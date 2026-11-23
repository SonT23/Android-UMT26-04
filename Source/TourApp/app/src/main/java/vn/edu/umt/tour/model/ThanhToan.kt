package vn.edu.umt.tour.model

data class ThanhToan(
    val id: String = "",
    val maDon: String = "",
    val soTien: Long = 0L,
    val hinhThuc: String = CHUYEN_KHOAN,
    val ghiChu: String = "",
    val ghiNhanLuc: Long = 0L,
    val ghiNhanBoi: String = ""
) {
    companion object {
        const val CHUYEN_KHOAN = "CHUYEN_KHOAN"
        const val TIEN_MAT = "TIEN_MAT"
    }
}