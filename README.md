# Game PVP - Trò chơi đối kháng 2 người chơi

## Tổng quan
Đây là một dự án game đối kháng 2 người chơi được phát triển bằng Java. Người chơi có thể điều khiển nhân vật, di chuyển và bắn đạn để đấu với nhau trong một môi trường thời gian thực.

## Tính năng
- Chơi 2 người trên cùng một bàn phím
- Di chuyển mượt mà từ 4 hướng
- Hệ thống bắn đạn theo hướng nhìn
- Có thể khóa hướng nhìn để di chuyển và bắn theo các hướng khác nhau
- Hiển thị chỉ báo hướng nhìn và nhận diện người chơi
- Phát hiện va chạm đạn với người chơi

## Hướng dẫn chơi
### Người chơi 1 (Màu xanh):
- **W**: Di chuyển lên
- **S**: Di chuyển xuống
- **A**: Di chuyển trái
- **D**: Di chuyển phải
- **Space**: Bắn đạn
- **Shift**: Khóa hướng nhìn (giữ để di chuyển mà không thay đổi hướng bắn)

### Người chơi 2 (Màu đỏ):
- **↑**: Di chuyển lên
- **↓**: Di chuyển xuống
- **←**: Di chuyển trái
- **→**: Di chuyển phải
- **Numpad 0**: Bắn đạn
- **Ctrl**: Khóa hướng nhìn

## Chi tiết kỹ thuật
- Được xây dựng hoàn toàn bằng Java
- Sử dụng Java Swing cho đồ họa
- Thiết kế hướng đối tượng
- Vòng lặp game với tốc độ khung hình có thể điều chỉnh
- Hệ thống xử lý đầu vào đa phím

## Cấu trúc dự án
```
/d:/GamePVP/
├── src/
│   ├── core/               # Các thành phần cốt lõi của game
│   │   ├── Game.java       # Quản lý vòng lặp game chính
│   │   ├── GamePanel.java  # Vẽ và xử lý logic game
│   │   └── GameWindow.java # Quản lý cửa sổ hiển thị
│   ├── entities/           # Các đối tượng trong game
│   │   ├── Player.java     # Người chơi
│   │   └── Attack.java     # Đạn bắn
│   ├── inputs/             # Xử lý đầu vào
│   │   └── KeyBoardsHandle.java # Xử lý phím bấm
│   └── Main.java           # Điểm khởi đầu chương trình
└── README.md               # Tài liệu hướng dẫn
```

## Cách cài đặt và chạy
1. Đảm bảo máy tính đã cài đặt Java JDK (khuyên dùng JDK 8 trở lên)
2. Tải xuống hoặc clone dự án
3. Mở terminal/command prompt trong thư mục dự án
4. Biên dịch toàn bộ mã nguồn:
   ```
   javac -d bin src/*.java src/*/*.java
   ```
5. Chạy game:
   ```
   java -cp bin Main
   ```

## Mẹo và chiến thuật
- Sử dụng phím Shift/Ctrl để giữ hướng bắn trong khi di chuyển
- Di chuyển liên tục để tránh đạn của đối phương
- Bạn có thể vừa di chuyển vừa bắn cùng một lúc

## Trạng thái phát triển
Dự án đang trong giai đoạn phát triển tích cực. Các tính năng và cải tiến đang được thêm vào thường xuyên.

## Những tính năng sẽ phát triển trong tương lai
- Hệ thống máu và hiển thị thanh máu
- Hiệu ứng hình ảnh và âm thanh
- Các loại vũ khí và đạn khác nhau
- Chế độ chơi với máy (AI)
- Các bản đồ và chướng ngại vật