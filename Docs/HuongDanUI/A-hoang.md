# Module A: Tài khoản, phiên, cài đặt (Hoàng)

Đọc [README.md](README.md) của thư mục này trước. File này chỉ ghi phần riêng của module A.

## Nơi làm việc

| Mục | Giá trị |
|---|---|
| Branch | `feature/layout-tai-khoan` |
| Package Kotlin | `Source/TourApp/app/src/main/java/vn/edu/umt/tour/ui/auth/` |
| File đăng ký màn hình | `ui/auth/ManHinhA.kt` |
| File chuỗi | `res/values/strings_taikhoan.xml` (tự tạo; bản tiếng Anh ở `res/values-en/` làm sau cũng được) |
| Ảnh Figma | [Docs/Figma/A-tai-khoan/](../Figma/A-tai-khoan/) |

## Các màn hình cần làm

| Mã | Ảnh Figma | Layout | Lớp Kotlin |
|---|---|---|---|
| A06 | [A06-dang-nhap.png](../Figma/A-tai-khoan/A06-dang-nhap.png) | `fragment_a06_dang_nhap.xml` | `DangNhapFragment` |
| A07 | [A07-dang-ky.png](../Figma/A-tai-khoan/A07-dang-ky.png) | `fragment_a07_dang_ky.xml` | `DangKyFragment` |
| A00 | [A00-khoi-dong.png](../Figma/A-tai-khoan/A00-khoi-dong.png) | `fragment_a00_khoi_dong.xml` | `KhoiDongFragment` |
| A10 | [A10-tai-khoan.png](../Figma/A-tai-khoan/A10-tai-khoan.png) | `fragment_a10_tai_khoan.xml` | `TaiKhoanFragment` |
| A19 | [A19-cai-dat.png](../Figma/A-tai-khoan/A19-cai-dat.png) | `fragment_a19_cai_dat.xml` | `CaiDatFragment` |
| A20 | [A20-quen-mat-khau.png](../Figma/A-tai-khoan/A20-quen-mat-khau.png) | `fragment_a20_quen_mat_khau.xml` | `QuenMatKhauFragment` |

Nên làm theo thứ tự trong bảng: A06 đơn giản nhất và các màn hình sau dùng lại cách làm ô nhập, nút của nó. Module A không có danh sách nên không cần Adapter.

## Chỉ dẫn từng màn hình

### A06 Đăng nhập

- Gốc là `ConstraintLayout`. Từ trên xuống: logo, tiêu đề, dòng mô tả, 2 ô nhập, dòng "Quên mật khẩu?", nút chính, dòng "hoặc", nút viền, 2 dòng chữ cuối.
- Logo: `ImageView` 56dp dùng `@drawable/ic_place`, nền bo góc màu `?attr/colorPrimary`. Nền này tự tạo: `res/drawable/bg_a06_logo.xml` (chép `bg_anh_trong.xml` rồi đổi màu).
- Ô Email và Mật khẩu: `TextInputLayout` (đoạn XML ở bảng tra trong README của TourApp). Ô mật khẩu thêm `app:endIconMode="password_toggle"` là có sẵn biểu tượng con mắt.
- "Quên mật khẩu?", "Đăng ký", "Xem tour không cần đăng nhập": `TextView` màu `?attr/colorPrimary`.
- Dòng "hoặc": hai `View` cao 1dp màu `?attr/colorOutline` hai bên một `TextView`.
- Nút "Đăng nhập bằng vân tay": nút viền có `app:icon`. Biểu tượng vân tay chưa có sẵn: chuột phải `res/drawable` → **New → Vector Asset** → tìm "fingerprint" → đặt tên `ic_fingerprint`.
- Bấm nút lúc này chỉ cần hiện `Toast`. Kiểm tra dữ liệu và gọi API làm sau.

### A07 Đăng ký

- Form dài nên bọc toàn bộ trong `ScrollView`, bên trong là một `LinearLayout` dọc.
- Thanh trên: `ImageView` `@drawable/ic_arrow_back` và `TextView` tiêu đề nằm ngang.
- 5 ô nhập như A06. Dòng "Gồm 10 chữ số" dưới ô số điện thoại là `app:helperText` của `TextInputLayout`.
- Ô mật khẩu viền đỏ kèm dòng "Mật khẩu cần từ 8 ký tự" trong ảnh là trạng thái báo lỗi. Trong code gọi `matKhauTIL.error = getString(R.string.a07_loi_mat_khau)` để xem thử; quy tắc kiểm tra thật làm sau.
- Ô đồng ý điều khoản: `CheckBox`.

### A00 Khởi động

