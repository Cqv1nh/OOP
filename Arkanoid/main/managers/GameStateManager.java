package managers;

import com.google.gson.Gson;
import engine.KeyboardManager;
import engine.MouseManager;
import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.awt.Point;
import java.util.HashSet;
import java.util.Properties;
import util.AudioManager;
import util.HighScoreManager;
import util.ScoreEntry;

public class GameStateManager {
    private GameState currentState;
    private Map<String, GameState> states;
    private KeyboardManager km;
    private MouseManager mm ;
    private String langCode;
    private Properties languageProps = new Properties();
    private int currentLevel;
    private int score;
    private int lives;
    private int numberOfLevel = 5;
    private boolean isLoadingGame = false;
    private LevelState lastLevelStateInstance;
    private long gameRunStartTime;
    private static final String SETTINGS_FILE = "settings.json";

    /**
     * Constructor khởi tạo về mọi thứ, tải các file âm thanh, setting, trạng thái.
     *
     * @param km quản lý bàn phím.
     * @param mm quản lý chuột.
     */
    public GameStateManager(KeyboardManager km, MouseManager mm) {
        states = new HashMap<>();
        
        AudioManager.loadSound("/resources/sounds/level_music_loop.wav", "background_music");
        AudioManager.loadSound("/resources/sounds/sfx_ball_hit.wav", "ball_hit");
        AudioManager.loadSound("/resources/sounds/sfx_ball_vs_paddle.wav", "sfx_ball_vs_paddle");
        AudioManager.loadSound("/resources/sounds/sfx_ball_vs_brick.wav", "sfx_ball_vs_brick");
        AudioManager.loadSound("/resources/sounds/sfx_power_up.wav", "sfx_power_up");
        AudioManager.loadSound("/resources/sounds/sfx_ball_vs_explosive_brick.wav", "sfx_ball_vs_explosive_brick");

        this.km = km;
        this.mm = mm;
        loadSettingsFromFile();

        // Initialize all states
        states.put("menu", new MenuState(this));
        states.put("level", new LevelState(this));
        states.put("pause", new PauseState(this));
        states.put("game over", new GameOverState(this));
        states.put("settings", new SettingsState(this));
        states.put("victory", new VictoryState(this));
        states.put("transition", new TransitionState(this));
        states.put("highscores", new HighScoreState(this));
        states.put("loadgame", new LoadGameState(this));

        // Initialize game data
        currentLevel = 1;
        score = 0;
        lives = 3;
    }

    /**
     * Chuyển đổi trạng thái game, đồng thời quản lý việc bật/tắt nhạc nền.
     *
     * @param stateName tên trạng thái.
     */
    public void setState(String stateName) {
        // Chỉ xử lý nếu stateName hợp lệ và khác state hiện tại.
        GameState nextState = states.get(stateName);
        if (nextState != null && nextState != currentState) {
            // Logic: Bật nhạc cho các trạng thái chờ/kết thúc.
            if (stateName.equals("menu") ||
                stateName.equals("game over") ||
                stateName.equals("victory") ||
                stateName.equals("transition") ||
                stateName.equals("settings") ||
                stateName.equals("highscores") ||
                stateName.equals("loadgame")) 
            {
                AudioManager.playBackgroundMusic("background_music");
            }
            // Tắt nhạc khi đang chơi hoặc tạm dừng.
            else if (stateName.equals("level") || stateName.equals("pause"))
            {
                AudioManager.stopBackgroundMusic();
            }
        }
        if (currentState instanceof LevelState) {
            // Lưu lại instance hiện tại trước khi chuyển state
            lastLevelStateInstance = (LevelState) currentState;
        }
        // Nếu state hiện tại LÀ level VÀ state tiếp theo KHÔNG PHẢI level (ví dụ về menu, pause...)
        // thì reset cờ loading
        if (currentState instanceof LevelState && !stateName.equals("level")) {
            isLoadingGame = false; // Reset cờ khi thoát khỏi level state
        }
        if (currentState != null) {
            currentState.exit();
        }
        currentState = states.get(stateName); // Lấy state tiếp theo
        if (currentState != null) {
            currentState.enter();
        } else {
            System.err.println("State not found: " + stateName);
        }
    }

