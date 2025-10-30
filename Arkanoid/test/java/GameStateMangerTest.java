import managers.*;
import engine.KeyboardManager;
import engine.MouseManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.AssetManager;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateMangerTest {
    private GameStateManager gsm;

    @BeforeEach
    void setUp() {
        KeyboardManager km = new KeyboardManager();
        MouseManager mm = new MouseManager();
        AssetManager.loadImages();
        gsm = new GameStateManager(km, mm);

        //AssetManager.loadImages();
    }

    @Test
    void testInitialValues() {
        assertEquals(1, gsm.getCurrentLevel());
        assertEquals(0, gsm.getScore());
        assertEquals(3, gsm.getLives());
    }

    @Test
    void testValidCurrentState() {
        gsm.setState("menu");
        assertInstanceOf(MenuState.class, gsm.getCurrentState());

        gsm.setState("game over");
        assertInstanceOf(GameOverState.class, gsm.getCurrentState());

        gsm.setState("victory");
        assertInstanceOf(VictoryState.class, gsm.getCurrentState());
    }

    @Test
    void testLoadLevelUpdatesCurrentLevel() {
        gsm.loadLevel(2);
        assertEquals(2, gsm.getCurrentLevel());
    }


    @Test
    void testLoadingFlagSetAndGet() {
        gsm.setLoadingGameFlag(true);
        assertTrue(gsm.isLoadingGame());
        gsm.setLoadingGameFlag(false);
        assertFalse(gsm.isLoadingGame());
    }

}
