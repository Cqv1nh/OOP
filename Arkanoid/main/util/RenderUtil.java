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

    /**
     * Vẽ một chuỗi ký tự căn giữa cột.
     *
     * @param g         Đối tượng Graphics.
     * @param text      Chuỗi cần vẽ.
     * @param colStartX Tọa độ X BẮT ĐẦU của cột.
     * @param colWidth  ĐỘ RỘNG của cột.
     * @param y         Tọa độ Y để vẽ.
     */
    public static void drawCenteredStringInColumn(Graphics g, String text, int colStartX, int colWidth, int y) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);        
        // Tính vị trí x để văn bản được căn giữa cột
        int x = colStartX + (colWidth - textWidth) / 2;        
        g.drawString(text, x, y);
    }
}