# TourApp — ứng dụng đặt tour du lịch trong nước

Project Android Studio của nhóm 04 (Kotlin, giao diện XML). Package `vn.edu.umt.tour`.

## Mở và chạy

1. Android Studio → **File → Open** → chọn thư mục `Source/TourApp` (không mở thư mục gốc của repo).
2. Chờ Gradle sync xong, chọn máy ảo hoặc điện thoại → **Run**.
3. Dòng lệnh: `./gradlew assembleDebug`, file APK ở `app/build/outputs/apk/debug/`.

## Ai code ở đâu

Cả nhóm làm chung **một project** này, không ai tạo project riêng. Mỗi người có một package riêng trong `java/vn/edu/umt/tour/` và chỉ tạo, sửa file trong package của mình.

| Package | Module | Người làm | File đăng ký màn hình |
| --- | --- | --- | --- |
| `ui/auth/` | A: Tài khoản, phiên, cài đặt | Hoàng | `ManHinhA.kt` |
| `ui/khach/khampha/` | B: Khám phá tour | Sơn | `ManHinhB.kt` |
| `ui/khach/dattour/` | C: Đặt tour, đơn của khách | Thịnh | `ManHinhC.kt` |
| `ui/quantri/tour/` | D: Quản trị tour | Phúc | `ManHinhD.kt` |
| `ui/quantri/don/` | E: Quản trị đơn, khách, thống kê | Trúc Đào | `ManHinhE.kt` |

File dùng chung, chỉ sửa sau khi báo nhóm: `MainActivity.kt`, package `xemthu/`, `AndroidManifest.xml`, `colors.xml`, `themes.xml`, `dimens.xml`, `strings.xml`, file Gradle. Các phần sẽ thêm sau: `SplashActivity`, `KhachActivity`, `QuanTriActivity` (Hoàng), `model/` (Thịnh), `data/api/` (Phúc), `util/` (Trúc Đào).

## Làm giao diện trước, nối sau

**Hướng dẫn riêng cho từng người** (màn hình nào, tên file nào, dùng view gì) nằm ở [Docs/HuongDanUI/](../../Docs/HuongDanUI/). Phần dưới đây là quy định chung.

Mỗi người dựng hết giao diện các màn hình của mình theo Figma, dùng dữ liệu mẫu (cả 25 màn hình đã xong ngày 19/11). Bấm **Run** sẽ thấy danh sách "Xem thử màn hình"; bấm vào một dòng để mở màn hình đó. Bước tiếp theo là nối các màn hình với nhau, xem mục **Nối các màn hình** bên dưới.

Màn hình mẫu để làm theo: **A01 Trang chủ** trong `ui/khach/khampha/`.

### Các bước làm một màn hình

Ví dụ màn hình A06 Đăng nhập của Hoàng:

1. **Tạo layout.** Chuột phải `res/layout` → **New → Layout Resource File** → tên `fragment_a06_dang_nhap`. Dựng giao diện theo Figma.
2. **Thêm chuỗi.** Mọi chữ trên màn hình đặt trong file chuỗi của module (`res/values/strings_taikhoan.xml`), tên bắt đầu bằng mã màn hình: `a06_dang_nhap`, `a06_quen_mat_khau`. Lần đầu thì tạo file bằng chuột phải `res/values` → **New → Values Resource File**. Bản tiếng Anh ở `res/values-en/` có thể bổ sung sau.
3. **Tạo Fragment.** Chuột phải package của mình → **New → Kotlin Class/File** → `DangNhapFragment`:

   ```kotlin
   class DangNhapFragment : Fragment() {
       override fun onCreateView(
           inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
       ): View? {
           return inflater.inflate(R.layout.fragment_a06_dang_nhap, container, false)
       }
   }
   ```

4. **Đăng ký để xem thử.** Mở file `ManHinhX.kt` trong package của mình, thêm một dòng:

   ```kotlin
   val manHinhA: List<ManHinh> = listOf(
       ManHinh("A06", "Đăng nhập") { DangNhapFragment() },
   )
   ```

5. **Run**, bấm vào "A06 · Đăng nhập" trong danh sách để xem.
6. **Commit** riêng màn hình đó: `[+] Layout A06 Đăng nhập`.

Màn hình có danh sách thì làm thêm: một layout `item_...` cho một dòng, một Adapter có ViewHolder (xem `TourNoiBatAdapter.kt`), và dữ liệu mẫu trong một file riêng (xem `DuLieuMau.kt`).

### Quy tắc đặt tên

