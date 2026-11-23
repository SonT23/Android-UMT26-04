package vn.edu.umt.tour.model

data class NguoiDung(
    val id: String = "",
    val hoTen: String = "",
    val email: String = "",
    val soDienThoai: String = "",
    val vaiTro: String = KHACH,
    val biKhoa: Boolean = false,
    val anhDaiDien: String = "",
    val taoLuc: Long = 0L
) {
    companion object {
        const val KHACH = "KHACH"
        const val QUAN_TRI = "QUAN_TRI"
    }
}