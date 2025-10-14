package ui;

import javax.swing.*;
import entities.Ball;
import entities.Brick;
import entities.ExpandPaddlePowerUp;
import entities.ExplosiveBrick;
import entities.ExtraLifePowerUp;
import entities.FastBallPowerUp;
import entities.MultiBallPowerUp;
import entities.Paddle;
import entities.PowerUp;
import util.BrickType;
import util.Constants;
import util.GameState;

import java.util.ArrayList;
import java.util.Iterator;
// Class thuc hien vong lap game
public class GameLoop {
    private GameWindow game;
    private GamePanel panel;
    private Timer timer;

    // Thêm biến để theo dõi thời gian cho logic PowerUp
    private long lastUpdateTime = System.currentTimeMillis();
    
    // Constructor
    public GameLoop(GameWindow game, GamePanel panel) {
        this.game = game;
        this.panel = panel;
    }

    public void start() {
        // Cập nhật thời gian khởi tạo: 
        lastUpdateTime = System.currentTimeMillis();
        timer = new Timer(16, e -> update());
        // GAME LOOP 60 FPS
        timer.start();
    }

    private void update() {
        // Neu chua vao game thi k chay Timer
        if (!game.getGameState().equals(GameState.GAMEPLAYING)) {
            panel.repaint();
            return;
        }

        Ball ball = game.getBall();
        Paddle paddle = game.getPaddle();
        ArrayList<Brick> brickList = game.getBricks();

        int panelWidth = panel.getWidth();
        // int panelHeight = panel.getHeight();

        // Xu ly logic Paddle dau tien
        paddle.move();
        if (paddle.getX() < 0) {
            paddle.setX(0);
        }
        if (paddle.getX() + paddle.getWidth() > panelWidth) {
            paddle.setX(panelWidth - paddle.getWidth());
        }

        double speed = ball.getSpeed(); // Lay gia tri speed
        if (game.isBallLaunched()) {
            ball.move();
            // va cham voi tuong
            if (ball.getX() <= 0) {
                ball.setX(0);
                ball.setDx(-ball.getDx());
            }
            if (ball.getX() + ball.getRadius() * 2 >= panelWidth) {
                ball.setX(panelWidth - ball.getRadius() * 2);
                ball.setDx(-ball.getDx());
            }
            if (ball.getY() <= 0) {
                ball.setY(0);
                ball.setDy(-ball.getDy());
            }

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
                // Cập nhật hướng di chuyển mới
            
                ball.setDirectionX(Math.cos(bounceAngle));
                ball.setDirectionY(-Math.abs(Math.sin(bounceAngle)));

                // Áp lại tốc độ (nếu Ball.java dùng dx, dy = speed * direction)
                ball.setDx(speed * ball.getDirectionX());
                ball.setDy(speed * ball.getDirectionY());
                ball.setY(paddle.getY() - ball.getRadius() * 2);
            }

            // Va cham voi brick Thinh thay doi logic va cham 1 chut
            boolean collisionOccurred = false;
            for (Brick b : brickList) {
                if (collisionOccurred == true) {
                    break; 
                    // Chi xu ly 1 gach moi vong for
                }

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
                    // Dat lai vi tri hinh 
                    // Tinh do chong lan
                    double overlap = ball.getRadius() - distance;
                    if (overlap > 0) {
                        // Va cham bat lai, brick dc co dinh nen bong phai dc lui ra dung 1 overlap
                        double newcenterBallX = centerBallX + unix * overlap;
                        double newcenterBallY = centerBallY + uniy * overlap;
                        ball.setX(newcenterBallX - ball.getRadius());
                        ball.setY(newcenterBallY - ball.getRadius());
                    }
                    // dat gia tri van toc
                    ball.setDx(newBallDx);
                    ball.setDy(newBallDy);
                    // Brick nhan sat thuong
                    // Day chinh la nguyen nhan gay ra bug bong di chuyen k dung vat ly doi voi gach k the bi pha vo
                    // Chi ap dung takeHit doi voi cac loai gach khac gach k the bi pha vo
                    if (b.getType() != BrickType.UNBREAKABLE) {
                        b.takeHit();
                    }
                    if (b.isDestroyed() && b instanceof ExplosiveBrick) {
                       ExplosiveBrick e = (ExplosiveBrick) b;
                       e.explode(brickList);
                    }
                }
            }
            // Don dep tat ca vien gach khi vao vu no
            // Them cong diem cho cac vien gach
            brickList.removeIf(brick -> {
                if (brick.isDestroyed()) {
                    game.addScore(brick.getScore());
                    // Tao powerup khi gach vo
                    if (brick.hasPowerUp()) {
                        spawnPowerUp(brick);
                    }
                    // Cong diem truoc khi xoa
                    return true;
                } 
                return false;
            });