Thư mục `res/` dùng chung cho cả nhóm và không chia thư mục con được, nên **mọi tài nguyên mình tạo đều mang mã màn hình** để không trùng tên với người khác.

| Loại | Cách đặt tên | Ví dụ |
| --- | --- | --- |
| Layout màn hình | `fragment_<mã>_<tên>` | `fragment_a01_trang_chu.xml` |
| Layout một dòng | `item_<mã>_<tên>` | `item_a01_tour_noi_bat.xml` |
| Layout hộp thoại, bảng trượt | `dialog_<mã>_<tên>` | `dialog_a02_bo_loc.xml` |
| Chuỗi | `<mã>_<tên>` trong file chuỗi của module | `a01_tim_nhanh` |
| Lớp Kotlin | Tên màn hình + `Fragment` hoặc `Adapter` | `TrangChuFragment`, `TourNoiBatAdapter` |
| Id của view | Tên + viết tắt loại view | `tenTV`, `anhIV`, `tourRV`, `datBtn` |

File chuỗi của từng module: `strings_taikhoan.xml` (A), `strings_khampha.xml` (B), `strings_dattour.xml` (C), `strings_quantritour.xml` (D), `strings_quantridon.xml` (E).

### Icon và nền dùng chung

Các file dưới đây nằm sẵn ở `res/drawable/`, ai cũng dùng được và không ai sửa.

| Nhóm | Tên file |
| --- | --- |
| Điều hướng | `ic_arrow_back`, `ic_close`, `ic_chevron_right`, `ic_more_vert` |
| Thao tác | `ic_add`, `ic_remove`, `ic_check`, `ic_search`, `ic_tune`, `ic_filter_list`, `ic_share`, `ic_download`, `ic_drag_handle` |
| Nội dung | `ic_calendar`, `ic_schedule`, `ic_place`, `ic_map`, `ic_my_location`, `ic_phone`, `ic_message`, `ic_person`, `ic_lock`, `ic_logout`, `ic_visibility`, `ic_notifications`, `ic_favorite`, `ic_favorite_border`, `ic_image` |
| Nền nhãn | `bg_nhan` (cam), `bg_nhan_xanh` (xanh lá), `bg_nhan_xanh_duong` (xanh dương), `bg_nhan_do` (đỏ), `bg_nhan_xam` (xám) |
| Nền khác | `bg_o_tim` (ô tìm bo tròn), `bg_anh_trong` (ô xám giữ chỗ cho ảnh), `bg_tron_trang`, `bg_tron_nhat` (hình tròn) |

Icon có màu đen, đổi màu bằng `app:tint`:

```xml
<ImageView
    android:layout_width="24dp"
    android:layout_height="24dp"
    android:contentDescription="@string/a12_quay_lai"
    app:srcCompat="@drawable/ic_arrow_back"
    app:tint="?attr/colorOnSurface" />
```

Cần icon chưa có: chuột phải `res/drawable` → **New → Vector Asset** → bấm vào hình ở ô *Clip art* để tìm, đặt tên `ic_<tên>`. Cần nền riêng cho màn hình của mình thì đặt tên có mã màn hình, vd. `bg_a05_tron_xanh.xml`.

### Bảng tra: thành phần trong Figma dựng bằng gì

Các đoạn XML dưới đây đã chạy thử với theme của project, chép vào rồi đổi `id` và chuỗi.

**Ô nhập có viền và nhãn**

```xml
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/emailTIL"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="@string/a06_email">

    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/emailET"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="textEmailAddress" />
</com.google.android.material.textfield.TextInputLayout>
```

Ô mật khẩu: thêm `app:endIconMode="password_toggle"` vào `TextInputLayout` và đổi `inputType` thành `textPassword`. Dòng gợi ý dưới ô: `app:helperText="@string/..."`.

**Nút.** Thẻ `Button` thường là nút chính nền xanh. Các kiểu khác thêm thuộc tính `style`:

| Kiểu nút | Thuộc tính `style` |
| --- | --- |
| Nút chính (nền xanh) | không cần |
| Nút viền | `@style/Widget.Material3.Button.OutlinedButton` |
| Nút chữ | `@style/Widget.Material3.Button.TextButton` |
| Nút nhạt (nền xanh nhạt) | `@style/Widget.Material3.Button.TonalButton` |

Nút có icon thêm `app:icon="@drawable/ic_add"`. Nút rộng hết màn hình đặt `android:layout_height="@dimen/button_height"`.

**Hàng chip lọc** (chọn một trong nhiều)

