package vn.edu.umt.tour.ui.khach.khampha

import androidx.fragment.app.Fragment
import vn.edu.umt.tour.R

// Mở một màn hình con thay cho màn hình hiện tại, trong khung chung có id "khung"
// (XemThuActivity, KhachActivity đều có). addToBackStack để bấm Back quay về màn hình hiện tại.
// Cách làm này giống mục "Nối các màn hình" trong README.
fun Fragment.moManHinhCon(manHinh: Fragment) {
    parentFragmentManager.beginTransaction()
        .replace(R.id.khung, manHinh)
        .addToBackStack(null)
        .commit()
}

// Quay về màn hình trước, giống bấm nút Back của máy
fun Fragment.quayLai() {
    requireActivity().onBackPressedDispatcher.onBackPressed()
}
