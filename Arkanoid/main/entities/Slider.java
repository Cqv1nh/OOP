package entities;

import engine.MouseManager;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Slider {
    private int x;
    private int y;
    private int width;
    private int height;
    private int handleWidth;
    private int handleHeight;
    private float minValue;
    private float maxValue;
    private float value;
    private float valueStep;
    private boolean isDragging;
    private boolean isHovered;
    private Rectangle handleBounds;
    private BufferedImage trackImage;
    private BufferedImage handleImage;
    private BufferedImage trackFillImage;
    private String name;
    private Color valueTextColor;

    Font defaultFont = new Font("Arial", Font.PLAIN, 13);

    /**
     * Constructor 11 tham số.
     *
     * @param x tọa độ x trái, trên.
     * @param y tọa độ y trái, trên.
     * @param width rộng
     * @param height cao.
     * @param minValue GTNN của thanh.
     * @param maxValue GTLN của thanh.
     * @param initialValue giá trị khởi tạo ban đầu.
     * @param trackImage Ảnh nền của đường trượt.
     * @param handleImage Ảnh của núm kéo.
     * @param trackFillImage Ảnh phần đường trượt đã được kéo.
     * @param silderName Tên hoặc nhãn hiển thị của thanh trượt.
     */
    public Slider(int x, int y, int width, int height, float minValue, float maxValue, float initialValue,
                  BufferedImage trackImage, BufferedImage handleImage, BufferedImage trackFillImage, String silderName) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.minValue = minValue;
        this.maxValue = maxValue;

        this.trackImage = trackImage;
        this.handleImage = handleImage;
        this.trackFillImage = trackFillImage;

        this.handleWidth = handleImage.getWidth();
        this.handleHeight = handleImage.getHeight();

        this.valueTextColor = Color.WHITE;
        this.isDragging = false;
        this.handleBounds = new Rectangle();

        this.name = silderName;
        this.valueStep = (maxValue - minValue) / (float) width;
        setValue(initialValue);
    }

    /**
     * Vẽ thanh trượt, phần đã kéo, núm kéo và giá trị hiện tại lên màn hình.
     *
     * @param g đối tượng đồ họa dùng để vẽ.
     */
    public void render(Graphics2D g) {
        Font font = defaultFont;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int handleX = handleBounds.x;
        int handleY = handleBounds.y;

        if (trackFillImage != null) {
            int fillWidth = (handleX + (handleWidth / 2)) - x;
            if (fillWidth < 0) fillWidth = 0;
            g.drawImage(trackFillImage, x, y, fillWidth, height, null);
        }

        if (trackImage != null) {
            g.drawImage(trackImage, x, y, width, height, null);
        }

        if (handleImage != null) {
            g.drawImage(handleImage, handleX, handleY + 7, handleWidth, handleHeight, null);
        }

        g.setColor(valueTextColor);
        String valueText = String.format("%.1f", value);

        g.setFont(font);
        int textBaseline = y + (height / 2) + (g.getFontMetrics().getAscent() / 2) - 2;
        g.drawString(valueText, x + width + 20, textBaseline);

        FontMetrics metrics = g.getFontMetrics(font);

        while (metrics.stringWidth(this.name) > 95) {
            font = new Font("Arial", Font.PLAIN, font.getSize() - 1);
            metrics = g.getFontMetrics(font);
        }
        g.setFont(font);
        g.drawString(name, x - 100 , textBaseline);
    }

    /**
     * Xử lý thao tác chuột để kéo và thay đổi giá trị của thanh trượt.
     *
     * @param mouseManager quản lý chuột.
     */
    public void handleMouseEvent(MouseManager mouseManager) {
        int mouseX = mouseManager.getMouseX();
        int mouseY = mouseManager.getMouseY();

        // Kiểm tra xem chuột có đang nằm trên núm kéo (handle) không?
        isHovered = handleBounds.contains(mouseX,mouseY);
        // Cập nhật trạng thái isHovered
        
        // Xử lý bắt đầu kéo (Nhấn chuột trái lần đầu trên núm kéo)
        if (mouseManager.isLeftJustPressed() && isHovered) {
            isDragging = true;
            // System.out.println("Start Dragging"); // Debug (Tùy chọn)
        } 
        
        // Xử lý kết thúc kéo (Thả chuột trái ra ở bất kỳ đâu)
        else if (!mouseManager.isLeftPressed()){ // Nếu nút trái KHÔNG còn được nhấn
            isDragging = false;
        }

        // Xử lý khi đang kéo (Nút trái đang được giữ và đang trong trạng thái kéo)
        else if (isDragging && mouseManager.isLeftPressed()) {
            // Giới hạn vị trí X của chuột trong phạm vi của thanh trượt (track)
            int clampedMouseX = Math.max(x, Math.min(mouseX, x + width));
            // Tính giá trị mới dựa trên vị trí tương đối của chuột trên thanh trượt
            float newValue = minValue + (clampedMouseX - x) * valueStep;
            // Cập nhật giá trị của slider (hàm setValue sẽ tự giới hạn trong min/max)
            setValue(newValue);
        }
    }

    /**
     * Getter cho giá trị hiện tại.
     *
     * @return value.
     */
    public float getValue() {
        return value;
    }

    /**
     * Setter cho giá trị hiện tại.
     *
     * @param value giá trị hiện tại.
     */
    public void setValue(float value) {
        if (value < minValue) {
            this.value = minValue;
        } else if (value > maxValue) {
            this.value = maxValue;
        } else {
            this.value = value;
        }
        updateHandleBounds();
    }

    /**
     * Tính toán lại vị trí núm kéo dựa trên giá trị hiện tại.
     */
    private void updateHandleBounds() {
        float percentage = (value - minValue) / (maxValue - minValue);

        int handleCenterX = x + (int) (percentage * width);

        int handleCenterY = y + (height / 2);

        int handleX = handleCenterX - (handleWidth / 2);
        int handleY = handleCenterY - (handleHeight / 2);

        handleBounds.setBounds(handleX, handleY, handleWidth, handleHeight);
    }

    /**
     * Setter cho phần track của thanh trượt.
     *
     * @param trackImage hình ảnh track.
     */
    public void setTrackImage(BufferedImage trackImage) {
        this.trackImage = trackImage;
    }

    /**
     * Setter cho núm kéo (handle).
     *
     * @param handleImage hình ảnh handle.
     */
    public void setHandleImage(BufferedImage handleImage) {
        this.handleImage = handleImage;
    }

    /**
     * Setter cho phần track đã được kéo (fill).
     *
     * @param trackFillImage hình ảnh trackfill.
     */
    public void setTrackFillImage(BufferedImage trackFillImage) {
        this.trackFillImage = trackFillImage;
    }

    /**
     * Setter cho màu chữ hiển thị giá trị của thanh trượt.
     *
     * @param valueTextColor giá trị màu chữ.
     */
    public void setValueTextColor(Color valueTextColor) {
        this.valueTextColor = valueTextColor;
    }

    /**
     * Lấy tên (nhãn) của thanh trượt.
     *
     * @return tên.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter cho tên thanh trượt.
     *
     * @param name tên mới.
     */
    public void setName(String name) {
        this.name = name;
    }
}
