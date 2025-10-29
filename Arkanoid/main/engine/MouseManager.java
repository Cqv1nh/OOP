package engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseManager implements MouseListener, MouseMotionListener {

    private boolean leftPressed, rightPressed;
    private boolean leftJustPressed, rightJustPressed;
    private boolean leftWasPressed, rightWasPressed; // Trạng thái của frame trước
    private int mouseX, mouseY;


    public MouseManager() {
    }

    // leftJustPressed = leftPressed && !leftWasPressed;:
    // Điều kiện này chỉ đúng (true) khi:
    // leftPressed là true (nút đang được nhấn trong frame này).
    // !leftWasPressed là true (nghĩa là leftWasPressed là false - nút chưa được nhấn trong frame trước).
    // Ngay sau frame này, ở frame tiếp theo, leftWasPressed sẽ được cập nhật thành true (vì leftPressed vẫn đang là true nếu bạn giữ nút). Lúc đó, điều kiện !leftWasPressed sẽ trở thành false, làm cho leftJustPressed trở về false, kể cả khi bạn vẫn giữ nút.
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


    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
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
}