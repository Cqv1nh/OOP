package engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseManager implements MouseListener, MouseMotionListener {


    private boolean leftPressed, rightPressed;

    private boolean leftJustPressed, rightJustPressed;

    private boolean leftCantPress, rightCantPress;

    private int mouseX, mouseY;


    public MouseManager() {
    }


    public void update() {

        if (leftCantPress && !leftPressed) {
            leftCantPress = false;
        } else if (leftJustPressed) {
            leftCantPress = true;
            leftJustPressed = false;
        }
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
        update();
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;

            if (!leftCantPress) {
                leftJustPressed = true;
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
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
        mouseX = e.getX();
        mouseY = e.getY();
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
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