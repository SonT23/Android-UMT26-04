# Hướng dẫn Git cho nhóm

Thầy chấm đóng góp dựa trên **lịch sử commit**: phần ai làm thì **người đó tự commit và tự push bằng tài khoản GitHub của mình**. Commit không mang tên mình thì không chứng minh được mình đã làm, mà ai không tự lập trình chức năng thì không qua môn. Theo template chấm đồ án, **không dùng git để lưu mã nguồn thì đồ án bị 0 điểm**.

- Repo: https://github.com/SonT23/Android-UMT26-04
- Nhánh chính `main` **không ai push thẳng được**. Mọi thay đổi đi qua pull request (PR).

Người mới làm lần lượt mục 1 → 2, sau đó mỗi chức năng làm theo mục 3. Gặp lỗi xem mục 6.

## 1. Cài đặt (làm một lần)

### 1.1 Tài khoản GitHub và email

1. Tạo tài khoản tại https://github.com (nếu chưa có), gửi **username** cho Sơn để được mời vào repo.
2. GitHub → **Settings → Emails**: thêm email bạn sẽ dùng để commit, bấm link xác minh trong hộp thư. Nên dùng email trường `...@st.umt.edu.vn` để thầy nhận ra.
3. Cùng trang đó: nếu ô **Block command line pushes that expose my email** đang bật thì push sẽ bị từ chối (lỗi `GH007`). Tắt ô đó, hoặc dùng địa chỉ dạng `12345678+username@users.noreply.github.com` hiện trên trang làm email commit.

> Repo đang để public nên ai cũng xem được email trong commit.

### 1.2 Cài Git

- **Windows:** tải Git for Windows tại https://git-scm.com, cài mặc định. Các lệnh bên dưới gõ trong **Git Bash**.
- **macOS:** `xcode-select --install`
- **Ubuntu:** `sudo apt install git`

Kiểm tra: `git --version`

### 1.3 Khai báo tên và email

```bash
git config --global user.name  "Họ Tên Đầy Đủ"     # vd. "Trần Quang Sơn"
git config --global user.email "email-ở-bước-1.1"
git config --global pull.rebase false
```

- **Tên:** họ tên đầy đủ có dấu, để thầy khớp với danh sách lớp.
- **Email:** phải **trùng** email đã xác minh ở 1.1. Sai thì commit vẫn lên được nhưng không gắn vào tài khoản của bạn (hiện tên trơn, avatar xám, không được tính đóng góp).
- Dòng cuối để `git pull` không hỏi cách gộp code.

### 1.4 Tạo SSH key và thêm vào GitHub

```bash
ssh-keygen -t ed25519 -C "email-ở-bước-1.1"    # Enter 3 lần để dùng mặc định
cat ~/.ssh/id_ed25519.pub                       # copy cả dòng bắt đầu bằng ssh-ed25519
```

Đã có file `~/.ssh/id_ed25519.pub` thì bỏ qua lệnh đầu.

GitHub → **Settings → SSH and GPG keys → New SSH key** → dán vào ô *Key* → **Add SSH key**. Kiểm tra:

```bash
ssh -T git@github.com
```

Lần đầu hỏi thì gõ `yes`. Thấy `Hi <username>! You've successfully authenticated...` là xong.

### 1.5 Nhận lời mời và clone

1. Sau khi Sơn mời, vào https://github.com/SonT23/Android-UMT26-04/invitations → **Accept invitation**. Chưa nhận thì không push được.
2. Clone về máy mình:

   ```bash
   git clone git@github.com:SonT23/Android-UMT26-04.git
   cd Android-UMT26-04
   ```

**Không** copy thư mục hoặc file zip project từ máy người khác.

## 2. Kiểm tra cài đặt bằng commit thử

Mỗi người làm một commit thử: tạo file thông tin của mình rồi đưa lên qua pull request. Vừa tập quy trình, vừa xác nhận commit mang đúng tên. Script `kiem-tra-git.sh` trong repo tự kiểm tra từng bước.

### 2.1 Chạy script lần 1 (ngay sau khi clone)

```bash
cd Android-UMT26-04
bash kiem-tra-git.sh
```

Mục 1–3 không được có `[LỖI]`. Mục 4 báo đang ở `main` là bình thường. Có `[LỖI]` hoặc `[!]` thì làm theo dòng `->` ngay bên dưới rồi chạy lại.

### 2.2 Tạo commit thử

Mỗi người dùng đúng dòng của mình:

| Thành viên | Branch | File cần tạo | Commit message |
|---|---|---|---|
| Hoàng | `docs/thanh-vien-hoang` | `Docs/ThanhVien/hoang.md` | `[+] Thông tin thành viên Hoàng` |
| Sơn | `docs/thanh-vien-son` | `Docs/ThanhVien/son.md` | `[+] Thông tin thành viên Sơn` |
| Thịnh | `docs/thanh-vien-thinh` | `Docs/ThanhVien/thinh.md` | `[+] Thông tin thành viên Thịnh` |
| Phúc | `docs/thanh-vien-phuc` | `Docs/ThanhVien/phuc.md` | `[+] Thông tin thành viên Phúc` |
| Trúc Đào | `docs/thanh-vien-truc-dao` | `Docs/ThanhVien/truc-dao.md` | `[+] Thông tin thành viên Trúc Đào` |

