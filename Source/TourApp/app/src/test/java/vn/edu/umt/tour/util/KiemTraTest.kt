package vn.edu.umt.tour.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class KiemTraTest {

    @Test
    fun matKhau_toiThieu8KyTu() {
        assertFalse("".laMatKhauHopLe())
        assertFalse("1234567".laMatKhauHopLe())
        assertTrue("12345678".laMatKhauHopLe())
        assertTrue("matkhau123".laMatKhauHopLe())
    }

    @Test
    fun soDienThoai_10So_BatDauBang0() {
        assertTrue("0905123456".laSoDienThoaiHopLe())
        assertTrue(" 0905123456 ".laSoDienThoaiHopLe())
        assertFalse("905123456".laSoDienThoaiHopLe())
        assertFalse("1905123456".laSoDienThoaiHopLe())
        assertFalse("09051234567".laSoDienThoaiHopLe())
        assertFalse("0905 123 456".laSoDienThoaiHopLe())
    }
}
