package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Button {

    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private Color color;
    private Color textColor;
    private Font font;
    private Rectangle bounds;

    private BufferedImage buttonImage;
    private BufferedImage buttonWhenHover;

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
        this.bounds = new Rectangle(x, y, width, height);
    }


    public Button(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.textColor = Color.decode("#FFFAFA");
        this.color = new Color(0, 149, 221);
        this.font = new Font("Arial", Font.BOLD, 16);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void drawImage(Graphics2D g, int mouseX, int mouseY) {
        if (isHovering(mouseX, mouseY)) {
            g.drawImage(buttonWhenHover, x, y, width, height, null);

        } else {
            g.drawImage(buttonImage, x, y, width, height, null);
        }
    }

    public void draw(Graphics2D g2d) {
        // Draw the button rectangle
        g2d.setColor(this.color);
        g2d.fillRect(this.x, this.y, this.width, this.height);

        // Draw the button text
        g2d.setColor(this.textColor);
        g2d.setFont(this.font);

        // Center the text in the button
        FontMetrics metrics = g2d.getFontMetrics(this.font);
        int textX = this.x + (this.width - metrics.stringWidth(this.text)) / 2;
        int textY = this.y + ((this.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.drawString(this.text, textX, textY);
    }


    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width &&
                mouseY >= this.y && mouseY <= this.y + this.height;
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

    public BufferedImage getButtonImage() {
        return buttonImage;
    }

    public void setButtonImage(BufferedImage buttonImage) {
        this.buttonImage = buttonImage;
    }

    public BufferedImage getButtonWhenHover() {
        return buttonWhenHover;
    }

    public void setButtonWhenHover(BufferedImage buttonWhenHover) {
        this.buttonWhenHover = buttonWhenHover;
    }
}
