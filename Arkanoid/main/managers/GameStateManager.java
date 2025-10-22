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
    private int numberOfLevel = 5;
    private boolean isLoadingGame = false; // <-- THÊM BIẾN NÀY
    // Thêm một biến boolean để đánh dấu xem chúng ta có đang trong quá trình load game hay không.

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

        // Nếu state hiện tại LÀ level VÀ state tiếp theo KHÔNG PHẢI level (ví dụ về menu, pause...)
        // thì reset cờ loading
        if (currentState instanceof LevelState2 && !stateName.equals("level")) {
            isLoadingGame = false; // Reset cờ khi thoát khỏi level state
        }
        
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
        int totalLevels = util.LevelData.getTotalLevels(); // Lấy tổng số level động
        if (levelNum > numberOfLevel) {
            setState("victory");
            return;
        }

        LevelState2 levelState = (LevelState2) states.get("level");
        if (levelState != null) {
            // **SỬA Ở ĐÂY:** Truyền score và lives hiện tại (đã được cập nhật khi load)
            levelState.initLevel(levelNum, this.score, this.lives); // Bỏ levelFileName, thêm score, lives

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

    // loadNextLevel giữ nguyên, vì nó lấy score/lives từ state hiện tại trước khi gọi loadLevel
    public void loadNextLevel() {
        if (currentState instanceof LevelState2) {
            // Get current level data before switching
            LevelState2 currentLevelState = (LevelState2) currentState;
            score = currentLevelState.getScore();
            lives = currentLevelState.getLives();
            System.out.println("Loading next level. Score: " + score + ", Lives: " + lives);
            currentLevel++;
            loadLevel(currentLevel);
        } else {
            System.err.println("Cannot load next level from non-LevelState2");
             setState("menu");
        } 
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


    public LevelState2 getCurrentLevelState() {
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

    // Dùng để MenuState cập nhật dữ liệu trước khi gọi loadLevel
    public void setCurrentLevelForLoad(int level) {
        this.currentLevel = (level > 0 && level <= numberOfLevel) ? level : 1;
    }

    public void setScoreForLoad(int score) {
        this.score = Math.max(0, score); // Đảm bảo điểm không âm
    }

    public void setLivesForLoad(int lives) {
        this.lives = Math.max(0, lives);
    }

    public boolean isLoadingGame() {
        return isLoadingGame;
    }
    public void setLoadingGameFlag(boolean isLoading) {
        this.isLoadingGame = isLoading;
    }

}