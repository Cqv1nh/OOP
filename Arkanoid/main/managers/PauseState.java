package managers;

import util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;

import static ui.GamePanel.drawCenteredString;

public class PauseState extends GameState {

    public PauseState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();


    }

    @Override
    public void enter() {

    }

    @Override
    public void exit() {
        km.clearAllKeys();
    }

    @Override
    public void update() {
        km.update();

        if (km.getKey(KeyEvent.VK_R) || km.getKey(KeyEvent.VK_ESCAPE)) {
            manager.setState("level");
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.decode("#FAB12F"));
        g.fillRect(100, 75, 600, 450);

        g.setColor(Color.decode("#DD0303"));
        g.setFont(new Font("Arial", Font.BOLD, 24));

        drawCenteredString("GAME PAUSE", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 100, 75);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        drawCenteredString("PRESS R TO RETURN HOME", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 100, 40 + 75);
    }
}
