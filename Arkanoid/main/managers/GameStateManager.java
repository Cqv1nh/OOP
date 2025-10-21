package managers;

import engine.KeybroadManager;
import engine.MouseManager;
import util.AudioManager;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;


public class GameStateManager {
    private GameState currentState;
    private Map<String, GameState> states;
    private KeybroadManager km;
    private MouseManager mm ;

    // Game data shared across states
    private int currentLevel;
    private int score;
    private int lives;
    private String[] levelFiles;
    private int numberOfLevel = 5;

    public GameStateManager(KeybroadManager km, MouseManager mm) {
        states = new HashMap<>();

        AudioManager.loadSound("/resources/sounds/level_music_loop.wav", "background_music");
        AudioManager.loadSound("/resources/sounds/sfx_ball_hit.wav", "ball_hit");

        // Đảm bảo đóng tài nguyên âm thanh khi cửa sổ đóng
        // addWindowListener(new java.awt.event.WindowAdapter() {
        //     @Override
        //     public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        //         AudioManager.closeAllSounds();
        //     }
        // });
        // Thinh sua loi
        // Kích hoạt nhạc nền ngay khi game khởi động (vì trạng thái ban đầu là GAMESTART)
        // AudioManager.playBackgroundMusic("background_music"); // <-- XÓA DÒNG NÀY

        this.km = km;
        this.mm = mm;

        // Initialize all states
        states.put("menu", new MenuState(this));
        states.put("level", new LevelState2(this));
        states.put("pause", new PauseState(this));
        states.put("game over", new GameOverState(this));
        //states.put("settings", new SettingsState(this));
        states.put("victory", new VictoryState(this));
        states.put("transition", new TransitionState(this));

        // Initialize game data
        currentLevel = 1;
        score = 0;
        lives = 3;
        levelFiles = new String[] {
                "main/resources/level1.txt",
                "main/resources/level2.txt",
                "main/resources/level3.txt",
                "main/resources/level4.txt",
                "main/resources/level5.txt"
        };


    }

    public void setState(String stateName) {
        // === THÊM LOGIC NHẠC NỀN VÀO ĐÂY ===
        // Chỉ xử lý nếu stateName hợp lệ và khác state hiện tại (tránh bật/tắt liên tục)
        GameState nextState = states.get(stateName);
        if (nextState != null && nextState != currentState) {
            // Logic: Bật nhạc cho các trạng thái chờ/kết thúc
            if (stateName.equals("menu") ||
                stateName.equals("game over") ||
                stateName.equals("victory") ||
                stateName.equals("transition")) // Transition cũng có nhạc nền? Nếu không thì bỏ đi
            {
                AudioManager.playBackgroundMusic("background_music");
            }
            // Logic: Tắt nhạc khi đang chơi hoặc tạm dừng
            else if (stateName.equals("level") || stateName.equals("pause"))
            {
                AudioManager.stopBackgroundMusic();
            }
        }
        // === KẾT THÚC LOGIC NHẠC NỀN ===
        if (currentState != null) {
            currentState.exit();
        }

        currentState = states.get(stateName);

        if (currentState != null) {
            currentState.enter();
        } else {
            System.err.println("State not found: " + stateName);
        }
    }

    public void startGame() {
        currentLevel = 1;
        score = 0;
        lives = 3;
        loadLevel(currentLevel);
    }

    

    public void update() {
        if (currentState != null) {
            currentState.update();
        }
    }

    public void pauseGame() {

    }

    public void render(Graphics2D g) {
        if (currentState != null) {
            currentState.render(g);
        }
    }


    //V2.
    public void loadLevel(int levelNum) {
        if (levelNum > numberOfLevel) {
            setState("victory");
            return;
        }

        LevelState2 levelState2 = (LevelState2) states.get("level");
        levelState2.initLevel(levelNum, score, lives);

        if (levelNum == levelFiles.length) {
            levelState2.setLastLevel(true);
        }

        //setState("level");
        setState("transition");
    }

    public void loadNextLevel() {
        // Get current level data before switching
        LevelState2 levelState2 = (LevelState2) states.get("level");
        score = levelState2.getScore();
        lives = levelState2.getLives();

        currentLevel++;
        loadLevel(currentLevel);
    }


    public GameState getCurrentState() {
        return currentState;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }


    public LevelState2 getLevelState() {
        if (currentState instanceof LevelState2) {
            return (LevelState2) currentState;
        }
        return null;
    }

    public MouseManager getMm() {
        return mm;
    }

    public KeybroadManager getKm() {
        return km;
    }

    public void setMm(MouseManager mm) {
        this.mm = mm;
    }

    public void setKm(KeybroadManager km) {
        this.km = km;
    }
}