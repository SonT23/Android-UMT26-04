package vn.edu.umt.tour.xemthu

import vn.edu.umt.tour.ui.auth.manHinhA
import vn.edu.umt.tour.ui.khach.dattour.manHinhC
import vn.edu.umt.tour.ui.khach.khampha.manHinhB
import vn.edu.umt.tour.ui.quantri.don.manHinhE
import vn.edu.umt.tour.ui.quantri.tour.manHinhD

/**
 * Gom màn hình của 5 module thành một danh sách.
 * KHÔNG sửa file này khi thêm màn hình: mỗi người chỉ thêm một dòng
 * vào file ManHinhX.kt trong package của mình.
 */
object DanhSachManHinh {
    val tatCa: List<ManHinh> = manHinhA + manHinhB + manHinhC + manHinhD + manHinhE

    // Tên khóa dùng để gửi vị trí màn hình qua Intent
    const val VI_TRI = "vi_tri"
}
