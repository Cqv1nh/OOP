package util;

import java.awt.FontMetrics;
import java.awt.Graphics;

// Lớp tiện ích để chứa các hàm vẽ dùng chung
public class RenderUtil {

    /**
     * Vẽ một chuỗi ký tự căn giữa màn hình.
     * Phương thức này được chuyển từ GamePanel để dùng chung.
     */
    public static void drawCenteredString(String text, int panelWidth,
            int panelHeight, Graphics g, int xOffset, int yOffset) {

        FontMetrics fm = g.getFontMetrics();
        int x = (panelWidth - fm.stringWidth(text)) / 2;
        int y = (fm.getAscent() + (panelHeight - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(text, x + xOffset, y + yOffset);
    }

}