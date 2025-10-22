package util;

import java.io.Serializable;
// Lớp này sẽ chứa thông tin cần lưu. Chúng ta sẽ dùng Serializable để Java có thể dễ dàng ghi/đọc nó vào file.

public class GameStateData implements Serializable {
    // Cần có serialVersionUID để đảm bảo tương thích khi đọc file lưu cũ (nếu lớp thay đổi)
    private static final long serialVersionUID = 1L;

    public int currentLevel;
    public int score;
    public int lives;
    // Co the them
    // Ví dụ: vị trí bóng, trạng thái power-up, ...
    
    // Constructor
    public GameStateData(int currentLevel, int score, int lives) {
        this.currentLevel = currentLevel;
        this.score = score;
        this.lives = lives;
    }

}
