package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import util.AssetManager;

public class Button {

    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private String function;
    private Color color;
    private Color textColor;
    private Font font;
    Font defaultFont = new Font("Arial", Font.PLAIN, 16);
    private boolean isHoveringState = false;
    private BufferedImage buttonHover;
    private BufferedImage buttonNormal;

    /**
     * Constructor 8 tham số.
     *
     * @param x tọa độ x trên trái.
     * @param y tọa độ y trên trái.
     * @param width chiều rộng nút.
     * @param height chiều cao nút.
     * @param text chuỗi văn bản hiển thị trên nút.
     * @param color màu nền mặc định nút.
     * @param textColor màu văn bản.
     * @param font phông chữ.
     */
    public Button(int x, int y, int width, int height, String text,
                  Color color, Color textColor, Font font) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.color = color;
        this.textColor = textColor;
        this.font = font;
    }

    /**
     * Constructor 5 tham số.
     *
     * @param x tọa độ x trên trái.
     * @param y tọa độ y trên trái.
     * @param width chiều rộng nút.
     * @param height chiều cao nút.
     * @param text chuỗi văn bản hiển thị trên nút.
     */
    public Button(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.textColor = Color.decode("#FFFAFA");
        this.color = new Color(0, 149, 221);
        this.font = new Font("Arial", Font.BOLD, 16);
    }

    /**
     * Constructor 8 tham số.
     *
     * @param x tọa độ x trên trái.
     * @param y tọa độ y trên trái.
     * @param width chiều rộng nút.
     * @param height chiều cao nút.
     * @param text chuỗi văn bản hiển thị trên nút.
     * @param function Tên hàm hoặc lệnh mà nút này kích hoạt khi được nhấn.
     * @param buttonNormal Hình ảnh của nút khi ở trạng thái bình thường.
     * @param buttonHover Hình ảnh của nút khi chuột đang di chuyển qua.
     */
    public Button(int x, int y, int width, int height, String text, String function,
                  BufferedImage buttonNormal,
                  BufferedImage buttonHover) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.textColor = Color.decode("#FAFFFF");
        this.color = Color.decode("#FAFAFF");
        this.font = new Font("Arial", Font.PLAIN, 16);
        this.function = function;
        this.buttonNormal = buttonNormal;
        this.buttonHover = buttonHover;
    }

    /**
     * Method vẽ nút bấm ra màn hình.
     *
     * @param g đối tượng đồ họa java.
     * @param mouseX tọa độ x con trỏ chuột.
     * @param mouseY tọa độ y con trỏ chuột.
     */
    public void draw(Graphics2D g2d, int mouseX, int mouseY) {
        if (g2d.getFontMetrics(defaultFont).stringWidth(this.text) < this.width - 40) {
            this.font = defaultFont;
        }

        if (!isHovering(mouseX, mouseY) && !isHoveringState) {
            g2d.drawImage(buttonNormal, x, y, width,
                    height, null);
        } else {
            g2d.drawImage(buttonHover, x, y, width,
                    height, null);
        }
        g2d.setFont(this.font);

        FontMetrics metrics = g2d.getFontMetrics(this.font);

        while (metrics.stringWidth(this.text) > this.width - 40) {
            font = new Font("Arial", Font.PLAIN, font.getSize() - 1);
            metrics = g2d.getFontMetrics(this.font);
        }

        int textX = this.x + (this.width - metrics.stringWidth(this.text)) / 2;
        int textY = this.y + ((this.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.drawString(this.text, textX, textY);
    }

    /**
     * Method vẽ button có hiệu ứng nổi lên.
     * 
     * @param g Đối tượng đồ họa được dùng để vẽ nút.
     * @param mouseX Tọa độ X hiện tại của con trỏ chuột.
     * @param mouseY Tọa độ Y hiện tại của con trỏ chuột.
     */
    public void drawFlag(Graphics2D g, int mouseX, int mouseY) {
        if (!isHovering(mouseX, mouseY) && !isHoveringState) {
            g.drawImage(buttonNormal, x, y, width, height, null);
        }
        else {
            Color shadowColor = Color.decode("#3D3D3D");

            g.setColor(shadowColor);

            int shadowOffset = 4;
            g.fillRoundRect(x + shadowOffset, y + shadowOffset, width, height, 8, 8);

            int lift = 2;
            g.drawImage(buttonNormal, x, y - lift, width, height, null);
        }
    }

    /**
     * Method hiển thị trực quan của Button.
     *
     * @param g2d Đối tượng đồ họa được dùng để vẽ nút.
     */
    public void draw(Graphics2D g2d) {
        BufferedImage currentButtonImage = null;
        // Kiem tra nhan va khong nhan
        if (isHoveringState && AssetManager.buttonHover != null) {
            currentButtonImage = AssetManager.buttonHover;
        } else if (!isHoveringState && AssetManager.buttonNormal != null) {
            currentButtonImage = AssetManager.buttonNormal;
        }
        
        g2d.drawImage(currentButtonImage, this.x, this.y, this.width, this.height, null);

        // Ve chu tren nut , mau chu cung se thay doi theo nut an
        g2d.setColor(isHoveringState ? Color.YELLOW : this.textColor);
        g2d.setFont(this.font);

        // Center the text in the button
        FontMetrics metrics = g2d.getFontMetrics(this.font);
        int textX = this.x + (this.width - metrics.stringWidth(this.text)) / 2;
        int textY = this.y + ((this.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.drawString(this.text, textX, textY);
    }

    /**
     * Kiểm tra con trỏ chuột có nằm trong vùng Button không.
     *
     * @param mouseX Tọa độ X hiện tại của con trỏ chuột.
     * @param mouseY Tọa độ Y hiện tại của con trỏ chuột.
     * @return T or F.
     */
    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width &&
               mouseY >= this.y && mouseY <= this.y + this.height;
    }

    /**
     * Setter để cập nhật trạng thái hover từ bên ngoài (MenuState, v.v.).
     *
     * @param isHoveringState trạng thái hover.
     */
    public void setHoveringState(boolean isHoveringState) {
        this.isHoveringState = isHoveringState;
    }

    /**
     * Getter cho kí tự.
     *
     * @return kí tự.
     */
    public String getText() {
        return text;
    }

    /**
     * Setter cho kí tự.
     *
     * @param text kí tự.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter cho màu sắc.
     *
     * @return màu sắc.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter cho màu sắc.
     *
     * @param color màu sắc.
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Getter cho X.
     *
     * @return x.
     */
    public int getX() {
        return x;
    }

    /**
     * Getter cho Y.
     *
     * @return y.
     */
    public int getY() {
        return y;
    }

    /**
     * Getter cho độ rộng.
     *
     * @return rộng.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter cho độ cao.
     *
     * @return độ cao.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter cho nút bình thường.
     *
     * @return buttonnormal.
     */
    public BufferedImage getButtonNormal() {
        return buttonNormal;
    }

    /**
     * Setter cho nút bình thường.
     *
     * @param buttonNormal nút bình thường.
     */
    public void setButtonNormal(BufferedImage buttonNormal) {
        this.buttonNormal = buttonNormal;
    }

    /**
     * Getter cho nút khi người dùng di chuột vào.
     *
     * @return buttonhover.
     */
    public BufferedImage getButtonHover() {
        return buttonHover;
    }

    /**
     * Setter cho nút khi người dùng di chuột vào.
     *
     * @param buttonHover nút khi người dùng di chuột vào.
     */
    public void setButtonHover(BufferedImage buttonHover) {
        this.buttonHover = buttonHover;
    }

    /**
     * Getter cho function.
     *
     * @return chức năng.
     */
    public String getFunction() {
        return function;
    }

    /**
     * Getter cho chuột có nằm trên vùng nút/thanh trượt không ?
     * 
     * @return T or F.
     */
    public boolean isHoveringState() {
        return isHoveringState;
    }

    /**
     * Setter cho độ rộng.
     * 
     * @param width độ rộng.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Setter cho độ cao.
     * 
     * @param height độ cao.
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
