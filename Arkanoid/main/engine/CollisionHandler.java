package engine;

import java.util.ArrayList;

import entities.Ball;
import entities.Brick;
import entities.ExplosiveBrick;
import entities.Paddle;
import managers.LevelState2;
import ui.GameWindow;
import util.BrickType;
import util.Constants;
import util.AudioManager;

// Thinh
// Lớp này tập trung toàn bộ logic xử lý va chạm phức tạp.
public class CollisionHandler {

    public CollisionHandler() {
    }

    // Quan ly cac powerUp
    private final PowerUpManager powerUpManager = new PowerUpManager();

    public void handleCollisions(GameWindow game, int panelWidth) {
        // Neu bong van con tren paddle, khong thuc hien tinh toan va cham
        if (!game.isBallLaunched()) {
            return;
        }
        // Phuong thuc xu ly
        handleBallWallCollision(game.getBall(), panelWidth);
        handleBallPaddleCollision(game.getBall(), game.getPaddle());
        handleBallBrickCollision(game);
    }
    // Va cham bong voi tuong
    public void handleBallWallCollision(Ball ball, int panelWidth) {
        // va cham voi tuong
        if (ball.getX() <= 0) {
            ball.setX(0);
            ball.setDirectionX(-ball.getDirectionX());
        }
        if (ball.getX() + ball.getRadius() * 2 >= panelWidth) {
            ball.setX(panelWidth - ball.getRadius() * 2);
            ball.setDirectionX(-ball.getDirectionX());
        }
        if (ball.getY() <= 0) {
            ball.setY(0);
            ball.setDirectionY(-ball.getDirectionY());
        }

    }
    // Xu ly bong voi paddle
    private void handleBallPaddleCollision(Ball ball, Paddle paddle) {
        // Va cham voi paddle
        if (ball.getY() + ball.getRadius() * 2 >= paddle.getY() 
            && ball.getX() + ball.getRadius() * 2 >= paddle.getX() 
            && ball.getX() <= paddle.getX() + paddle.getWidth()
            && ball.getY() < paddle.getY() + paddle.getHeight()) {

            // Tính vị trí tương đối va chạm so với tâm paddle
            double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
            double hitPosition = (ball.getX() + ball.getRadius()) - paddleCenter;

            // Chuẩn hóa hitPosition thành [-1, 1]
            double normalized = hitPosition / (paddle.getWidth() / 2.0);

            // Giới hạn để không ra ngoài [-1,1]
            if (normalized < -1) normalized = -1;
            if (normalized > 1) normalized = 1; 
                
            // Góc bật lại: từ 150° (trái) → 30° (phải)
            double bounceAngle = Math.toRadians(150 - 120 * (normalized + 1) / 2.0);
            bounceAngle += Math.toRadians((Math.random() - 0.5) *10); // them ngau nhien + - 5 do

            // Cập nhật hướng di chuyển mớ
            ball.setDirectionX(Math.cos(bounceAngle));
            ball.setDirectionY(-Math.abs(Math.sin(bounceAngle)));
            // Áp lại tốc độ (nếu Ball.java dùng dx, dy = speed * direction)
            ball.setY(paddle.getY() - ball.getRadius() * 2);
        }
    }   
    
    // Va cham voi Gach
    private void handleBallBrickCollision(GameWindow game) {
        Ball ball = game.getBall();
        ArrayList<Brick> brickList = game.getBricks();
        for (Brick b : brickList) {
            // Tinh tam cua ball
            double centerBallX = ball.getX() + ball.getRadius();
            double centerBallY = ball.getY() + ball.getRadius();
            // Tinh diem tren hinh chu nhat co toa do gan ball nhat
            double closetX = Math.max(b.getX(), Math.min(centerBallX, b.getX() + b.getWidth()));
            double closetY = Math.max(b.getY(), Math.min(centerBallY, b.getY() + b.getHeight()));
            // Tinh khoang tu diem do den tam qua bong
            double distance = Math.sqrt((centerBallX - closetX) * (centerBallX - closetX)
            + (centerBallY - closetY) * (centerBallY - closetY));
            // Dk va cham: khoang cach nho hon ban kinh
            if (distance < ball.getRadius()) {
                // Xử lý vật lý va chạm
                processCollisionPhysics(ball, closetX, closetY, distance);
                // Xử lý logic gạch
                if (b.getType() != BrickType.UNBREAKABLE) {
                    b.takeHit();
                }
                // Xu ly gach no
                if (b.isDestroyed() && b instanceof ExplosiveBrick) {
                    ExplosiveBrick e = (ExplosiveBrick) b;
                    e.explode(brickList);
                }

                // Chỉ xử lý một va chạm gạch mỗi khung hình để tránh lỗi
                break;
            }
        }

        // Dọn dẹp gạch đã bị phá hủy
        brickList.removeIf(brick -> {
            if (brick.isDestroyed()) {
                // Cong diem truoc khi xoa
                game.addScore(brick.getScore());
                // Tao powerup khi gach vo
                if (brick.hasPowerUp()) {
                    powerUpManager.spawnPowerUp(game, brick);
                }
                return true;
            }
            return false;
        });
    }




