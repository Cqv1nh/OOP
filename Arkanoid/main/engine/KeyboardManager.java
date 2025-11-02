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
     * Updates the keyboard state.
     * MUST be called once per frame at the start of your game loop or state update.
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
     * Check if a key is currently held down.
     * @param keyCode The KeyEvent constant (e.g., KeyEvent.VK_SPACE)
     * @return true if the key is currently pressed
     */
    public boolean isKeyDown(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            return keys[keyCode];
        }
        return false;
    }

    /**
     * Check if a key was just pressed this frame (single press detection).
     * @param keyCode The KeyEvent constant (e.g., KeyEvent.VK_R)
     * @return true only on the frame the key was pressed
     */
    public boolean isKeyJustPressed(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            return justPressed[keyCode];
        }
        return false;
    }

    /**
     * Check if a key was just released this frame.
     * @param keyCode The KeyEvent constant (e.g., KeyEvent.VK_SPACE)
     * @return true only on the frame the key was released
     */
    public boolean isKeyJustReleased(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            return justReleased[keyCode];
        }
        return false;
    }

    /**
     * Updates paddle movement based on keyboard input.
     * @param paddle The paddle to control
     */
    public void updatePaddle(Paddle paddle) {
        if (isKeyDown(moveLeftPrimary) || isKeyDown(moveLeftSecondary)) {
            paddle.setDx(-paddle.getSpeed());
        } else if (isKeyDown(moveRightPrimary) || isKeyDown(moveLeftSecondary)) {
            paddle.setDx(paddle.getSpeed());
        } else {
            paddle.setDx(0);
        }
    }

    /**
     * Checks if spacebar was pressed to launch the ball.
     * @param ball The ball to launch
     * @return true if the ball should stop following the paddle
     */
    public boolean outFollow(Ball ball) {
        if (isKeyJustPressed(KeyEvent.VK_SPACE)) {
            ball.setDy(-ball.getSpeed());
            return true;
        }
        return false;
    }

    /**
     * Checks if 'R' key was pressed (for returning to menu, restarting, etc.)
     * @return true if R was just pressed this frame
     */
    public boolean back() {
        return isKeyJustPressed(KeyEvent.VK_R);
    }

    public boolean getKey(int keycode) {
        return keys[keycode];
    }
    /**
     * Manually clear all key states. Useful when switching game states.
     */
    public void clearAllKeys() {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = false;
            justPressed[i] = false;
            justReleased[i] = false;
            wasPressed[i] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used for game input
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = false;
        }
    }

    public boolean isAnyKeyPressed() {
        return lastKeyPressed > 0;
    }

    public int getLastKeyPressed() {
        return lastKeyPressed;
    }

    public int getMoveLeftPrimary() {
        return moveLeftPrimary;
    }

    public void setMoveLeftPrimary(int moveLeftPrimary) {
        this.moveLeftPrimary = moveLeftPrimary;
    }

    public int getMoveLeftSecondary() {
        return moveLeftSecondary;
    }

    public void setMoveLeftSecondary(int moveLeftSecondary) {
        this.moveLeftSecondary = moveLeftSecondary;
    }

    public int getMoveRightPrimary() {
        return moveRightPrimary;
    }

    public void setMoveRightPrimary(int moveRightPrimary) {
        this.moveRightPrimary = moveRightPrimary;
    }

    public int getMoveRightSecondary() {
        return moveRightSecondary;
    }

    public void setMoveRightSecondary(int moveRightSecondary) {
        this.moveRightSecondary = moveRightSecondary;
    }
}