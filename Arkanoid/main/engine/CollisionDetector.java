package engine;

import entities.Ball;
import entities.Paddle;
import util.Constants;

import javax.swing.*;

public class CollisionDetector {

    public static boolean checkWallCollision (Ball ball) {

        if (ball.getX() < Constants.BALL_DIAMETER) {
           ball.setX(20);
           ball.setDx(-ball.getDx());
           return true;
        }

        if (ball.getX() > Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER) {
            ball.setX(Constants.SCREEN_WIDTH - 20);
            ball.setDx(-ball.getDx());
            return true;
        }

        if (ball.getY() < Constants.BALL_DIAMETER) {
            ball.setY(20);
            ball.setDy(-ball.getDy());
            return true;
        }

        if (ball.getY() > Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER ) {
            ball.setY(Constants.SCREEN_HEIGHT - 20);
            ball.setDy(-ball.getDy());
            return true;
        }

        return false;
    }

    public boolean collisionDetector() {

        return false;
    }
    public boolean checkPaddleCollision(Ball ball, Paddle paddle) {

        return false;
    }
}
