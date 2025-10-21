package entities;
import managers.LevelState2;
import util.Constants;
public class ExtraLifePowerUp extends PowerUp {
    private static final double INSTANT_DURATION = 0.0;

    public ExtraLifePowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, "EXTRA_LIFE", INSTANT_DURATION);
    }

    //Áp dụng hiệu ứng thêm 1 mạng.

    //V2.
    @Override
    public void applyEffect(LevelState2 game) {
        game.addLife();
    }

    @Override
    public void removeEffect(LevelState2 game) {

    }

}

