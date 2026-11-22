package vn.edu.umt.tour.data

import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.TimeZone

/**
 * Nạp dữ liệu mẫu lên Firestore: 6 điểm đến, 5 tour, 7 đợt khởi hành (DỮ LIỆU GIẢ để demo).
 * Mọi document dùng id cố định (DD01, T01, DT01...) và ghi bằng set(), nên chạy lại nhiều lần
 * chỉ ghi đè đúng các document đó, không sinh bản trùng. Dữ liệu người dùng tự thêm không bị đụng tới.
 *
 * Tên trường theo Docs/Firestore.md. Chưa có lớp trong model/ nên tạm ghi bằng Map;
 * khi có model/ thì vẫn đọc được bằng document.toObject(...) vì tên trường giống hệt.
 *
 * Cách chạy: mở danh sách Xem thử, bấm "Firebase · Nạp dữ liệu mẫu" (NapDuLieuMauFragment).
 */
object DuLieuMauFirestore {

    /** Ghi tất cả trong một lô (batch): hoặc ghi được hết, hoặc không ghi gì. loi = null là thành công. */
    fun nap(db: FirebaseFirestore = FirebaseFirestore.getInstance(), xong: (loi: Exception?) -> Unit) {
        val lo = db.batch()
        for ((ma, duLieu) in diemDen()) lo.set(db.collection(Collections.DIEM_DEN).document(ma), duLieu)
        for ((ma, duLieu) in tour()) lo.set(db.collection(Collections.TOUR).document(ma), duLieu)
        for ((ma, duLieu) in dotKhoiHanh()) lo.set(db.collection(Collections.DOT_KHOI_HANH).document(ma), duLieu)
        lo.commit()
            .addOnSuccessListener { xong(null) }
            .addOnFailureListener { loi -> xong(loi) }
    }

    /** Số document mỗi collection sẽ nạp, để màn hình báo cho người dùng */
    val soLuong: String
        get() = "${diemDen().size} điểm đến, ${tour().size} tour, ${dotKhoiHanh().size} đợt khởi hành"

    // Ngày giờ lưu kiểu Long (mili giây), tính theo giờ Việt Nam
    private fun ngay(nam: Int, thang: Int, ngay: Int, gio: Int = 0): Long {
        val lich = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
        lich.clear()
        lich.set(nam, thang - 1, ngay, gio, 0, 0)
        return lich.timeInMillis
    }

    private fun diemDen(): Map<String, Map<String, Any>> {
        fun dd(ten: String, tinh: String, moTa: String, viDo: Double, kinhDo: Double) =
            mapOf("ten" to ten, "tinhThanh" to tinh, "moTa" to moTa, "viDo" to viDo, "kinhDo" to kinhDo, "anh" to "")
        return linkedMapOf(
            "DD01" to dd("Đà Lạt", "Lâm Đồng", "Thành phố cao nguyên, khí hậu mát quanh năm.", 11.9404, 108.4583),
            "DD02" to dd("Nha Trang", "Khánh Hòa", "Thành phố biển, nhiều đảo đẹp.", 12.2388, 109.1967),
            "DD03" to dd("Hội An", "Quảng Nam", "Phố cổ bên sông Hoài.", 15.8801, 108.3380),
            "DD04" to dd("Phú Quốc", "Kiên Giang", "Đảo ngọc, biển xanh cát trắng.", 10.2899, 103.9840),
            "DD05" to dd("Hạ Long", "Quảng Ninh", "Vịnh di sản với hàng nghìn đảo đá.", 20.9101, 107.1839),
            "DD06" to dd("Sa Pa", "Lào Cai", "Ruộng bậc thang và núi Fansipan.", 22.3364, 103.8438),
        )
    }

    // Một điểm dừng trong ngày của lịch trình
    private fun diemDung(ten: String, gio: String, viDo: Double, kinhDo: Double) =
        mapOf("ten" to ten, "gio" to gio, "viDo" to viDo, "kinhDo" to kinhDo)

    // Một ngày trong lichTrinh
    private fun ngayLichTrinh(so: Long, tieuDe: String, moTa: String, dung: List<Map<String, Any>>) =
        mapOf("ngay" to so, "tieuDe" to tieuDe, "moTa" to moTa, "diemDung" to dung)

    private val benXeMienDong = mapOf("ten" to "Bến xe Miền Đông", "viDo" to 10.8150, "kinhDo" to 106.7110)

