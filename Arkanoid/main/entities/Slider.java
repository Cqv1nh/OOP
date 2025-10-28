package entities;

import engine.MouseManager;

import java.awt.*;
import java.awt.event.MouseEvent;
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


    public void render(Graphics2D g) {
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


        int textBaseline = y + (height / 2) + (g.getFontMetrics().getAscent() / 2) - 2;
        g.drawString(valueText, x + width + 20, textBaseline);


        g.drawString(name, x - 100 , textBaseline);
    }

    public void handleMouseEvent(MouseManager mouseManager) {
        int mouseX = mouseManager.getMouseX();
        int mouseY = mouseManager.getMouseY();

        switch (mouseManager.getCurrentStatus()) {
            case MouseEvent.MOUSE_PRESSED:
                if (handleBounds.contains(mouseX, mouseY)) {
                    isDragging = true;
                }
                break;
            case MouseEvent.MOUSE_RELEASED:
                isDragging = false;
                break;
            case MouseEvent.MOUSE_DRAGGED:
                if (isDragging) {
                    float newValue = minValue + (mouseX - x) * valueStep;
                    setValue(newValue); // setValue will clamp it
                }
                break;
            case MouseEvent.MOUSE_MOVED:
                isHovered = handleBounds.contains(mouseX, mouseY);
                break;
        }
    }

    public void isHoveringButton(int mouseX, int mouseY) {
        if (handleBounds.contains(mouseX, mouseY)) {

        }
    }

    public float getValue() {
        return value;
    }

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

    private void updateHandleBounds() {
        float percentage = (value - minValue) / (maxValue - minValue);

        int handleCenterX = x + (int) (percentage * width);

        int handleCenterY = y + (height / 2);

        int handleX = handleCenterX - (handleWidth / 2);
        int handleY = handleCenterY - (handleHeight / 2);

        handleBounds.setBounds(handleX, handleY, handleWidth, handleHeight);
    }

    public void setTrackImage(BufferedImage trackImage) {
        this.trackImage = trackImage;
    }

    public void setHandleImage(BufferedImage handleImage) {
        this.handleImage = handleImage;
    }

    public void setTrackFillImage(BufferedImage trackFillImage) {
        this.trackFillImage = trackFillImage;
    }

    public void setValueTextColor(Color valueTextColor) {
        this.valueTextColor = valueTextColor;
    }

}
