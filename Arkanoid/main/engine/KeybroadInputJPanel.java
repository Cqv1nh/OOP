package engine;

import entities.Ball;
import entities.Paddle;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeybroadInputJPanel extends JPanel implements KeyListener {
    private boolean[] keys = new boolean[256];

    public KeybroadInputJPanel(){
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = false;
        }
    }

    public void update(Paddle paddle) {
        if (keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) {
            //System.out.println("L \n");
            paddle.setDx(-paddle.getSpeed());
        } else if (keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) {
            //System.out.println("R \n");
            paddle.setDx(paddle.getSpeed());
        } else {
            paddle.setDx(0);
        }
    }

    public boolean outFollow(Ball ball) {
        if (keys[KeyEvent.VK_SPACE]) {
           ball.setDy(-ball.getSpeed());

           return true;
        }
        return  false;
    }
}
