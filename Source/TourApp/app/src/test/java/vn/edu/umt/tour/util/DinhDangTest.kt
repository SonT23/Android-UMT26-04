package vn.edu.umt.tour.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

class DinhDangTest {

    @Test
    fun dinhDangTien_theoDonViTienVietNam() {
        assertEquals("6.500.000 ₫", dinhDangTien(6_500_000))
        assertEquals("2.500.000 ₫", dinhDangTien(2_500_000))
        assertEquals("0 ₫", dinhDangTien(0))
    }

    @Test
    fun dinhDangNgay_theoNgayThangNam() {
        val lich = Calendar.getInstance().apply {
            clear()
            set(2026, Calendar.NOVEMBER, 21, 12, 0, 0)
        }

        assertEquals("21/11/2026", dinhDangNgay(lich.timeInMillis))
        assertEquals("12:00 · 21/11/2026", dinhDangNgayGio(lich.timeInMillis))
    }
}