    // V2
    public void handleCollisions(LevelState2 game, int panelWidth, int panelHeight) {
        // Neu bong van con tren paddle, khong thuc hien tinh toan va cham
        if (!game.isBallLaunched()) {
            return;
        }

        ArrayList<Ball> balls = new ArrayList<>();
        balls = game.getBalls();

        if (balls.size() == 1) {
            if (handleBallWallCollision(balls.getFirst())) {
                game.setBallLaunched(false);
                game.loseLives();
            }
        }
        for (Ball ball : game.getBalls()) {
            handleBallWallCollision(ball);
            handleBallPaddleCollision(ball, game.getPaddle());
            handleBallBrickCollision(game, ball); // Truyền quả bóng hiện tại vào
        }
    }

    //V2
    public boolean handleBallWallCollision(Ball ball) {
        // va cham voi tuong
        if (ball.getX() < 10) {
            ball.setX(10);
            ball.setDx(ball.getSpeed());
            return false;
        }

        if (ball.getX() > Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER) {
            ball.setX(Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER);
            ball.setDx(-ball.getSpeed());
            return false;
        }

        if (ball.getY() < 10) {
            ball.setY(10);
            ball.setDy(ball.getSpeed());
            return false;
        }

        if (ball.getY() > Constants.SCREEN_HEIGHT - Constants.BALL_DIAMETER ) {

            return true;
        }

        return false;
    }


    //V2
    private void handleBallBrickCollision(LevelState2 game, Ball ball) {
        ArrayList<Brick> brickList = game.getBricks();
        for (Brick b : brickList) {
            // Tinh tam cua ball
            double centerBallX = ball.getX() + ball.getRadius();
            double centerBallY = ball.getY() + ball.getRadius();
            // Tinh diem tren hinh chu nhat co toa do gan ball nhat
            double closetX = Math.max(b.getX(), Math.min(centerBallX, b.getX() + b.getWidth()));
            double closetY = Math.max(b.getY(), Math.min(centerBallY, b.getY() + b.getHeight()));
            // Tinh khoang tu diem do den tam qua bong
            double distance = Math.sqrt((centerBallX - closetX) * (centerBallX - closetX)
                    + (centerBallY - closetY) * (centerBallY - closetY));
            // Dk va cham: khoang cach nho hon ban kinh
            if (distance < ball.getRadius()) {
                AudioManager.playSound("ball_hit");
                // Xử lý vật lý va chạm
                processCollisionPhysics(ball, closetX, closetY, distance);
                // Xử lý logic gạch
                if (b.getType() != BrickType.UNBREAKABLE) {
                    b.takeHit();
                }
                // Xu ly gach no
                if (b.isDestroyed() && b instanceof ExplosiveBrick) {
                    ExplosiveBrick e = (ExplosiveBrick) b;
                    e.explode(brickList);
                }

                // Chỉ xử lý một va chạm gạch mỗi khung hình để tránh lỗi
                break;
            }
        }

        // Dọn dẹp gạch đã bị phá hủy
        brickList.removeIf(brick -> {
            if (brick != null && brick.isDestroyed()) {
                // Cong diem truoc khi xoa
                game.addScore(brick.getScore());
                // Tao powerup khi gach vo
                if (brick.hasPowerUp()) {
                    powerUpManager.spawnPowerUp(game, brick);
                }
                return true;
            }
            return false;
        });
    }

    private void processCollisionPhysics(Ball ball, double closetX, double closetY, double distance) {
        // Tinh tam cua ball
        double centerBallX = ball.getX() + ball.getRadius();
        double centerBallY = ball.getY() + ball.getRadius();
        // Vectophaptuyen
        double nx = centerBallX - closetX;
        double ny = centerBallY - closetY;
        // Tinh do lon vector
        double disN = Math.sqrt((nx * nx) + (ny * ny));
        if (disN == 0) {
            return;
        }

        // Chuan hoa
        double unix = nx / disN;
        double uniy = ny / disN;
        // Tinh van toc tuong doi, vat the brick luon co van toc bang 0
        double dentaV = ball.getDx() * unix + (ball.getDy()) * uniy;
        // Cong thuc van toc moi
        double newBallDx = ball.getDx() - 2 * dentaV * unix;
        double newBallDy = ball.getDy() - 2 * dentaV * uniy;
        // Đẩy bóng ra khỏi gạch để tránh bị kẹt
        double overlap = ball.getRadius() - distance;
        if (overlap > 0) {
            // Va cham bat lai, brick dc co dinh nen bong phai dc lui ra dung 1 overlap
            double newcenterBallX = centerBallX + unix * overlap;
            double newcenterBallY = centerBallY + uniy * overlap;
            ball.setX(newcenterBallX - ball.getRadius());
            ball.setY(newcenterBallY - ball.getRadius());
        }

        // Cập nhật hướng và tốc độ mới
        double newSpeed = Math.sqrt(newBallDx * newBallDx + newBallDy * newBallDy);
        ball.setSpeed(3);
        ball.setDirectionX(newBallDx / ball.getSpeed());
        ball.setDirectionY(newBallDy / ball.getSpeed());
    }
}