```xml
<com.google.android.material.chip.ChipGroup
    android:id="@+id/locCG"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:selectionRequired="true"
    app:singleSelection="true">

    <com.google.android.material.chip.Chip
        android:id="@+id/tatCaChip"
        style="@style/Widget.Material3.Chip.Filter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:checked="true"
        android:text="@string/a13_tat_ca" />

    <com.google.android.material.chip.Chip
        android:id="@+id/dangHienChip"
        style="@style/Widget.Material3.Chip.Filter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/a13_dang_hien" />
</com.google.android.material.chip.ChipGroup>
```

Hàng chip dài hơn màn hình thì đặt `ChipGroup` trong một `HorizontalScrollView` và thêm `app:singleLine="true"`.

**Nhãn trạng thái** ("Còn 1 chỗ", "Đang hiện", "Đã hủy"…)

```xml
<TextView
    android:id="@+id/trangThaiTV"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="@drawable/bg_nhan_xanh"
    android:paddingStart="8dp"
    android:paddingTop="2dp"
    android:paddingEnd="8dp"
    android:paddingBottom="2dp"
    android:textAppearance="?attr/textAppearanceLabelSmall"
    android:textColor="@color/on_trang_thai_tot" />
```

| Nền | Màu chữ đi kèm | Dùng cho |
| --- | --- | --- |
| `bg_nhan` | `?attr/colorOnTertiaryContainer` | Còn N chỗ, Chờ xác nhận, Nổi bật |
| `bg_nhan_xanh` | `@color/on_trang_thai_tot` | Đang hiện, Mở, Hoàn thành |
| `bg_nhan_xanh_duong` | `?attr/colorOnPrimaryContainer` | Đã xác nhận |
| `bg_nhan_do` | `?attr/colorOnErrorContainer` | Đã hủy |
| `bg_nhan_xam` | `?attr/colorOnSurfaceVariant` | Đang ẩn, Đã đóng, Hết chỗ |

**Các thành phần khác**

| Trong Figma | Dùng |
| --- | --- |
| Thẻ có viền bo góc | `com.google.android.material.card.MaterialCardView` với `style="?attr/materialCardViewOutlinedStyle"` (xem `item_a01_tour_noi_bat.xml`) |
| Công tắc bật tắt | `com.google.android.material.materialswitch.MaterialSwitch`, chữ đặt ở `android:text` |
| Ô chọn một giá trị từ danh sách | `Spinner` với `ArrayAdapter` |
| Chọn một trong vài lựa chọn | `RadioGroup` chứa các `RadioButton` |
| Thanh tiến độ ngang | `<ProgressBar style="?android:attr/progressBarStyleHorizontal" android:max="20" android:progress="12" ... />` |
| Vòng xoay đang tải | `ProgressBar` không cần `style` |
| Nút nổi "Thêm …" | `com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton` với `app:icon` và `android:text` |
| Ô tìm kiếm bo tròn | `EditText` hoặc `TextView` nền `@drawable/bg_o_tim` (xem `fragment_a01_trang_chu.xml`) |
| Ảnh chưa có | `ImageView` nền `@drawable/bg_anh_trong`, hình `@drawable/ic_image`, `app:tint="?attr/colorOutline"` |
| Ảnh đại diện bằng chữ cái | `TextView` vuông, nền `@drawable/bg_tron_nhat`, `android:gravity="center"` |
| Đường kẻ ngang | `View` cao `1dp`, nền `?attr/colorOutline` |
| Danh sách | `RecyclerView` + Adapter có ViewHolder (xem `TourNoiBatAdapter.kt`) |
| Danh sách nằm trong `ScrollView` | `RecyclerView` thêm `android:nestedScrollingEnabled="false"` |
| Màn hình dài hơn chiều cao máy | bọc nội dung trong `ScrollView` |

### Lưu ý khi dựng layout

- **Không vẽ thanh điều hướng dưới** trong layout của mình. Nó thuộc `KhachActivity` và `QuanTriActivity`.
- **Màu:** dùng thuộc tính theme (`?attr/colorPrimary`, `?attr/colorOnSurface`, `?attr/colorSurfaceVariant`…), không gõ mã màu, để giao diện tối tự đổi theo.
- **Khoảng cách:** dùng `@dimen/space_xs` (4dp), `space_s` (8dp), `space_m` (16dp), `space_l` (24dp).
- **Cỡ chữ:** dùng `android:textAppearance="?attr/textAppearanceTitleLarge"` (và `BodyLarge`, `BodyMedium`, `LabelLarge`…) thay cho `textSize`.
- **Ảnh:** chưa có ảnh thật thì dùng `ImageView` với nền `@drawable/bg_anh_trong` và hình `@drawable/ic_image` như màn hình A01.
- **Xem trước không cần chạy:** thêm `tools:text="..."` vào `TextView` và `tools:listitem="@layout/item_..."` vào `RecyclerView` để thấy dữ liệu mẫu ngay trong Layout Editor.

