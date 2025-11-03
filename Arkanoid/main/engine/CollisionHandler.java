package engine;

import entities.Ball;
import entities.Brick;
import entities.ExplosiveBrick;
import entities.Paddle;
import java.util.ArrayList;
import java.util.List;
import managers.LevelState2;
import util.BrickType;
import util.Constants;
import util.AudioManager;

public class CollisionHandler {
    private final PowerUpManager powerUpManager = new PowerUpManager();
    /**
     * Constructor không có tham số.
     */
    public CollisionHandler() {
    }

    /**
     * Method điều phồi tổng cho va chạm.
     *
     * @param game
     * @param panelWidth độ rộng màn hình.
     * @param panelHeight độ cao màn hình.
     */
    public void handleCollisions(LevelState2 game, int panelWidth, int panelHeight) {
        // Neu bong van con tren paddle, khong thuc hien tinh toan va cham
        if (!game.isBallLaunched()) {
            return;
        }

        for (Ball ball : game.getBalls()) {
            if (handleBallWallCollision(ball)) { // Nếu va chạm cạnh dưới (true)
            }
            handleBallPaddleCollision(ball, game.getPaddle());
            handleBallBrickCollision(game, ball); // Truyền quả bóng hiện tại vào
        }
    }

    /**
     * Method xử lý va chạm bóng với tường.
     *
     * @param ball bóng
     * @param panelWidth độ rộng màn hình.
     */
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

    /**
     * Method xử lý bóng va chạm với thanh.
     *
     * @param ball bóng
     * @param paddle thanh.
     */
    private void handleBallPaddleCollision(Ball ball, Paddle paddle) {
        // Va cham voi paddle
        if (ball.getY() + ball.getRadius() * 2 >= paddle.getY() 
            && ball.getX() + ball.getRadius() * 2 >= paddle.getX() 
            && ball.getX() <= paddle.getX() + paddle.getWidth()
            && ball.getY() < paddle.getY() + paddle.getHeight()) {

            AudioManager.playSound("sfx_ball_vs_paddle");
            
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
            // Áp lại tốc độ
            ball.setY(paddle.getY() - ball.getRadius() * 2);
        }
    }

    /**
     * Method va chạm bóng với tường, trả về T, F.
     *
     * @param ball bóng
     * @return T or F.
     */
    public boolean handleBallWallCollision(Ball ball) {
        boolean hitWall = false; // Biến để kiểm tra có va chạm tường (trên, trái, phải) không

        // Va chạm tường TRÁI
        if (ball.getX() <= 0) { // Dùng <= 0 thay vì < 10 để sát mép
            ball.setX(0); // Đặt lại vị trí sát mép
            ball.setDirectionX(-ball.getDirectionX()); // Đổi hướng X
            ball.setDx(ball.getSpeed() * ball.getDirectionX()); // Cập nhật Dx
            hitWall = true;
        }

        // Va chạm tường PHẢI
        else if (ball.getX() + Constants.BALL_DIAMETER >= Constants.SCREEN_WIDTH) { // Dùng >= thay vì >
            ball.setX(Constants.SCREEN_WIDTH - Constants.BALL_DIAMETER); // Đặt lại vị trí sát mép
            ball.setDirectionX(-ball.getDirectionX()); // Đổi hướng X
            ball.setDx(ball.getSpeed() * ball.getDirectionX()); // Cập nhật Dx
            hitWall = true;
        }

        // Va chạm tường TRÊN
        if (ball.getY() <= 0) { // Dùng <= 0 thay vì < 10
            ball.setY(0); // Đặt lại vị trí sát mép
            ball.setDirectionY(-ball.getDirectionY()); // Đổi hướng Y
            ball.setDy(ball.getSpeed() * ball.getDirectionY()); // Cập nhật Dy
            hitWall = true;
        }

        // Va chạm cạnh DƯỚI (Rơi ra ngoài)
        else if (ball.getY() + Constants.BALL_DIAMETER >= Constants.SCREEN_HEIGHT) { // Dùng >= thay vì >
            // Không đổi hướng, chỉ báo hiệu là bóng đã rơi
            return true; // Trả về true để báo hiệu bóng rơi
        }

        // Nếu có va chạm tường (trên, trái, phải), phát âm thanh
        if (hitWall) {
            AudioManager.playSound("ball_hit");
        }

        // Nếu không va chạm cạnh dưới, trả về false
        return false;
    }

