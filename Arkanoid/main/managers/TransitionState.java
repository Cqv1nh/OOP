package managers;

import util.AssetManager;
import util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;

import static util.RenderUtil.drawCenteredString; // DÒNG MỚI (ĐÚNG)

public class TransitionState extends GameState {
    // thuoc tinh km mm la cac thuoc tinh cua lop GameState
    // Lop con su dung ma khong can khai bao
    

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

        if (km.isKeyJustPressed(KeyEvent.VK_SPACE)) {
            km.clearAllKeys();
            manager.setState("level");
        }
    }

    @Override
    public void render(Graphics2D g) {
        Properties lang = manager.getLanguageProps();
        int level = manager.getCurrentLevel();

        g.drawImage(AssetManager.transitionBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);

        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        if (level == 1) {

            drawCenteredString(lang.getProperty("transition.level1_message", "ENTER LEVEL 1"),
                    Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, 0);
        } else {
            String msg = lang.getProperty("transition.message1", "LEVEL {0} PASSED");
            msg = msg.replace("{0}", String.valueOf(level - 1)); // handle {0} placeholder
            drawCenteredString(msg, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, 0);
        }

        g.setColor(Color.decode("#DD0303"));
        g.setFont(new Font("Arial", Font.BOLD, 24));
        drawCenteredString(lang.getProperty("transition.message2", "PRESS SPACE TO CONTINUE"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, 40);

    }
}