- Nền cả màn hình: `android:background="?attr/colorPrimary"`, chữ màu `?attr/colorOnPrimary`.
- Logo ở giữa (ô vuông trắng bo góc chứa `ic_place`), tên ứng dụng, dòng mô tả, `ProgressBar` (loại tròn mặc định), dòng "Đang kiểm tra đăng nhập…", số phiên bản sát đáy.
- Theo Proposal màn hình này sẽ là `SplashActivity`. Giờ cứ dựng thành Fragment để xem thử; khi làm khung điều hướng thì dùng lại nguyên file layout.

### A10 Tài khoản

- Ảnh đại diện: `TextView` 80dp chứa chữ cái đầu, nền `@drawable/bg_tron_nhat`, chữ căn giữa. Nút máy ảnh nhỏ ở góc: `ImageView` 28dp, biểu tượng tự thêm bằng Vector Asset (`ic_photo_camera`).
- Tên, email, tiêu đề nhóm "THÔNG TIN CÁ NHÂN", 2 ô nhập, nút "Lưu thay đổi".
- Ba dòng "Đổi mật khẩu", "Cài đặt", "Đăng xuất": mỗi dòng là một `LinearLayout` ngang cao 48dp gồm `ImageView` (`ic_lock`, tự thêm `ic_settings`, `ic_logout`), `TextView` (`layout_weight="1"`), `ImageView` `ic_chevron_right`. Giữa các dòng có `View` cao 1dp làm đường kẻ. Dòng "Đăng xuất" màu `?attr/colorError` và không có mũi tên.
- Biểu tượng bánh răng ở góc trên phải: `ImageView` `ic_settings`.
- **Không vẽ thanh điều hướng dưới** có trong ảnh.

### A19 Cài đặt

- Ba nhóm "Chung", "Thông báo", "Bảo mật": tiêu đề nhóm là `TextView` màu `?attr/colorPrimary`, giữa các nhóm có đường kẻ 1dp.
- "Ngôn ngữ": dòng gồm biểu tượng (tự thêm `ic_language`), tên mục và giá trị "Tiếng Việt" bên dưới.
- "Giao diện" Sáng / Tối / Theo máy: `RadioGroup` nằm ngang chứa 3 `RadioButton`.
- "Thông báo đơn hàng", "Nhắc lịch khởi hành", "Mở khóa bằng vân tay": mỗi dòng gồm tên mục, dòng mô tả nhỏ và một `MaterialSwitch` bên phải.
- "Phiên bản 1.0.0": `TextView` cuối màn hình.

### A20 Quên mật khẩu

- Chức năng có 3 bước (Email → Mã OTP → Mật khẩu mới). Ảnh Figma vẽ bước 2; dựng bước 2 trước.
- Hàng chỉ bước: 3 `TextView` tròn 28dp (bước đã qua dùng `ic_check`, bước hiện tại nền `?attr/colorPrimary`, bước chưa tới nền `?attr/colorSurfaceVariant`), nối bằng 2 `View` cao 2dp, bên dưới là 3 nhãn.
- 6 ô nhập mã: `LinearLayout` ngang chứa 6 `EditText`, mỗi ô `layout_weight="1"`, `android:maxLength="1"`, `android:inputType="number"`, `android:gravity="center"`.
- "Gửi lại mã sau 00:45", nút "Xác nhận mã", dòng ghi chú.
- Hai bước còn lại làm sau, mỗi bước một layout riêng: `fragment_a20_nhap_email.xml`, `fragment_a20_mat_khau_moi.xml`.

## Đăng ký màn hình và commit

Sau mỗi màn hình, thêm một dòng vào `ui/auth/ManHinhA.kt`:

```kotlin
val manHinhA: List<ManHinh> = listOf(
    ManHinh("A06", "Đăng nhập") { DangNhapFragment() },
    ManHinh("A07", "Đăng ký") { DangKyFragment() },
)
```

Commit từng màn hình, ví dụ cho A06:

```bash
git add Source/TourApp/app/src/main/res/layout/fragment_a06_dang_nhap.xml
git add Source/TourApp/app/src/main/res/drawable/bg_a06_logo.xml
git add Source/TourApp/app/src/main/res/drawable/ic_fingerprint.xml
git add Source/TourApp/app/src/main/res/values/strings_taikhoan.xml
git add Source/TourApp/app/src/main/java/vn/edu/umt/tour/ui/auth/
git commit -m "[+] Layout A06 Đăng nhập"
git push -u origin feature/layout-tai-khoan
```

Các commit tiếp theo: `[+] Layout A07 Đăng ký`, `[+] Layout A00 Khởi động`, `[+] Layout A10 Tài khoản`, `[+] Layout A19 Cài đặt`, `[+] Layout A20 Quên mật khẩu`.

## Việc làm sau, không thuộc giai đoạn này

Khung điều hướng (`SplashActivity`, `AuthActivity`, `KhachActivity`, `QuanTriActivity`), lưu token, gọi API đăng nhập và đăng ký, chụp ảnh đại diện, mã OTP, mở khóa vân tay.