Ví dụ của Hoàng (người khác thay theo dòng của mình trong bảng):

```bash
git switch main
git pull
git switch -c docs/thanh-vien-hoang
```

Tạo file `Docs/ThanhVien/hoang.md` bằng trình soạn thảo bất kỳ (Android Studio, VS Code, Notepad), chép mẫu trong [Docs/ThanhVien/README.md](Docs/ThanhVien/README.md) rồi điền thông tin của mình. Lưu lại, rồi:

```bash
git add Docs/ThanhVien/hoang.md
git commit -m "[+] Thông tin thành viên Hoàng"
git push -u origin docs/thanh-vien-hoang
```

### 2.3 Chạy script lần 2 (vẫn đứng trên branch vừa push)

```bash
bash kiem-tra-git.sh
```

Kết quả đúng: mục 4 có dòng `[OK] Commit ... gắn đúng vào tài khoản GitHub: <username của bạn>`, dòng cuối là `==> Hoạt động tốt`.

### 2.4 Mở PR và merge

Mở PR như mục 3.3, nhờ một bạn bất kỳ duyệt rồi merge. Xong khi đủ cả 3 điều:

- [ ] Script lần 2 báo `==> Hoạt động tốt`
- [ ] PR đã được merge
- [ ] Trang https://github.com/SonT23/Android-UMT26-04/commits/main có commit của bạn kèm avatar và username của bạn

Xong thì nhắn vào nhóm để Sơn đánh dấu.

## 3. Quy trình cho mỗi chức năng

### 3.1 Tạo branch từ `main` mới nhất

```bash
git switch main
git pull
git switch -c feature/<ten-chuc-nang>          # vd. feature/gio-hang
```

Tên branch viết chữ thường, không dấu, nối bằng `-`. Dùng `feature/` cho chức năng mới, `fix/` cho sửa lỗi, `docs/` cho tài liệu.

### 3.2 Commit

```bash
git status                                     # xem đã sửa những file nào
git add <file>...                              # hoặc git add . để thêm tất cả
git commit -m "[+] Màn hình giỏ hàng"
```

Tiền tố theo quy định của thầy (slide *A01 – Course Introduction*, mục *gitlab policy*): `[+]` thêm chức năng mới, `[-]` xóa một chức năng, `[m]` sửa một chức năng. Ví dụ:

- `[+] Đăng nhập bằng email`
- `[m] Sửa lỗi giỏ hàng không cập nhật tổng tiền`
- `[-] Xóa màn hình test`

Mỗi commit chỉ chứa **một** tính năng hoặc thay đổi, đừng gộp nhiều chức năng vào một commit.

### 3.3 Push và mở pull request

```bash
git push -u origin feature/<ten-chuc-nang>     # các lần sau trên branch này chỉ cần: git push
```

Lên trang repo → bấm **Compare & pull request** (hoặc tab **Pull requests → New pull request**, chọn `base: main` ← `compare: feature/<ten-chuc-nang>`).

- **Tiêu đề:** giống commit, vd. `[+] Màn hình giỏ hàng`.
- **Mô tả:** làm gì, thử thế nào. Chức năng có giao diện thì kèm ảnh chụp màn hình.
- Cột bên phải → **Reviewers**: chọn người review của tuần.
- Bấm **Create pull request**.

Bị yêu cầu sửa thì sửa tiếp trên branch đó → commit → `git push`. PR tự cập nhật, không cần mở PR mới.

### 3.4 Review (người review của tuần)

- Tab **Files changed**: đọc code, bấm dấu `+` cạnh dòng code để góp ý.
- Muốn chạy thử: `git fetch` → `git switch feature/<ten-chuc-nang>` → Run trong Android Studio.
- **Review changes** → chọn *Approve* (đồng ý) hoặc *Request changes* (cần sửa) → **Submit review**.

### 3.5 Merge (người gộp code của tuần)

Khi PR đã có ít nhất 1 Approve: **Merge pull request → Confirm merge**. Branch tự xóa sau khi merge.

Merge commit mang tên người bấm, nhưng **các commit bên trong vẫn giữ tên người viết**. Người gộp code không lấy công của ai, và GitHub không tính merge commit vào thống kê đóng góp.

### 3.6 Sau khi PR được merge

```bash
git switch main
git pull
git branch -d feature/<ten-chuc-nang>
```

### 3.7 Khi `main` thay đổi trong lúc bạn đang làm

PR báo *This branch is out-of-date with the base branch* → bấm **Update branch**.