## Nối các màn hình (điều hướng)

Nối dần từ bây giờ, không đợi làm xong hết chức năng rồi mới ghép. Chức năng nào đi qua nhiều màn hình (A03 Chi tiết tour → A04 Đặt tour → A05 Đặt thành công) thì nối luôn khi làm chức năng đó. Như vậy màn hình nhận dữ liệu từ màn hình trước ngay từ đầu, không phải viết lại lúc ghép.

### Ai nối phần nào

| Lớp | Nội dung | Người làm |
| --- | --- | --- |
| Khung | `SplashActivity`, `AuthActivity`, `KhachActivity`, `QuanTriActivity`; thanh điều hướng dưới gắn các màn hình gốc | Hoàng |
| Trong module | Từ màn hình của mình mở màn hình con của mình | Mỗi người, làm cùng lúc với chức năng |
| Giữa các module | Màn hình người này mở màn hình người kia, có truyền dữ liệu | Hai người liên quan (bảng dưới) |

Tab ở thanh điều hướng dưới (Hoàng chốt):

| Activity | Các tab |
| --- | --- |
| `KhachActivity` | A01 Trang chủ · A02 Tìm tour · A21 Yêu thích · A08 Đơn của tôi · A10 Tài khoản |
| `QuanTriActivity` | A11 Tổng quan · A13 Tour · A16 Đơn · A18 Khách hàng · A10 Tài khoản |

Các chỗ nối có truyền dữ liệu:

| Từ → đến | Truyền | Bên gửi | Bên nhận |
| --- | --- | --- | --- |
| A00 Khởi động, A06 Đăng nhập → `KhachActivity` hoặc `QuanTriActivity` | vai trò | Hoàng | Hoàng |
| A01, A02, A21 → A03 Chi tiết tour | mã tour | Sơn | Sơn |
| A03 Chi tiết tour → A04 Đặt tour | mã tour, mã đợt | Sơn | Thịnh |
| A05 Đặt thành công, A08 Đơn của tôi → A09 Chi tiết đơn | mã đơn | Thịnh | Thịnh |
| A13 Danh sách tour → A14 Thêm, sửa tour, A15 Đợt khởi hành | mã tour (thêm mới thì không có) | Phúc | Phúc |
| A14 → A24 Chọn vị trí → **trả tọa độ về** A14 | vĩ độ, kinh độ | Phúc | Phúc |
| A16 Danh sách đơn, A23 Quét QR → A17 Xử lý đơn | mã đơn | Trúc Đào | Trúc Đào |

### Mở màn hình con

Mọi Activity chứa Fragment của nhóm dùng **một khung chung có id `khung`**. `XemThuActivity` đã dùng id này; `KhachActivity` và `QuanTriActivity` cũng đặt `FrameLayout` id `khung`. Nhờ vậy code dưới đây chạy được ngay trong danh sách Xem thử, và chạy trong app thật mà không phải sửa.

```kotlin
// Trong TrangChuFragment, khi bấm vào một tour
parentFragmentManager.beginTransaction()
    .replace(R.id.khung, ChiTietTourFragment.newInstance(tour.ma))
    .addToBackStack(null)
    .commit()
```

- `replace` thay màn hình đang hiện trong `khung` bằng màn hình mới.
- `addToBackStack(null)` ghi lại bước này, bấm Back sẽ quay về màn hình trước. Thiếu dòng này thì Back thoát luôn khỏi Activity.
- Nhóm không dùng Navigation Component: nó cần một file sơ đồ điều hướng dùng chung, 5 người cùng sửa sẽ xung đột liên tục.

### Nút Quay lại (←) và nút Hủy, Đóng trong màn hình

Không gọi `requireActivity().finish()` (đóng cả Activity, trong app thật là văng khỏi cả phần khách hoặc quản trị), cũng không gọi `parentFragmentManager.popBackStack()` (màn hình mở thẳng từ danh sách Xem thử không nằm trong back stack, nên bấm không có phản ứng gì). Dùng:

```kotlin
quayLaiIV.setOnClickListener {
    requireActivity().onBackPressedDispatcher.onBackPressed()
}
```

Lệnh này làm đúng như bấm nút Back của máy: có màn hình trước thì quay về màn hình đó, không có thì đóng Activity (trong danh sách Xem thử là quay về danh sách).

### Truyền dữ liệu: `newInstance` + `arguments`

