package managers;

import util.AssetManager;
import util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;

import static util.RenderUtil.drawCenteredString; // DÒNG MỚI (ĐÚNG)

public class TransitionState extends GameState {
    // thuoc tinh km mm la cac thuoc tinh cua lop GameState
    // Lop con su dung ma khong can khai bao
    private final Font FONT_TITLE = new Font("Arial", Font.BOLD, 36);
    private final Font FONT_SUBTITLE = new Font("Arial", Font.BOLD, 24);

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
        int level = manager.getCurrentLevel();

        g.drawImage(AssetManager.transitionBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
        String stringText; // Lay chuoi de in
        if (level == 1) {
            stringText = "ENTER LEVEL 1";
        } else {
            stringText = String.format("LEVEL %d PASSED", level - 1);
        }

        g.setColor(Color.CYAN);
        g.setFont(FONT_TITLE);
        drawCenteredString(stringText, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, -20);

        g.setColor(Color.RED);
        g.setFont(FONT_SUBTITLE);
        drawCenteredString("PRESS SPACE TO CONTINUE", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, 30);
    }
}
