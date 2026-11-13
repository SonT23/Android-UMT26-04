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

Giai đoạn này mỗi người dựng hết giao diện các màn hình của mình theo Figma, dùng dữ liệu mẫu. Chưa cần API, chưa cần database, chưa cần khung điều hướng. Bấm **Run** sẽ thấy danh sách "Xem thử màn hình"; bấm vào một dòng để mở màn hình đó.

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

Icon và nền dùng chung (`ic_search`, `ic_favorite`, `ic_favorite_border`, `ic_notifications`, `ic_image`, `bg_nhan`, `bg_o_tim`, `bg_anh_trong`, `bg_tron_trang`) nằm ở `res/drawable/`, ai cũng dùng được. Cần icon mới: chuột phải `res/drawable` → **New → Vector Asset**, đặt tên `ic_<tên>`; xem trong thư mục đã có chưa trước khi thêm.

### Lưu ý khi dựng layout

- **Không vẽ thanh điều hướng dưới** trong layout của mình. Nó thuộc `KhachActivity` và `QuanTriActivity`.
- **Màu:** dùng thuộc tính theme (`?attr/colorPrimary`, `?attr/colorOnSurface`, `?attr/colorSurfaceVariant`…), không gõ mã màu, để giao diện tối tự đổi theo.
- **Khoảng cách:** dùng `@dimen/space_xs` (4dp), `space_s` (8dp), `space_m` (16dp), `space_l` (24dp).
- **Cỡ chữ:** dùng `android:textAppearance="?attr/textAppearanceTitleLarge"` (và `BodyLarge`, `BodyMedium`, `LabelLarge`…) thay cho `textSize`.
- **Ảnh:** chưa có ảnh thật thì dùng `ImageView` với nền `@drawable/bg_anh_trong` và hình `@drawable/ic_image` như màn hình A01.
- **Xem trước không cần chạy:** thêm `tools:text="..."` vào `TextView` và `tools:listitem="@layout/item_..."` vào `RecyclerView` để thấy dữ liệu mẫu ngay trong Layout Editor.

### Khi nối các phần lại

Khung điều hướng xong thì Fragment được gắn thẳng vào `KhachActivity` hoặc `QuanTriActivity`, không phải sửa layout. Dữ liệu mẫu được thay bằng lớp trong `model/` và lời gọi API. Danh sách xem thử (`MainActivity` tạm và package `xemthu/`) sẽ được gỡ bỏ ở bước đó.

## Thư viện có sẵn

Material 3, Fragment, RecyclerView, SwipeRefreshLayout, ViewModel, Coroutines, Retrofit + Gson, OkHttp logging, Room (KSP), WorkManager, Coil. Phiên bản ở `gradle/libs.versions.toml`.

Thư viện riêng của module (Google Maps, CameraX, ML Kit, ZXing, MPAndroidChart, Biometric) do người cần tự thêm vào `libs.versions.toml` trong PR chức năng của mình. Coil đang giữ bản 3.4.0: bản 3.5 trở lên cần Kotlin 2.4, Android Studio hiện tại chưa biên dịch được.
