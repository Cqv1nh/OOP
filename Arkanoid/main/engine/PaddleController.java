package engine;

import entities.Paddle;

public class PaddleController {
    private final KeyboardInputTerminal keyboardInputTerminal;

    public PaddleController(KeyboardInputTerminal inputTerminal) {
        this.keyboardInputTerminal = inputTerminal;
    }

    public void startListening() {
        keyboardInputTerminal.start();
    }

    public boolean movePaddle(Paddle paddle) {
        Character key = keyboardInputTerminal.pollNextKey();

        paddle.setDx(0);
        if (key != null) {
            switch (key) {
                case 'a':
                    paddle.setDx(-paddle.getSpeed());
                    paddle.move();
                    return true;  // keep running
                case 'd':
                    paddle.setDx(paddle.getSpeed());
                    paddle.move();
                    return true;  // keep running
                case 'q':
                    return false; // signal game to stop
            }
        }
        return true; // no key pressed → keep running
    }

    public void stopListening() {
        keyboardInputTerminal.stop();
    }
}
