package engine;

import entities.Ball;
import entities.Brick;
import entities.GameObject;
import entities.Paddle;
import util.Constants;

import javax.swing.*;


class CollisionResult {
    boolean collided;
    double collisionX;
    double collisionY;
    GameObject rect;

    public CollisionResult(boolean collided, double collisionX, double collisionY, GameObject rect) {
        this.collided = collided;
        this.collisionX = collisionX;
        this.collisionY = collisionY;
        this.rect = rect;
    }

}
public class CollisionDetector {

    public static boolean checkWallCollision (Ball ball) {

        if (ball.getX() < 10) {
           ball.setX(10);
           ball.setDx(ball.getSpeed());
           return true;
        }

        if (ball.getX() > Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER) {
            ball.setX(Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER);
            ball.setDx(-ball.getSpeed());
            return true;
        }

        if (ball.getY() < 10) {
            ball.setY(10);
            ball.setDy(ball.getSpeed());
            return true;
        }

        if (ball.getY() > Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER ) {
            ball.setY(Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER);
            ball.setDy(-ball.getSpeed());
            return true;
        }

        return false;
    }

    public static CollisionResult checkCollision(Ball ball, GameObject obj) {
        double closestX = Math.max(obj.getX(), Math.min(ball.getX(), obj.getX() + obj.getWidth()));
        double closestY = Math.max(obj.getY(), Math.min(ball.getY(), obj.getY() + obj.getHeight()));

        // Calculate distance
        double distanceX = ball.getX() - closestX;
        double distanceY = ball.getY() - closestY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        // Check collision
        if (distance < (double) Constants.BALL_DIAMETER / 2) {
            return new CollisionResult(true, closestX, closestY, obj);
        }

        return new CollisionResult(false, 0, 0, null);
    }

    enum CollisionSide {
        NONE, TOP, BOTTOM, LEFT, RIGHT
    }

    public static CollisionSide getCollisionSide(CollisionResult result, Ball ball) {
        if (!result.collided) {
            return CollisionSide.NONE;
        }

        GameObject rect = result.rect;
        double ballCenterX = ball.getX() + Constants.BALL_DIAMETER / 2.0;
        double ballCenterY = ball.getY() + Constants.BALL_DIAMETER / 2.0;

        double rectCenterX = rect.getX() + rect.getWidth() / 2.0;
        double rectCenterY = rect.getY() + rect.getHeight() / 2.0;

        double overlapX = (Constants.BALL_DIAMETER / 2.0 + rect.getWidth() / 2.0) - Math.abs(ballCenterX - rectCenterX);
        double overlapY = (Constants.BALL_DIAMETER / 2.0 + rect.getHeight() / 2.0) - Math.abs(ballCenterY - rectCenterY);

        if (overlapX < overlapY) {
            // Collision is more horizontal than vertical
            if (ballCenterX < rectCenterX) {
                return CollisionSide.LEFT;
            } else {
                return CollisionSide.RIGHT;
            }
        } else {
            // Collision is more vertical than horizontal
            if (ballCenterY < rectCenterY) {
                return CollisionSide.TOP;
            } else {
                return CollisionSide.BOTTOM;
            }
        }
    }

    public static void handleCollision(CollisionSide side, Paddle paddle, Ball ball) {
        switch (side) {
            case CollisionSide.TOP:
                ball.setDy(-ball.getSpeed());

                if (paddle != null) {
                    double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
                    double hitOffset = (ball.getX() - paddleCenter) / (paddle.getWidth() / 2);
                    ball.setDx(hitOffset * 4);
                }
                System.out.println("TOP");
                break;
            case CollisionSide.BOTTOM:
                ball.setDy(ball.getSpeed()); // Bounce down
                System.out.println("BOTTOM");
                break;

            case CollisionSide.LEFT:
                ball.setDx(-ball.getSpeed()); // Bounce horizontally
                System.out.println("SIDE");
                break;
            case CollisionSide.RIGHT:
                ball.setDx(ball.getSpeed()); // Bounce horizontally
                System.out.println("SIDE");
                break;
        }

        if (paddle == null) {
            return;
        }
        double radius = Constants.BALL_DIAMETER / 2.0;
        if (side == CollisionSide.TOP) {
            ball.setY(paddle.getY() - Constants.BALL_DIAMETER);
        } else if (side == CollisionSide.BOTTOM) {
            ball.setY(paddle.getY() + paddle.getHeight());
        } else if (side == CollisionSide.LEFT) {
            ball.setX(paddle.getX() - Constants.BALL_DIAMETER);
        } else if (side == CollisionSide.RIGHT) {
            ball.setX(paddle.getX() + paddle.getWidth());
        }

    }

    public static void handlePaddleCollision(Paddle paddle, Ball ball) {
        CollisionResult result = CollisionDetector.checkCollision(ball, paddle);

        if (result.collided) {
            CollisionSide side = CollisionDetector.getCollisionSide(result, ball);
            handleCollision(side, paddle, ball);
        }
    }

    public static boolean handleBrickCollision(Brick brick, Ball ball) {

        if (brick.getHitPoints() == 0) return false;

        CollisionResult result = CollisionDetector.checkCollision(ball, brick);

        if (result.collided) {
            CollisionSide side = CollisionDetector.getCollisionSide(result, ball);

            handleCollision(side, null, ball);
            brick.setHitPoints(brick.getHitPoints() - 1);
            return true; // Brick should be destroyed
        }

        return false;
    }

}
