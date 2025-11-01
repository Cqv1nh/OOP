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

    // Su dung constructor nay cho gon
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

    // Button khong the tu check xem la chuot da nhan hay chua
    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width &&
               mouseY >= this.y && mouseY <= this.y + this.height;
    }

    // Setter mới để cập nhật trạng thái hover từ bên ngoài (MenuState, v.v.)
    public void setHoveringState(boolean isHoveringState) {
        this.isHoveringState = isHoveringState;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getButtonNormal() {
        return buttonNormal;
    }

    public void setButtonNormal(BufferedImage buttonNormal) {
        this.buttonNormal = buttonNormal;
    }

    public BufferedImage getButtonHover() {
        return buttonHover;
    }

    public void setButtonHover(BufferedImage buttonHover) {
        this.buttonHover = buttonHover;
    }

    public String getFunction() {
        return function;
    }
}