    private fun tour(): Map<String, Map<String, Any>> {
        fun t(
            ten: String, maDiemDen: String, tenDiemDen: String, soNgay: Long, soDem: Long, giaTu: Long,
            moTa: String, noiBat: Boolean, hienThi: Boolean, lichTrinh: List<Map<String, Any>>
        ) = mapOf(
            "ten" to ten, "maDiemDen" to maDiemDen, "tenDiemDen" to tenDiemDen,
            "soNgay" to soNgay, "soDem" to soDem, "giaTu" to giaTu, "moTa" to moTa,
            "anh" to emptyList<String>(), "noiBat" to noiBat, "hienThi" to hienThi,
            "diemTapTrung" to benXeMienDong, "lichTrinh" to lichTrinh
        )
        return linkedMapOf(
            "T01" to t(
                "Đà Lạt 3 ngày 2 đêm", "DD01", "Đà Lạt", 3, 2, 2_500_000,
                "Đồi chè Cầu Đất, thác Datanla, chợ đêm Đà Lạt.", noiBat = true, hienThi = true,
                lichTrinh = listOf(
                    ngayLichTrinh(1, "Khởi hành, nhận phòng",
                        "06:00 đón khách tại TP.HCM. Chiều nhận phòng khách sạn, tối dạo chợ đêm Đà Lạt.",
                        listOf(diemDung("Bến xe Miền Đông", "06:00", 10.8150, 106.7110),
                            diemDung("Chợ đêm Đà Lạt", "19:00", 11.9429, 108.4372))),
                    ngayLichTrinh(2, "Đồi chè và thác Datanla",
                        "Sáng tham quan đồi chè Cầu Đất. Chiều trượt máng ở thác Datanla, tối tự do.",
                        listOf(diemDung("Đồi chè Cầu Đất", "08:00", 11.8660, 108.5560),
                            diemDung("Thác Datanla", "14:00", 11.9020, 108.4490))),
                    ngayLichTrinh(3, "Chợ Đà Lạt, trở về",
                        "Sáng mua đặc sản ở chợ Đà Lạt, trả phòng lúc 11:00. Về đến TP.HCM khoảng 19:00.",
                        listOf(diemDung("Chợ Đà Lạt", "08:30", 11.9429, 108.4372)))
                )
            ),
            "T02" to t(
                "Nha Trang biển đảo 4 ngày", "DD02", "Nha Trang", 4, 3, 3_900_000,
                "Đi cano ra đảo, tắm bùn khoáng, hải sản.", noiBat = true, hienThi = true,
                lichTrinh = listOf(
                    ngayLichTrinh(1, "Khởi hành, nhận phòng", "Đón khách tại TP.HCM, chiều nhận phòng.", emptyList()),
                    ngayLichTrinh(2, "Tham quan đảo", "Đi cano ra Hòn Mun, lặn ngắm san hô.", emptyList()),
                    ngayLichTrinh(3, "Tắm bùn khoáng", "Sáng tắm bùn, chiều tự do.", emptyList()),
                    ngayLichTrinh(4, "Trở về", "Trả phòng, về TP.HCM.", emptyList())
                )
            ),
            "T03" to t(
                "Phú Quốc nghỉ dưỡng 4 ngày", "DD04", "Phú Quốc", 4, 3, 5_200_000,
                "Nghỉ dưỡng biển, cáp treo Hòn Thơm.", noiBat = false, hienThi = false,
                lichTrinh = listOf(ngayLichTrinh(1, "Bay ra đảo, nhận phòng", "Đón khách tại sân bay Phú Quốc.", emptyList()))
            ),
            "T04" to t(
                "Hạ Long du thuyền 2 ngày", "DD05", "Hạ Long", 2, 1, 3_100_000,
                "Ngủ đêm trên du thuyền giữa vịnh.", noiBat = false, hienThi = true,
                lichTrinh = listOf(
                    ngayLichTrinh(1, "Lên du thuyền", "Trưa lên tàu, chiều chèo kayak.", emptyList()),
                    ngayLichTrinh(2, "Hang Sửng Sốt, trở về", "Sáng thăm hang, trưa về bến.", emptyList())
                )
            ),
            "T05" to t(
                "Đà Lạt săn mây 2 ngày 1 đêm", "DD01", "Đà Lạt", 2, 1, 1_800_000,
                "Săn mây đồi Đa Phú lúc bình minh.", noiBat = false, hienThi = true,
                lichTrinh = listOf(
                    ngayLichTrinh(1, "Khởi hành", "Đêm đi xe giường nằm lên Đà Lạt.", emptyList()),
                    ngayLichTrinh(2, "Săn mây, trở về", "05:00 lên đồi săn mây, chiều về TP.HCM.", emptyList())
                )
            ),
        )
    }

    private fun dotKhoiHanh(): Map<String, Map<String, Any>> {
        fun dt(maTour: String, ngayDi: Long, ngayVe: Long, giaNguoiLon: Long, giaTreEm: Long, tongCho: Long, daDat: Long, trangThai: String) =
            mapOf(
                "maTour" to maTour, "ngayDi" to ngayDi, "ngayVe" to ngayVe,
                "giaNguoiLon" to giaNguoiLon, "giaTreEm" to giaTreEm,
                "tongCho" to tongCho, "daDat" to daDat, "trangThai" to trangThai
            )
        return linkedMapOf(
            // 4 đợt của T01 giống dữ liệu mẫu ở A15
            "DT01" to dt("T01", ngay(2026, 11, 20), ngay(2026, 11, 22), 2_500_000, 1_500_000, 4, 3, "MO"),
            "DT02" to dt("T01", ngay(2026, 12, 4), ngay(2026, 12, 6), 2_700_000, 1_620_000, 20, 0, "MO"),
            "DT03" to dt("T01", ngay(2026, 10, 30), ngay(2026, 11, 1), 2_400_000, 1_440_000, 20, 20, "DONG"),
            "DT04" to dt("T01", ngay(2026, 12, 18), ngay(2026, 12, 20), 2_800_000, 1_680_000, 16, 0, "HUY"),
            "DT05" to dt("T02", ngay(2026, 12, 5), ngay(2026, 12, 8), 3_900_000, 2_340_000, 20, 8, "MO"),
            "DT06" to dt("T04", ngay(2026, 12, 12), ngay(2026, 12, 13), 3_100_000, 1_860_000, 12, 12, "MO"),
            "DT07" to dt("T05", ngay(2026, 11, 28), ngay(2026, 11, 29), 1_800_000, 1_080_000, 15, 7, "MO"),
        )
    }
}
