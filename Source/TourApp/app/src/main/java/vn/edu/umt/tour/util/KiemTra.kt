package vn.edu.umt.tour.util

import android.util.Patterns

/*
 * Ví dụ:
 *   if (!email.laEmailHopLe()) emailTIL.error = "Email không hợp lệ"
 *   if (!matKhau.laMatKhauHopLe()) matKhauTIL.error = "Mật khẩu tối thiểu 8 ký tự"
 *   if (!sdt.laSoDienThoaiHopLe()) sdtTIL.error = "Số điện thoại không hợp lệ"
 */

fun String.laEmailHopLe(): Boolean = Patterns.EMAIL_ADDRESS.matcher(trim()).matches()

fun String.laMatKhauHopLe(): Boolean = length >= 8

fun String.laSoDienThoaiHopLe(): Boolean = trim().matches(Regex("0[0-9]{9}"))
