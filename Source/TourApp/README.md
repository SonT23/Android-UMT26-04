# TourApp — ứng dụng đặt tour du lịch trong nước

Project Android Studio của nhóm 04 (Kotlin, giao diện XML). Package `vn.edu.umt.tour`.

## Mở và chạy

1. Android Studio → **File → Open** → chọn thư mục `Source/TourApp` (không mở thư mục gốc của repo).
2. Chờ Gradle sync xong, chọn máy ảo hoặc điện thoại → **Run**.
3. Dòng lệnh: `./gradlew assembleDebug`, file APK ở `app/build/outputs/apk/debug/`.

## Ai code ở đâu

Theo Proposal mục 7.2. Mỗi người làm trong package của module mình; file dùng chung chỉ sửa sau khi báo nhóm.

| Thư mục trong `java/vn/edu/umt/tour/` | Nội dung | Người làm |
| --- | --- | --- |
| `SplashActivity.kt`, `KhachActivity.kt`, `QuanTriActivity.kt`, `ui/auth/` | Khởi động, điều hướng, đăng nhập, đăng ký | Hoàng |
| `ui/khach/` (Trang chủ, Tìm tour, Chi tiết, Yêu thích, Bản đồ) | Module B | Sơn |
| `model/`, `ui/khach/` (Đặt tour, Đơn của tôi), `work/` | Module C | Thịnh |
| `ui/quantri/` (Điểm đến, Tour, Đợt khởi hành), `data/api/` | Module D, ApiClient | Phúc |
| `ui/quantri/` (Tổng quan, Đơn, Khách hàng), `QuetQrActivity.kt`, `util/` | Module E | Trúc Đào |

## Quy ước tài nguyên

- **Chuỗi:** mỗi module một file riêng ở cả `values/` và `values-en/`: `strings_taikhoan.xml`, `strings_khampha.xml`, `strings_dattour.xml`, `strings_quantritour.xml`, `strings_quantridon.xml`. Không viết chữ cứng trong layout hay code.
- **Màu:** dùng thuộc tính theme (`?attr/colorPrimary`, `?attr/colorOnSurface`…) thay vì mã màu, để giao diện tối tự đổi. Bộ màu ở `values/colors.xml` và `values-night/colors.xml`.
- **Khoảng cách:** dùng `@dimen/space_s` (8dp), `space_m` (16dp), `space_l` (24dp) trong `values/dimens.xml`.
- **Tên layout:** `activity_`, `fragment_`, `item_`, `dialog_` + tên màn hình không dấu, vd. `fragment_trang_chu.xml`.

## Thư viện có sẵn

Material 3, Fragment, RecyclerView, SwipeRefreshLayout, ViewModel, Coroutines, Retrofit + Gson, OkHttp logging, Room (KSP), WorkManager, Coil. Phiên bản ở `gradle/libs.versions.toml`.

Thư viện riêng của module (Google Maps, CameraX, ML Kit, ZXing, MPAndroidChart, Biometric) do người cần tự thêm vào `libs.versions.toml` trong PR chức năng của mình. Coil đang giữ bản 3.4.0: bản 3.5 trở lên cần Kotlin 2.4, Android Studio hiện tại chưa biên dịch được.
