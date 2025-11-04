package managers;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;
import util.AssetManager;
import util.Constants;
import static util.RenderUtil.drawCenteredString;

public class TransitionState extends GameState {
    /**
     * Constructor chuyển trạng thái giữa 2 màn game.
     *
     * @param manager Đối tượng quản lý trạng thái game chính.
     */
    public TransitionState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();
    }

    /**
     * Method enter.
     */
    @Override
    public void enter() {
    }

    /**
     * Method exit.
     */
    @Override
    public void exit() {
    }

    /**
     * Method cập nhật đối tượng.
     */
    @Override
    public void update() {
        if (km.isKeyJustPressed(KeyEvent.VK_SPACE)) {
            km.clearAllKeys();
            manager.setState("level");
        }
    }

    /**
     * Method vẽ ra màn hình.
     *
     * @param g đối tượng đồ họa.
     */
    @Override
    public void render(Graphics2D g) {
        Properties lang = manager.getLanguageProps();
        int level = manager.getCurrentLevel();

        g.drawImage(AssetManager.transitionBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);

        g.setColor(Color.decode("#FFFFE3"));
        g.setFont(new Font("Arial", Font.BOLD, 28));
        if (level == 1) {
            drawCenteredString(lang.getProperty("transition.level1_message", "ENTER LEVEL 1"),
                    Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, -80);
        } else {
            String msg = lang.getProperty("transition.message1", "LEVEL {0} PASSED");
            msg = msg.replace("{0}", String.valueOf(level - 1)); // handle {0} placeholder
            drawCenteredString(msg, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, -80);
        }

        g.setColor(Color.decode("#66CCFF"));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        drawCenteredString(lang.getProperty("transition.message2", "PRESS SPACE TO CONTINUE"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, 50);
    }
}
