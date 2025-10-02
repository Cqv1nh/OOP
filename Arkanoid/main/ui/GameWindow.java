import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public GameWindow() {
        // Đặt tiêu đề cho cửa sổ
        setTitle();

        // Kích thước cửa sổ
        setSize(800, 600);

        // Thoát khi bấm nút X
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Không cho resize
        setResizable(false);

        // Tạo panel chính (màn hình game)
        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Vẽ nền màu đen
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Thêm panel vào cửa sổ
        add(gamePanel);

        // Hiển thị cửa sổ
        setVisible(true);
    }

    public static void main(String[] args) {
        // Tạo cửa sổ trên luồng giao diện
        SwingUtilities.invokeLater(() -> new GameWindow());
    }
}
