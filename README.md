# OOP
Lập trình hướng đối tượng

Công việc tuần 5 (6/10 - 12/10)

Thịnh: 
    - Tạo các lớp cơ sở: GameObject, MovableObject.
    - Tạo lớp Paddle đầu tiên kế thừa từ MovableObject.
    - Triển khai 4 loại gạch: NormalBrick, StrongBrick, ExplosiveBrick, UnbreakableBrick, thêm lớp BrickType định nghĩa các loại gạch.
    - Triển khai hệ thống lives và logic đặt lại bóng.
    
Trọng:
    - Viết code sơ khai cho class Ball, PowerUp tổng quát và 4 PowerUp: + 1 bóng, tăng tốc độ, + 1 mạng, + độ dài thanh
    - Fix lỗi class Ball.
    - Áp dụng cơ chế thêm điểm khi phá hủy Brick.
    - Tìm hình ảnh về các PowerUp.
    - Fix 1 số lỗi tồn đọng trong các code sẵn có.
Vinh:
    - Tạo lớp LevelState là lớp quản lý trạng thái màn chơi.
    
Công việc tuần 6 (13/10- 19/10)

Thịnh
    - Triển khai hệ thống theo dõi và hiển thị điểm số của game.
    - Sửa hàm logic di chuyển bóng trong Ball
    - Thay thế logic va chạm chồng lặp AABB không chính xác bằng thuật toán dựa trên khoảng cách hình tròn-hình chữ nhật.
    - Triển khai Kiến trúc V1: Tách khối GameLoop thực hiện đa chức năng thành các lớp nhỏ hơn: EntityManager, CollisionHandler, PowerUpManager, GameStateManager, mỗi lớp thực hiện 1 chức năng.
    - Quản lý dữ liệu gạch của từng level chơi.
    - Xây dựng thanh đồng hồ hiển thị thời gian PowerUp có hiệu lực.
    - Quản lý âm thanh nhạc nền và âm thanh va chạm.

Trọng:
    - Tìm thêm các hình ảnh về các vật thể: Paddle, Brick, Ball, PowerUp.
    - Tìm nhạc nền ở sảnh chờ và âm thanh va chạm ban đầu chung chung giữa bóng và vật thể khác.
    - Code thêm duration và paddle size cho các PowerUp.
    - Fix code 6 class liên quan gamewindow, entitymanager, multiballpowerup, collisionhandler, gamepanel, fastball để tác động lên nhiều bóng.
    - Fix lỗi tốc độ bóng không ổn định.
    - Fix 1 số lỗi tồn đọng trong các code sẵn có.

Vinh:
    - Tạo class GameStateManager là lớp quản lý trạng thái trò chơi, lớp GameState là lớp trừu tượng cho một trạng thái game.
    - Dựa vào GameState thêm các lớp LevelState2, MenuState, PauseState, TransitionState, GameOverState và VictoryState biễu diễn các trạng thái khác nhau của trò chơi. Thay thế chức năng lớp LevelState bằng lớp LevelState2.
    - Sửa đổi game loop chính trong class Game để sử dụng GameStateManager và GameState.
    - Tạo thêm class Button đại diện cho một nút nhấn.
    - Thêm class MouseManager và KeyBroadManager để xử bàn phím và chuột.

Công việc tuần 7 (20/10 - 26/10)

Thịnh:
    - Thiết kế hình ảnh và background dành riêng cho từng 5 màn chơi.
    - Tiếp nhận kiến trúc V2: GameStateManager từ Vinh, sửa code và xóa bỏ hoàn toàn kiến trúc V1: GameLoop, GameWindow.
    - Xây dựng hệ thống save/load game sử dụng 1 file.
    - Dọn dẹp thư viện hình ảnh: Xóa các hình ảnh không dùng đến.
    
Trọng:
    - Thêm hiệu ứng làm mờ khi phá hủy PowerUp.
    - Thay đổi hành vi va chạm để phù hợp hơn với animation làm mờ.
    - Thay đổi vòng lặp game với cập nhật phương thức render().
    - Fix 1 số lỗi tồn đọng trong các code sẵn có.
    
Vinh:
    - Tạo thêm class Slider tượng trưng cho thanh trượt.
    - Tạo thêm Class Settings để lưu cấu hình tùy chọn.
    - Sử dụng class Slider thêm chức năng điều khiển âm lượng tại SettingsState và các phương thức để lưu và tải cấu hình sử dụng file .json.
    - Sửa đổi các class liên quan để có thể sử dụng chức năng mới.

Công việc tuần 8 (27/10 - 2/11)

Thịnh: 
    - Sửa lỗi logic liên quan đến 2 PowerUp có thời gian hiệu lực.
    - Xây dựng UML hoàn chỉnh cho dự án.
    - Xây dựng hệ thống xếp hạng HighScore xếp hạng theo điểm số và thời gian.
    - Chuyển đổi cơ chế save 1 game thành save 3 game, người chơi có thể tùy ý chọn màn chơi đã chơi mong muốn.
    - Cải tiến giao diện các trạng thái game cho đẹp hơn.
    - Sửa lỗi điểm số HUD kkhông cập nhật tức thời.

Trọng:
    - Làm thêm animation liên quan đến gạch nổ Explosive.
    - Đa dạng thêm âm thanh va chạm giữa bóng - paddle, bóng - brick, và hiệu ứng khi ăn powerup.
    - Fix 1 số lỗi tồn đọng trong các code sẵn có.
    
Vinh: 
    - Tạo thư mục JUnit để test các chức năng game.
    - Thêm thư mực chữa các file .properties và ảnh cờ các quốc gia để sử dụng trong tùy chọn ngôn ngữ mới trong SettingsState.
    - Tạo các methods và chỉnh sửa trong SettingsState, GameStateManager và các GameState còn lại để chúng có thể hiển thị các ngôn ngữ khác nhau.
    
Công việc tuần 9 (3/11 - 5/11)

Thịnh: 
    - Đổi tên lớp và di chuyển các class về đúng thư mục cho hợp lý.
    
Trọng:
    - Cập nhật toàn bộ chú thích (Javadoc) cho tất cả các class của code game Arkanoid.
    - Fix 1 số lỗi tồn đọng trong các code sẵn có.
    - Dọn dẹp các phương thức và thuộc tính không sử dụng.
    
Vinh: 
    - Thêm tùy chọn gán nút di chuyển cho Paddle.

