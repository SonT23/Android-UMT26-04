package vn.edu.umt.tour.ui.quantri.don

import androidx.fragment.app.Fragment
import vn.edu.umt.tour.R

fun Fragment.moManHinhCon(manHinh: Fragment) {
    parentFragmentManager.beginTransaction()
        .replace(R.id.khung, manHinh)
        .addToBackStack(null)
        .commit()
}

fun Fragment.quayLai() {
    requireActivity().onBackPressedDispatcher.onBackPressed()
}
