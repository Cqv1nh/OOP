package engine;

import entities.Ball;
import entities.Paddle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardManager implements KeyListener {
    private final boolean[] keys = new boolean[256];
    private final boolean[] justPressed = new boolean[256];
    private final boolean[] justReleased = new boolean[256];
    private final boolean[] wasPressed = new boolean[256];

    private int lastKeyPressed = -1;

    private int moveLeftPrimary;
    private int moveLeftSecondary;
    private int moveRightPrimary;
    private int moveRightSecondary;

    /**
     * Cập nhật trạng thái "vừa nhấn", "vừa thả" cho tất cả các phím (phải gọi 1 lần/khung hình).
     */
    public void update() {
        lastKeyPressed = -1;
        for (int i = 0; i < keys.length; i++) {
            justPressed[i] = keys[i] && !wasPressed[i];
            if(justPressed[i]) {
                lastKeyPressed = i;
            }
            justReleased[i] = !keys[i] && wasPressed[i];
            wasPressed[i] = keys[i];
        }
    }

    /**
     * Kiểm tra xem một phím có đang được giữ nhấn hay không.
     *
     * @param keyCode The KeyEvent constant (e.g., KeyEvent.VK_SPACE)
     * @return đúng nếu đang được giữ nhấn
     */
    public boolean isKeyDown(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            return keys[keyCode];
        }
        return false;
    }

    /**
     * Kiểm tra xem một phím có vừa mới được nhấn trong khung hình này không (chỉ đúng 1 lần).
     *
     * @param keyCode
     * @return
     */
    public boolean isKeyJustPressed(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            return justPressed[keyCode];
        }
        return false;
    }

    /**
     * Kiểm tra xem một phím có vừa mới được thả ra trong khung hình này không (chỉ đúng 1 lần).
     *
     * @param keyCode
     * @return
     */
    public boolean isKeyJustReleased(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            return justReleased[keyCode];
        }
        return false;
    }

    /**
     * Cập nhật vận tốc (Dx) của thanh trượt dựa trên phím di chuyển trái/phải đang được giữ.
     *
     * @param paddle đang được xét.
     */
    public void updatePaddle(Paddle paddle) {
        if (isKeyDown(moveLeftPrimary) || isKeyDown(moveLeftSecondary)) {
            paddle.setDx(-paddle.getSpeed());
        } else if (isKeyDown(moveRightPrimary) || isKeyDown(moveRightSecondary)) {
            paddle.setDx(paddle.getSpeed());
        } else {
            paddle.setDx(0);
        }
    }

    /**
     * Kiểm tra phím Space (vừa nhấn), nếu đúng thì phóng quả bóng bay lên (setDy).
     *
     * @param ball
     * @return T or F.
     */
    public boolean outFollow(Ball ball) {
        if (isKeyJustPressed(KeyEvent.VK_SPACE)) {
            ball.setDy(-ball.getSpeed());
            return true;
        }
        return false;
    }

    /**
     * Kiểm tra xem phím 'R' có vừa được nhấn không (dùng để quay lại menu hoặc chơi lại).
     *
     * @return T or F.
     */
    public boolean back() {
        return isKeyJustPressed(KeyEvent.VK_R);
    }

    /**
     * Kiểm tra xem một phím có đang được giữ nhấn hay không.
     *
     * @param keycode
     * @return T or F.
     */
    public boolean getKey(int keycode) {
        return keys[keycode];
    }

    /**
     * Method đặt lại trạng thái tất cả các phím thành chưa nhấn.
     */
    public void clearAllKeys() {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = false;
            justPressed[i] = false;
            justReleased[i] = false;
            wasPressed[i] = false;
        }
    }

    /**
     * Method ghi đè, được gọi khi gõ phím (ký tự).
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used for game input
    }

    /**
     * Method ghi đè, được hệ điều hành gọi khi một phím được nhấn xuống, đánh dấu phím đó là true.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = true;
        }
    }

    /**
     * Method ghi đè, được hệ điều hành gọi khi một phím được thả ra, đánh dấu phím đó là false.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = false;
        }
    }

    /**
     * Kiểm tra xem có bất kỳ phím nào vừa được nhấn trong khung hình này không.
     *
     * @return
     */
    public boolean isAnyKeyPressed() {
        return lastKeyPressed > 0;
    }

    /**
     * Trả về mã (keyCode) của phím cuối cùng vừa được nhấn.
     *
     * @return mã keyCode.
     */
    public int getLastKeyPressed() {
        return lastKeyPressed;
    }

    /**
     * Getter cho mã phím sang trái chính.
     *
     * @return
     */
    public int getMoveLeftPrimary() {
        return moveLeftPrimary;
    }

    /**
     * Setter cho mã phím sang trái chính.
     *
     * @param moveLeftPrimary
     */
    public void setMoveLeftPrimary(int moveLeftPrimary) {
        this.moveLeftPrimary = moveLeftPrimary;
    }

    /**
     * Getter cho mã phím sang trái phụ.
     *
     * @return
     */
    public int getMoveLeftSecondary() {
        return moveLeftSecondary;
    }

    /**
     * Setter cho mã phím sang trái phụ.
     *
     * @param moveLeftSecondary
     */
    public void setMoveLeftSecondary(int moveLeftSecondary) {
        this.moveLeftSecondary = moveLeftSecondary;
    }

    /**
     * Getter cho mã phím sang phải chính.
     *
     * @return
     */
    public int getMoveRightPrimary() {
        return moveRightPrimary;
    }

    /**
     * Setter cho mã phím sang phải chính.
     *
     * @param moveRightPrimary
     */
    public void setMoveRightPrimary(int moveRightPrimary) {
        this.moveRightPrimary = moveRightPrimary;
    }

    /**
     * Getter cho mã phím sang phải phụ.
     *
     * @return
     */
    public int getMoveRightSecondary() {
        return moveRightSecondary;
    }

    /**
     * Setter cho mã phím sang phải phụ.
     *
     * @param moveRightSecondary
     */
    public void setMoveRightSecondary(int moveRightSecondary) {
        this.moveRightSecondary = moveRightSecondary;
    }
}