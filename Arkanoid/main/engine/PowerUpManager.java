package engine;

import java.util.Iterator;

import entities.*;
import managers.LevelState2;
import util.Constants;

public class PowerUpManager {

    private long lastUpdateTime = System.currentTimeMillis();
    

    // Được gọi bởi CollisionHandler khi một viên gạch bị phá

    //CODE MỚI: Xử lý thời gian hiệu lực và xóa bỏ các PowerUp có thời gian.

    //V2,
    public void update(LevelState2 game, int panelHeight) {
        // Truyen panelHeight xuong tiep
        updateFallingPowerUps(game, panelHeight);
        processActivePowerUps(game);
    }

    private void updateFallingPowerUps(LevelState2 game, int panelHeight) {
        // Dung itertator de xoa an toan
        Paddle paddle = game.getPaddle();
        Iterator<PowerUp> iterator = game.getPowerUps().iterator();
        while (iterator.hasNext()) {
            PowerUp p = iterator.next(); // Lay phan tu tiep theo
            p.setY(p.getY() + p.getFallSpeed());    // Power-up di chuyển xuống dưới
            //Kiểm tra va chạm với paddle
            if (p.getX() < paddle.getX() + paddle.getWidth()
                    && p.getX() + p.getWidth() > paddle.getX()
                    && p.getY() < paddle.getY() + paddle.getHeight()
                    && p.getY() + p.getHeight() > paddle.getY()) {
                p.applyEffect(game);
                iterator.remove();
            } else if (p.getY() > panelHeight) {
                // Thay the bang gamePanel.getHeight
                iterator.remove(); // Xóa khi rơi ra ngoài màn hình
            }
        }
    }

    private void processActivePowerUps(LevelState2 game) {
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

    //V2.
    public void spawnPowerUp(LevelState2 game, Brick brick) {
        String type = brick.getPowerUpType();
        PowerUp newPowerUp = null;

        double x = brick.getX() + brick.getWidth() / 2 - Constants.POWERUP_WIDTH / 2;
        double y = brick.getY();
        // Dung switch de them cac powerup khac trong tuong lai
        switch (type) {
            case "EXTRA_LIFE":
                newPowerUp = new ExtraLifePowerUp(x, y,
                        Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, 0.0);
                break;
            case "FAST_BALL":
                newPowerUp = new FastBallPowerUp(x, y,
                        Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT,
                        (double) Constants.POWERUP_DURATION);
                // Hiệu ứng kéo dài 7 giây
                break;
            case "EXPAND_PADDLE":
                newPowerUp = new ExpandPaddlePowerUp(x, y,
                        Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT,
                        (double) Constants.POWERUP_DURATION);
                break;
            case "MULTI_BALL":
                newPowerUp = new MultiBallPowerUp(x, y,
                        Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT,
                        0.0);
                break;
        }

        if (newPowerUp != null) {
            game.getPowerUps().add(newPowerUp);
        }
    }
}
