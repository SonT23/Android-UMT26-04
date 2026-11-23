package vn.edu.umt.tour.model

data class DotKhoiHanh(
    val id: String = "",
    val maTour: String = "",
    val ngayDi: Long = 0L,
    val ngayVe: Long = 0L,
    val giaNguoiLon: Long = 0L,
    val giaTreEm: Long = 0L,
    val tongCho: Long = 0L,
    val daDat: Long = 0L,
    val trangThai: String = MO
) {
    companion object {
        const val MO = "MO"
        const val DONG = "DONG"
        const val HUY = "HUY"
    }
}