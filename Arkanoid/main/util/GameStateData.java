package util;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashSet; // Dùng HashSet để lưu trữ hiệu quả và kiểm tra nhanh
// Lớp này sẽ chứa thông tin cần lưu. Chúng ta sẽ dùng Serializable để Java có thể dễ dàng ghi/đọc nó vào file.

public class GameStateData implements Serializable {
    // Cần có serialVersionUID để đảm bảo tương thích khi đọc file lưu cũ (nếu lớp thay đổi)
    private static final long serialVersionUID = 1L;
    public int currentLevel;
    public int score;
    public int lives;
    // Ví dụ: vị trí bóng, trạng thái power-up, ...
    public HashSet<Point> remainingBrickIndices;

    /**
     * Constructor 4 tham số.
     * 
     * @param currentLevel level hiện tại.
     * @param score điểm.
     * @param lives số mạng.
     * @param remainingBrickIndices vị trí gạch chưa được phá.
     */
    public GameStateData(int currentLevel, int score, int lives, HashSet<Point> remainingBrickIndices) {
        this.currentLevel = currentLevel;
        this.score = score;
        this.lives = lives;
        this.remainingBrickIndices = remainingBrickIndices;
    }
    // Lý do dùng HashSet<Point>: Nó giúp dễ dàng kiểm tra xem một vị trí 
    // (hàng, cột) có nằm trong danh sách gạch còn lại hay không khi tạo lại level.
    // Point (từ java.awt.Point) là một lớp đơn giản để lưu cặp (x, y) (chúng ta sẽ dùng nó để lưu (hàng, cột)).
}