    /**
     * Bắt đầu 1 màn chơi mới. Mạng = 3, màn chơi = 1.
     */
    public void startGame() {
        currentLevel = 1;
        score = 0;
        lives = 3;
        gameRunStartTime = System.currentTimeMillis();
        loadLevel(currentLevel);
    }

    /**
     * Gọi method update của bất kỳ logic nào đang chạy.
     */
    public void update() {
        if (currentState != null) {
            currentState.update();
        }
    }

    /**
     * Method tạm dừng game.
     */
    public void pauseGame() {
    }

    /**
     * Method vẽ ra màn hình.
     *
     * @param g
     */
    public void render(Graphics2D g) {
        if (currentState != null) {
            currentState.render(g);
        }
    }

    /**
     * Tải một màn chơi (LOAD GAME) với đầy đủ thông tin (điểm, mạng, gạch còn lại).
     *
     * @param levelNum level.
     * @param score điểm.
     * @param lives mạng
     * @param remainingBrickIndices gạch còn lại.
     */
    public void loadLevel(int levelNum, int score, int lives, HashSet<Point> remainingBrickIndices) {
        this.currentLevel = levelNum;
        this.score = score;
        this.lives = lives;
        int totalLevels = util.LevelData.getTotalLevels(); // Lấy tổng số level động
        if (levelNum > numberOfLevel) {
            // Ghi lại điểm cao khi thắng toàn bộ game
            // (this.score là điểm số cuối cùng, totalLevels là level cao nhất đã hoàn thành)
            System.out.println("Victory! Saving final score...");
            long totalPlayTime = System.currentTimeMillis() - this.gameRunStartTime;
            HighScoreManager.addScore(new ScoreEntry(this.score, totalPlayTime, totalLevels));

            setState("victory");
            return;
        }
        LevelState levelState = (LevelState) states.get("level");
        if (levelState != null) {
            // Truyền score và lives hiện tại (đã được cập nhật khi load)
            levelState.initLevel(levelNum, this.score, this.lives, remainingBrickIndices); // Bỏ levelFileName, thêm score, lives

            if (levelNum == totalLevels) {
                levelState.setLastLevel(true);
            } else {
                levelState.setLastLevel(false);
            }
            setState("transition"); // Vẫn qua transition để hiển thị màn hình chờ
        } else {
            System.err.println("LevelState2 ('level') not found!");
        }
    }

    /**
     * Method loadLevel cho New Game / Next Level (truyền null cho brick indices).
     *
     * @param levelNum số level hiện tại.
     */
    public void loadLevel(int levelNum) {
        // Lấy score và lives hiện tại nếu là next level
        // Sử dụng score và lives hiện tại của GameStateManager.
        // Khi startGame() gọi: score=0, lives=3.
        // Khi loadGame() gọi hàm loadLevel đầy đủ: score/lives đã được set từ file.
        // Khi loadNextLevel() gọi: score/lives đã được lấy từ state trước.
        int currentScore = this.score;
        int currentLives = this.lives;
        // Gọi hàm đầy đủ với remainingBrickIndices là null
        loadLevel(levelNum, currentScore, currentLives, null);
    }


    /**
     * Lấy điểm từ màn hiện tại, reset mạng về 3, tăng màn lên 1, gọi loadLevel() tải màn tiếp theo.
     */
    public void loadNextLevel() {
        if (currentState instanceof LevelState) {
            // Get current level data before switching
            LevelState currentLevelState = (LevelState) currentState;
            this.score = currentLevelState.getScore();
            // this.lives = currentLevelState.getLives(); // 
            // LUÔN đặt lại số mạng thành 3 cho màn mới:
            this.lives = 3;
            System.out.println("Loading next level. Score: " + score + ", Lives: " + lives);
            currentLevel++; // Tăng level
            loadLevel(currentLevel);
        } else {
            System.err.println("Cannot load next level from non-LevelState2");
            setState("menu");
        } 
    }

