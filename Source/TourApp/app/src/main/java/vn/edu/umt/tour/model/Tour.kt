package vn.edu.umt.tour.model

data class DiemTapTrung(
    val ten: String = "",
    val viDo: Double = 0.0,
    val kinhDo: Double = 0.0
)

data class DiemDung(
    val ten: String = "",
    val gio: String = "",
    val viDo: Double = 0.0,
    val kinhDo: Double = 0.0
)

data class NgayLichTrinh(
    val ngay: Long = 0L,
    val tieuDe: String = "",
    val moTa: String = "",
    val diemDung: List<DiemDung> = emptyList()
)

data class Tour(
    val id: String = "",
    val ten: String = "",
    val maDiemDen: String = "",
    val tenDiemDen: String = "",
    val soNgay: Long = 0L,
    val soDem: Long = 0L,
    val giaTu: Long = 0L,
    val moTa: String = "",
    val anh: List<String> = emptyList(),
    val noiBat: Boolean = false,
    val hienThi: Boolean = true,
    val diemTapTrung: DiemTapTrung = DiemTapTrung(),
    val lichTrinh: List<NgayLichTrinh> = emptyList()
)