package vn.edu.umt.tour.ui.khach.khampha

import vn.edu.umt.tour.data.local.YeuThich
import vn.edu.umt.tour.data.local.YeuThichDao

// B5 Yêu thích: hai hàm dùng chung cho nút tim ở A01, A02, A03.
// Cả hai là suspend vì gọi Room, nên phải chạy trong lifecycleScope.launch { }.

// Đọc các tour đã thích trong Room rồi tô tim cho đúng từng tour trong danh sách
suspend fun danhDauYeuThich(dao: YeuThichDao, tours: List<TourMau>) {
    val daThich = dao.layTatCaMa().toSet()
    for (tour in tours) {
        tour.yeuThich = tour.ma in daThich
    }
}

// Lưu tour vào Room nếu vừa thả tim, xóa khỏi Room nếu vừa bỏ tim
suspend fun luuYeuThich(dao: YeuThichDao, tour: TourMau) {
    if (tour.yeuThich) {
        dao.them(YeuThich(tour.ma, tour.ten, tour.moTa, tour.gia, System.currentTimeMillis()))
    } else {
        dao.xoa(tour.ma)
    }
}
