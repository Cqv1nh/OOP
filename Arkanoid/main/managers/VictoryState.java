package managers;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;
import util.AssetManager;
import util.Constants;
import static util.RenderUtil.drawCenteredString;

public class VictoryState extends GameState {
    /**
     * Constructor trạng thái chiến thắng.
     *
     * @param manager
     */
    public VictoryState(GameStateManager manager) {
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
        if (km.isKeyJustPressed(KeyEvent.VK_R)) {
            manager.setState("menu");
        }
    }

    /**
     * Method vẽ ra màn hình.
     *
     * @param g
     */
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