PR báo **conflict** → gộp `main` vào branch của bạn trên máy:

```bash
git switch feature/<ten-chuc-nang>
git fetch origin
git merge origin/main
```

Mở các file bị conflict (Android Studio: **Git → Resolve Conflicts…**), chọn giữ phần nào, rồi:

```bash
git add <file-đã-sửa>
git commit                                     # giữ nguyên message mặc định
git push
```

Dùng `merge`, **không dùng `rebase`**. Rebase viết lại lịch sử, phải force push và dễ làm mất commit.

## 4. Quy tắc giữ đúng tên người làm

- **Không commit hay push hộ ai**, kể cả khi được gửi code qua Zalo/Drive. Ai viết thì người đó commit từ máy mình.
- Không commit trên máy người khác hoặc máy phòng lab. Nếu bắt buộc phải dùng, gõ `git config user.name` và `git config user.email` để kiểm tra **trước khi commit**.
- Hai người cùng code một chức năng: người commit thêm dòng `Co-authored-by` để GitHub hiện cả hai người:

  ```bash
  git commit -m "[+] Màn hình thanh toán" -m "Co-authored-by: Họ Tên <email-github-của-người-kia>"
  ```

- Việc không phải code (APK vào `Release/`, video vào `Videos/`, báo cáo vào `Docs/`) cũng do người làm tự commit, để có bằng chứng cho phần việc đó.
- Commit đều mỗi tuần, đừng dồn đến cuối kỳ. Thầy xem được ngày giờ của từng commit.
- Chỉ sửa file trong module của mình. File dùng chung (theme, navigation, database) cần sửa thì báo nhóm trước để tránh conflict.
- Không `git push --force`, không sửa lịch sử (`rebase`, `commit --amend`) sau khi đã push.
- Giữ nguyên `.gitignore` của Android Studio, không commit `local.properties`, `build/`, `.gradle/`.
- Repo đang public: **không commit mật khẩu hay API key**. Key đặt trong `local.properties` (file này không được commit).

## 5. Dùng Android Studio thay cho dòng lệnh

Android Studio dùng đúng tên và email đã khai báo ở 1.3.

| Việc | Trong Android Studio |
|---|---|
| Mở project | **File → Open** → `Android-UMT26-04/Source/<TenApp>` (không mở thư mục gốc của repo) |
| Tạo branch | Bấm tên branch ở góc trên bên trái → **New Branch…** |
| Lấy code mới (`git pull`) | **Git → Update Project…** (`Ctrl+T`) |
| Commit | **Git → Commit…** (`Ctrl+K`) → tick file → nhập message → **Commit** |
| Push | **Git → Push…** (`Ctrl+Shift+K`) |
| Xử lý conflict | **Git → Resolve Conflicts…** |
| Xem lịch sử | Cửa sổ **Git** (`Alt+9`) → tab **Log** |

macOS: thay `Ctrl` bằng `⌘`, `Alt+9` thành `⌘9`.

## 6. Lỗi thường gặp

Chạy `bash kiem-tra-git.sh` trước: script phát hiện phần lớn các lỗi dưới đây và chỉ cách sửa.

| Thông báo | Nguyên nhân và cách sửa |
|---|---|
| `git@github.com: Permission denied (publickey)` | Chưa thêm SSH key vào GitHub → làm lại 1.4 |
| `ERROR: Permission to SonT23/Android-UMT26-04.git denied to <username>` | Chưa nhận lời mời → 1.5 |
| `GH007: Your push would publish a private email address` | Xem 1.1 bước 3 |
| `GH013: Repository rule violations found for refs/heads/main` | Đang push thẳng lên `main` → làm theo 3.1–3.3 |
| `Your local changes to the following files would be overwritten` | Còn thay đổi chưa commit → commit trước, hoặc `git stash` rồi làm xong thì `git stash pop` |
| Commit trên GitHub có avatar xám, không có username | Email commit chưa có trong tài khoản GitHub → 1.1 và 1.3 |

**Lỡ commit trên `main`** (push sẽ bị chặn), chuyển commit sang branch mới:

```bash
git status                                     # phải sạch, nghĩa là đã commit hết
git branch feature/<ten-chuc-nang>             # giữ commit ở branch mới
git reset --hard origin/main                   # đưa main về giống GitHub
git switch feature/<ten-chuc-nang>
git push -u origin feature/<ten-chuc-nang>
```

**Lỡ commit sai tên hoặc email** (chưa push): sửa lại theo 1.3 rồi chạy `git commit --amend --reset-author --no-edit`.

## Tóm tắt hằng ngày

```bash
git switch main && git pull                    # 1. lấy code mới
git switch -c feature/<ten-chuc-nang>          # 2. tạo branch
git add . && git commit -m "[+] ..."           # 3. commit
git push -u origin feature/<ten-chuc-nang>     # 4. push → mở PR → review → merge
```
