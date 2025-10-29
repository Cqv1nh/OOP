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

    /**
     * Updates the keyboard state.
     * MUST be called once per frame at the start of your game loop or state update.
     */
    public void update() {
        for (int i = 0; i < keys.length; i++) {
            // Key was just pressed this frame if it's down now but wasn't down last frame
            justPressed[i] = keys[i] && !wasPressed[i];

            // Key was just released this frame if it's up now but was down last frame
            justReleased[i] = !keys[i] && wasPressed[i];

            // Update the previous state for next frame
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
        if (isKeyDown(KeyEvent.VK_A) || isKeyDown(KeyEvent.VK_LEFT)) {
            paddle.setDx(-paddle.getSpeed());
        } else if (isKeyDown(KeyEvent.VK_D) || isKeyDown(KeyEvent.VK_RIGHT)) {
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
}