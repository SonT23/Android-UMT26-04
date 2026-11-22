package vn.edu.umt.tour.data

/**
 * Tên các collection trên Firestore, đúng theo Docs/Firestore.md.
 * Luôn dùng hằng này thay vì gõ chuỗi, vd. db.collection(Collections.TOUR), để gõ sai tên là báo lỗi ngay khi build.
 * Đổi hay thêm collection thì sửa Docs/Firestore.md trước, báo nhóm, rồi mới sửa file này.
 */
object Collections {
    const val NGUOI_DUNG = "nguoi_dung"       // id document = uid của Firebase Authentication
    const val DIEM_DEN = "diem_den"           // id "DD01"...
    const val TOUR = "tour"                   // id "T01"...
    const val DOT_KHOI_HANH = "dot_khoi_hanh" // id "DT01"...
    const val DON_DAT = "don_dat"             // id "DH00124"...
    const val THANH_TOAN = "thanh_toan"       // id tự sinh
}