Màn hình **nhận** tự khai báo mình cần tham số gì. Màn hình gửi chỉ gọi `newInstance(...)`, không phải nhớ tên khóa.

```kotlin
class ChiTietTourFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val maTour = requireArguments().getString(MA_TOUR)!!
        // Tìm tour theo maTour trong DuLieuMau; tuần 9 đổi sang đọc Firestore
    }

    companion object {
        private const val MA_TOUR = "ma_tour"

        fun newInstance(maTour: String) = ChiTietTourFragment().apply {
            arguments = bundleOf(MA_TOUR to maTour)
        }
    }
}
```

- **Không truyền qua constructor** (`ChiTietTourFragment(maTour)`). Khi xoay máy, hoặc app bị hệ thống tắt rồi mở lại, Android tạo lại Fragment bằng constructor rỗng: tham số mất và app văng. `arguments` thì được Android tự lưu và trả lại.
- **Chỉ truyền mã**, không truyền cả đối tượng. Màn hình nhận tự lấy dữ liệu theo mã. Để mã kiểu `String` ngay từ bây giờ, vì mã document của Firestore là chuỗi.
- `bundleOf` import từ `androidx.core.os.bundleOf`.
- Màn hình đã có `newInstance` thì sửa luôn dòng đăng ký trong `ManHinhX.kt` để danh sách Xem thử truyền một mã mẫu. Nếu không, `requireArguments()` sẽ báo lỗi khi mở từ danh sách:

  ```kotlin
  ManHinh("A03", "Chi tiết tour") { ChiTietTourFragment.newInstance("T01") },
  ```

- Nối sang màn hình của người khác: người **nhận** viết `newInstance` trước (một PR nhỏ), người gửi chỉ việc gọi.

### Trả kết quả về màn hình trước

Dùng khi màn hình con chọn xong một giá trị rồi đóng lại, ví dụ A24 Chọn vị trí trả tọa độ về A14. Fragment Result có sẵn trong `fragment-ktx`:

```kotlin
// A24 ChonViTriFragment: khi bấm Xác nhận thì gửi kết quả rồi đóng
setFragmentResult(KET_QUA, bundleOf(VI_DO to viDo, KINH_DO to kinhDo))
requireActivity().onBackPressedDispatcher.onBackPressed()

companion object {
    const val KET_QUA = "a24_ket_qua"
    const val VI_DO = "vi_do"
    const val KINH_DO = "kinh_do"
}
```

```kotlin
// A14 ThemSuaTourFragment: đăng ký nhận trong onCreate
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setFragmentResultListener(ChonViTriFragment.KET_QUA) { _, bundle ->
        val viDo = bundle.getDouble(ChonViTriFragment.VI_DO)
        val kinhDo = bundle.getDouble(ChonViTriFragment.KINH_DO)
        // hiện tọa độ lên form
    }
}
```

Dòng cuối đóng màn hình hiện tại, quay về màn hình trước (giống bấm Back, xem mục "Nút Quay lại"). Kết quả được giữ lại và giao cho A14 khi A14 hiện lên. Import: `androidx.fragment.app.setFragmentResult`, `androidx.fragment.app.setFragmentResultListener`.

### Chuyển giữa các Activity (phần của Hoàng)

- Đăng nhập xong: mở `KhachActivity` hoặc `QuanTriActivity` bằng `startActivity(Intent(...))` rồi gọi `finish()`, để bấm Back không quay lại màn hình đăng nhập. Đăng xuất làm ngược lại.
- Bấm sang tab khác: xóa các màn hình con đang mở của tab cũ (`popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)`) rồi `replace` màn hình gốc của tab mới vào `khung`.
- Các bạn khác không cần đụng vào phần này. Fragment của mình gắn vào khung là chạy.

### Danh sách Xem thử

Giữ lại tới tuần 10 để mở riêng từng màn hình khi làm và test. `MainActivity` tạm và package `xemthu/` chỉ gỡ ở bước hoàn thiện cuối cùng.

## Thư viện có sẵn

Material 3, Fragment, RecyclerView, SwipeRefreshLayout, ViewModel, Coroutines, Retrofit + Gson, OkHttp logging, Room (KSP), WorkManager, Coil. Phiên bản ở `gradle/libs.versions.toml`.

Thư viện riêng của module (Google Maps, CameraX, ML Kit, ZXing, MPAndroidChart, Biometric) do người cần tự thêm vào `libs.versions.toml` trong PR chức năng của mình. Coil đang giữ bản 3.4.0: bản 3.5 trở lên cần Kotlin 2.4, Android Studio hiện tại chưa biên dịch được.
