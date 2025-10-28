package engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseManager implements MouseListener, MouseMotionListener {

    private int currentStatus;
    private boolean leftPressed, rightPressed;
    private boolean leftJustPressed, rightJustPressed;
    private boolean leftCantPress, rightCantPress;
    private boolean leftWasPressed, rightWasPressed; // Trạng thái của frame trước
    private int mouseX, mouseY;


    public MouseManager() {
    }


    public void update() {
        // Cập nhật trạng thái "vừa nhấn" dựa trên trạng thái hiện tại và trạng thái cũ
        leftJustPressed = leftPressed && !leftWasPressed;
        rightJustPressed = rightPressed && !rightWasPressed;
        
        // Lưu trạng thái hiện tại để sử dụng trong frame tiếp theo
        leftWasPressed = leftPressed;
        rightWasPressed = rightPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isLeftJustPressed() {
        return leftJustPressed;
    }

    public boolean isLeftCantPress() {
        return leftCantPress;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        currentStatus = MouseEvent.MOUSE_PRESSED;
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentStatus = MouseEvent.MOUSE_RELEASED;
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = false;
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }


    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void mouseDragged(MouseEvent e) {
        currentStatus = MouseEvent.MOUSE_DRAGGED;
        mouseX = e.getX();
        mouseY = e.getY();
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        currentStatus = MouseEvent.MOUSE_MOVED;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isRightJustPressed() {
        return rightJustPressed;
    }

    public boolean isRightCantPress() {
        return rightCantPress;
    }


}