    /**
     * Method xử lý va chạm bóng với gạch.
     *
     * @param game
     * @param ball bóng.
     */
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
                // Xử lý vật lý va chạm
                processCollisionPhysics(ball, closetX, closetY, distance);

                // Chỉ xử lý va chạm nếu gạch CÒN SỐNG (hitPoints > 0)
                if (b.getHitPoints() > 0) {
                    if (b instanceof ExplosiveBrick) {
                        // 1. Phát âm thanh gạch nổ
                        AudioManager.playSound("sfx_ball_vs_explosive_brick");
                    } else if (b.getType() != BrickType.UNBREAKABLE) {
                        // 2. Phát âm thanh gạch thường/mạnh
                        AudioManager.playSound("sfx_ball_vs_brick");
                    } else {
                        // 3. Nếu là gạch không vỡ, phát âm thanh va tường
                        AudioManager.playSound("ball_hit"); 
                    }

                    if (b.getType() != BrickType.UNBREAKABLE) {
                        b.takeHit(); // Gạch nhận sát thương
                    }

                    // Kiểm tra xem cú đánh này có VỪA PHÁ VỠ gạch không
                    if (b.isDestroyed()) {
                        List<Brick> totalDestroyed = new ArrayList<>(); // isDestroyed() vẫn là (hitPoints <= 0)
                        
                        // Xử lý GẠCH NỔ (nó sẽ bị xóa ngay ở `removeIf`)
                        if (b instanceof ExplosiveBrick) {
                            ExplosiveBrick e = (ExplosiveBrick) b;
                            totalDestroyed.addAll(e.startExplosion(brickList));
                            // Cộng điểm và tạo powerup cho gạch nổ NGAY LẬP TỨC
                            game.addScore(b.getScore()); 
                            if (b.hasPowerUp()) {
                                powerUpManager.spawnPowerUp(game, b);
                            }
                        }
                        totalDestroyed.add(b);

                        // 3. XỬ LÝ ĐIỂM VÀ POWER-UP CHO TẤT CẢ GẠCH BỊ HỦY TRONG CHUỖI PHẢN ỨNG
                        for (Brick destroyedBrick : totalDestroyed) {
                            // Kiểm tra để tránh cộng điểm cho Unbreakable
                            if (!destroyedBrick.getType().equals(BrickType.UNBREAKABLE)) {
                                // Chỉ cộng điểm/tạo powerup MỘT LẦN khi gạch VỪA BỊ PHÁ HỦY
                                // Cộng điểm ngay lập tức
                                game.addScore(destroyedBrick.getScore()); 
                                if (destroyedBrick.hasPowerUp()) {
                                    powerUpManager.spawnPowerUp(game, destroyedBrick);
                                }

                                // Kích hoạt Fading cho gạch thường nếu chưa được kích hoạt
                                if ((destroyedBrick.getType().equals(BrickType.NORMAL) || destroyedBrick.getType().equals(BrickType.STRONG)) && !destroyedBrick.isFading()) {
                                    destroyedBrick.startFading();
                                }
                            }
                        }
                    }
                }
                // Chỉ xử lý một va chạm gạch mỗi khung hình để tránh lỗi
                break;
            }
        }
        // Dọn dẹp gạch đã bị phá hủy
        brickList.removeIf(brick -> {
            if (brick != null && brick.isReadyForRemoval()) {
                return true;
            }
            return false;
        });
    }

    /**
     * Method tính toán hướng bật lại + xử lý chống kẹt.
     *
     * @param ball bóng
     * @param closetX
     * @param closetY
     * @param distance khoảng cách
     */
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
        ball.setSpeed(newSpeed);
        ball.setDirectionX(newBallDx / ball.getSpeed());
        ball.setDirectionY(newBallDy / ball.getSpeed());
    }
}