    /**
     * Đọc file settings.json để tải các cài đặt (âm lượng, ngôn ngữ, phím điều khiển) vào game.
     */
    private void loadSettingsFromFile() {
        Path path = Paths.get(SETTINGS_FILE);
        if (Files.exists(path)) {
            try {
                String json = new String(Files.readAllBytes(path));
                Gson gson = new Gson();
                Settings settings = gson.fromJson(json, Settings.class);

                AudioManager.setMasterVolume((float) settings.getMasterVolume() / 100);
                AudioManager.setSoundFxVolume((float) settings.getSoundEffectsVolume() / 100);
                AudioManager.setBackgroundMusicVolume((float) settings.getBackGroundMusic() / 100);
                langCode = settings.getLanguage();

                km.setMoveLeftPrimary(settings.getMoveLeftPrimary());
                km.setMoveLeftSecondary(settings.getMoveLeftSecondary());
                km.setMoveRightPrimary(settings.getMoveRightPrimary());
                km.setMoveRightSecondary(settings.getMoveRightSecondary());

                System.out.println("Settings loaded from " + SETTINGS_FILE);
            } catch (IOException e) {
                System.err.println("Error reading settings: " + e.getMessage());
            }
        } else {
            System.out.println("Settings file not found, using default values.");
        }
    }

    /**
     * Getter lấy giai đoạn hiện tại.
     *
     * @return giai đoạn hiện tại.
     */
    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * Getter lấy level hiện tại.
     *
     * @return level.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Getter lấy điểm.
     *
     * @return điểm.
     */
    public int getScore() {
        // If current state is a LevelState2, prefer its live score (keeps HUD and other callers in sync)
        if (currentState instanceof LevelState) {
            return ((LevelState) currentState).getScore();
        }
        return score;
    }

    /**
     * Getter lấy số mạng còn lại.
     *
     * @return số mạng còn lại.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Lấy level hiện tại.
     *
     * @return level.
     */
    public LevelState getCurrentLevelState() {
        if (currentState instanceof LevelState) {
            return (LevelState) currentState;
        }
        return null;
    }

    /**
     * Getter trình quản lý chuột.
     *
     * @return chuột.
     */
    public MouseManager getMm() {
        return mm;
    }

    /**
     * Getter trình quản lý phím.
     *
     * @return phím.
     */
    public KeyboardManager getKm() {
        return km;
    }

    /**
     * Setter trình quản lý chuột.
     *
     * @param mm chuột.
     */
    public void setMm(MouseManager mm) {
        this.mm = mm;
    }

    /**
     * Setter trình quản lý phím.
     *
     * @param km phím.
     */
    public void setKm(KeyboardManager km) {
        this.km = km;
    }

    /**
     * Getter có đang load game không?
     *
     * @return T or F.
     */
    public boolean isLoadingGame() {
        return isLoadingGame;
    }

    /**
     * Setter cờ báo hiệu load game.
     *
     * @param isLoading đang load.
     */
    public void setLoadingGameFlag(boolean isLoading) {
        this.isLoadingGame = isLoading;
    }

    /**
     * Getter LevelState2 gần đây nhất (dùng cho PauseState để vẽ nền mờ).
     *
     * @return level gần nhất.
     */
    public LevelState getLastLevelStateInstance() {
        return lastLevelStateInstance;
    }

    /**
     * Getter mốc thời gian bắt đầu chơi.
     *
     * @return thời gian.
     */
    public long getGameRunStartTime() {
        return gameRunStartTime;
    }

    /**
     * Setter thời gian bắt đầu chơi game.
     *
     * @param time thời gian.
     */
    public void setGameRunStartTime(long time) {
         this.gameRunStartTime = time;
    }

    /**
     * Getter mã ngôn ngữ.
     *
     * @return mã ngôn ngữ.
     */
    public String getLangCode() {
        return langCode;
    }

    /**
     * Setter mã ngôn ngữ.
     *
     * @param langCode mã ngôn ngữ.
     */
    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    /**
     * Getter file chứa văn bản đã dịch.
     *
     * @return văn bản.
     */
    public Properties getLanguageProps() {
        return languageProps;
    }

    /**
     * Setter file văn bản đã dịch.
     *
     * @param languageProps file văn bản.
     */
    public void setLanguageProps(Properties languageProps) {
        this.languageProps = languageProps;
    }
}