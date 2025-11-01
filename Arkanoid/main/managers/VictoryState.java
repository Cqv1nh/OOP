package managers;

import util.AssetManager;
import util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;

import static util.RenderUtil.drawCenteredString; // DÒNG MỚI (ĐÚNG)

public class VictoryState extends GameState {

    private final Font FONT_TITLE = new Font("Arial", Font.BOLD, 35);
    private final Font FONT_SUBTITLE = new Font("Arial", Font.BOLD, 24);

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
        
        g.drawImage(AssetManager.transitionBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
        
        g.setColor(Color.CYAN);
        g.setFont(FONT_TITLE);
        drawCenteredString("CONGRATULATIONS", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, -20);

        g.setColor(Color.RED);
        g.setFont(FONT_SUBTITLE);
        drawCenteredString("PRESS R TO RETURN HOME", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, 30);

    }
}
