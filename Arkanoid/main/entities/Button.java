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
    private boolean isHoveringState = false;

    private BufferedImage buttonHover;
    private BufferedImage buttonNormal;

    /**
     * Constructor 8 tham số.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text
     * @param color
     * @param textColor
     * @param font
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
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text
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
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text
     * @param function
     * @param buttonNormal
     * @param buttonHover
     */
    public Button(int x, int y, int width, int height, String text, String function,
                  BufferedImage buttonNormal,
                  BufferedImage buttonHover) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.textColor = Color.decode("#FFFAFA");
        this.color = Color.decode("#FAFAFF");
        this.font = new Font("Arial", Font.BOLD, 16);
        this.function = function;
        this.buttonNormal = buttonNormal;
        this.buttonHover = buttonHover;
    }

    /**
     * Method vẽ nút bấm ra màn hình.
     *
     * @param g
     * @param mouseX
     * @param mouseY
     */
    public void drawFlag(Graphics2D g, int mouseX, int mouseY) {
        if (!isHovering(mouseX, mouseY)) {
            g.drawImage(buttonNormal, x, y, width, height, null);
        }
        else {
            Color shadowColor = new Color(0, 0, 0, 100);
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
     * @param g2d
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

        // // Center the text in the button
        FontMetrics metrics = g2d.getFontMetrics(this.font);
        int textX = this.x + (this.width - metrics.stringWidth(this.text)) / 2;
        int textY = this.y + ((this.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.drawString(this.text, textX, textY);
    }

    /**
     * Kiểm tra con trỏ chuột có nằm trong vùng Button không.
     *
     * @param mouseX
     * @param mouseY
     * @return
     */
    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width &&
               mouseY >= this.y && mouseY <= this.y + this.height;
    }

    /**
     * Setter để cập nhật trạng thái hover từ bên ngoài (MenuState, v.v.).
     *
     * @param isHoveringState
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
}
