package managers;

import util.AssetManager;
import util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.util.Properties;

import static util.RenderUtil.drawCenteredString; // DÒNG MỚI (ĐÚNG)

public class VictoryState extends GameState {

   
    public VictoryState(GameStateManager manager) {
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

        if (km.isKeyJustPressed(KeyEvent.VK_R)) {
            manager.setState("menu");
        }
    }

    @Override
    public void render(Graphics2D g) {

        Properties languageProps = manager.getLanguageProps();

        g.drawImage(AssetManager.transitionBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);

        g.setColor(Color.decode("#F8DE7E"));
        g.setFont(new Font("Arial", Font.BOLD, 36));

        drawCenteredString(languageProps.getProperty("victory.message1", "CONGRATULATIONS!"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 0, -80);

        g.setColor(Color.decode("#66CCFF"));
        g.setFont(new Font("Arial", Font.BOLD, 24));

        drawCenteredString(languageProps.getProperty("victory.message2", "PRESS R TO RETURN HOME"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 0, 50);

    }
}
