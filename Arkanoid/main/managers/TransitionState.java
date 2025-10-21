package managers;

import util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;

import static util.RenderUtil.drawCenteredString; // DÒNG MỚI (ĐÚNG)

public class TransitionState extends GameState {

    public TransitionState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();
    }

    @Override
    public void enter() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update() {
        km.update();

        if (km.getKey(KeyEvent.VK_SPACE)) {
            km.clearAllKeys();
            manager.setState("level");
        }
    }

    @Override
    public void render(Graphics2D g) {
        int level = manager.getCurrentLevel();

        g.setColor(Color.decode("#FAB12F"));
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.decode("#DD0303"));
        g.setFont(new Font("Arial", Font.BOLD, 30));

        if (level == 1) {
            drawCenteredString("ENTER LEVEL 1", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                    g, 0, 0);
        } else {
            drawCenteredString(String.format("LEVEL %d PASSED", level - 1),
                    Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                    g, 0, 0);
        }

        g.setColor(Color.decode("#DD0303"));
        g.setFont(new Font("Arial", Font.BOLD, 24));
        drawCenteredString("PRESS SPACE TO CONTINUE", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 0, 40);
    }
}
