package vn.edu.umt.tour.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/*
 * Ví dụ:
 *   tongTienTV.text = dinhDangTien(2_500_000)          // "2.500.000 ₫"
 *   ngayTV.text = dinhDangNgay(1763664000000)          // "21/11/2026"
 *   taoLucTV.text = dinhDangNgayGio(1763664000000)     // "12:00 · 21/11/2026"
 */

private val localeVietNam = Locale("vi", "VN")

fun dinhDangTien(soTien: Long): String =
    NumberFormat.getNumberInstance(localeVietNam).format(soTien) + " ₫"

fun dinhDangTien(soTien: Int): String = dinhDangTien(soTien.toLong())

fun dinhDangNgay(miliGiay: Long): String =
    SimpleDateFormat("dd/MM/yyyy", localeVietNam).format(Date(miliGiay))

fun dinhDangNgayGio(miliGiay: Long): String =
    SimpleDateFormat("HH:mm · dd/MM/yyyy", localeVietNam).format(Date(miliGiay))