            // Kiem tra thang man
            if (brickList.stream().allMatch(b -> b.getType() == BrickType.UNBREAKABLE)) {
                game.setGameState(GameState.LEVELCLEAR);
            }
            // Khi bong roi khoi man hinh
            if (ball.getY() > Constants.SCREEN_HEIGHT) {
                // giam mang di 1 lan
                game.loseLives();
                if (game.getLives() > 0) {
                    game.resetAfterLifeLost();
                } else {
                    game.setGameState(GameState.GAMEEND);
                }
            }
        } else {
            ball.setX(paddle.getX()+paddle.getWidth() / 2 - ball.getRadius());
            ball.setY(paddle.getY() - ball.getRadius() * 2 - 1);
        }
        // Cap nhat va xu ly va cham cho PowerUps
        updatePowerUps();
        panel.repaint();

        // Xử lý thời gian hiệu lực của PowerUp
        processActivePowerUps();
    }

    private void spawnPowerUp(Brick brick) {
        String type = brick.getPowerUpType();
        PowerUp newPowerUp = null;

        // Dung switch de them cac powerup khac trong tuong lai
        switch (type) {
            case "EXTRA_LIFE":
                newPowerUp = new ExtraLifePowerUp(
                    brick.getX() + brick.getWidth() / 2 - Constants.POWERUP_WIDTH / 2, 
                    brick.getY(), Constants.POWERUP_WIDTH, 
                    Constants.POWERUP_HEIGHT, 0.0);
                break;
            case "FAST_BALL":
                newPowerUp = new FastBallPowerUp(
                    brick.getX() + brick.getWidth() / 2 - Constants.POWERUP_WIDTH / 2, 
                    brick.getY(), Constants.POWERUP_WIDTH, 
                    Constants.POWERUP_HEIGHT, (double) Constants.POWERUP_DURATION); // Hiệu ứng kéo dài 7 giây
                break;
            case "EXPAND_PADDLE":
                newPowerUp = new ExpandPaddlePowerUp(
                    brick.getX() + brick.getWidth() / 2 - Constants.POWERUP_WIDTH / 2, 
                    brick.getY(), Constants.POWERUP_WIDTH, 
                    Constants.POWERUP_HEIGHT, (double)Constants.POWERUP_DURATION);
                break;
            case "MULTI_BALL":
                newPowerUp = new MultiBallPowerUp(
                    brick.getX() + brick.getWidth() / 2 - Constants.POWERUP_WIDTH / 2, 
                    brick.getY(), Constants.POWERUP_WIDTH, 
                    Constants.POWERUP_HEIGHT, 0.0);
                // KẾT THÚC ĐOẠN CODE THÊM
    
            default:
                break;
        }
        if (newPowerUp != null) {
            game.getPowerUps().add(newPowerUp);
        }
    }

    private void updatePowerUps() {
        ArrayList<PowerUp> powerUps = game.getPowerUps();
        Paddle paddle = game.getPaddle();
        // Dung itertator de xoa an toan
        Iterator<PowerUp> iterator = powerUps.iterator();

        while (iterator.hasNext()) {
            PowerUp p = iterator.next(); // Lay phan tu tiep theo
            p.update(null); // cho powerup di chuyen xuong duoi
            
            //Kiểm tra va chạm với paddle
            if (p.getX() < paddle.getX() + paddle.getWidth() 
            && p.getX() + p.getWidth() > paddle.getX() 
            && p.getY() < paddle.getY() + paddle.getHeight()
            && p.getY() + p.getHeight() > paddle.getY()) {
                p.applyEffect(game);
                iterator.remove();
            } else if (p.getY() > Constants.SCREEN_HEIGHT) {
                iterator.remove();
            }
        }       
    }

    //CODE MỚI: Xử lý thời gian hiệu lực và xóa bỏ các PowerUp có thời gian.
    private void processActivePowerUps() {
        long currentTime = System.currentTimeMillis();
        // Lượng thời gian đã trôi qua kể từ lần update cuối cùng (tính bằng ms)
        long elapsedTime = currentTime - lastUpdateTime; 
        
        // Vòng lặp an toàn với Iterator
        Iterator<PowerUp> iterator = game.getActivePowerUpEffects().iterator();
        while (iterator.hasNext()) {
            PowerUp p = iterator.next();
            
            // Chỉ xử lý PowerUp có thời gian (duration > 0)
            if (p.getDuration() > 0) {
                // Giảm thời gian hiệu lực (đang tính bằng ms)
                p.setDuration(p.getDuration() - elapsedTime);

                // Nếu thời gian hiệu lực <= 0, xóa bỏ hiệu ứng
                if (p.getDuration() <= 0) {
                    p.removeEffect(game);
                    iterator.remove();
                }
            }
        }
        // Cập nhật thời gian cuối cùng cho lần lặp tiếp theo
        lastUpdateTime = currentTime; 
    }
}
