# Đồ án Android — BIT202V1 Lập trình ứng dụng di động

UMT · Học kỳ 1, 2026–2027

**Đề tài:** TourApp, ứng dụng Android đặt tour du lịch trong nước. Project ở [Source/TourApp/](Source/TourApp/).
**Thành viên:** Hoàng · Sơn · Thịnh · Phúc · Trúc Đào (chi tiết trong [Docs/ThanhVien/](Docs/ThanhVien/))

## Cấu trúc thư mục

| Thư mục | Nội dung |
|---|---|
| `Source/` | Project Android Studio (Kotlin, giao diện XML) |
| `Release/` | File APK bản nộp |
| `Docs/` | Proposal, báo cáo, sơ đồ database, thông tin thành viên |
| `Videos/` | Video demo. GitHub không nhận file trên 100 MB, video lớn thì để link Google Drive/YouTube trong một file `.md` ở đây |
| `References/` | Tài liệu tham khảo, nguồn ảnh và icon đã dùng |

## Đang làm gì

Giai đoạn dựng giao diện: mỗi người làm các màn hình của module mình theo thiết kế trong [Docs/Figma/](Docs/Figma/). Hướng dẫn cho từng người ở **[Docs/HuongDanUI/](Docs/HuongDanUI/)**.

## Làm việc với Git

Đọc **[CONTRIBUTING.md](CONTRIBUTING.md)** trước khi code. Tóm tắt:

- Phần ai làm thì người đó **tự commit, tự push bằng tài khoản của mình**. Lịch sử commit là bằng chứng đóng góp.
- Mỗi chức năng một branch → pull request → review → merge. Không push thẳng lên `main`.
- Commit có tiền tố `[+]` thêm, `[-]` xóa, `[m]` sửa.
- Kiểm tra máy mình đã cài đúng chưa: `bash kiem-tra-git.sh`.
