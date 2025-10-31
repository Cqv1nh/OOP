package managers;

import entities.Button;
import util.Constants;
import util.HighScoreManager;
import util.ScoreEntry;
import util.RenderUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit; // Dùng để format thời gian

public class HighScoreState extends GameState {
    private ArrayList<ScoreEntry> scores;
    private Button backButton; // Nut tro ve menu

    private final Font FONT_TITLE = new Font("Arial",Font.BOLD,48);
    private final Font FONT_HEADER = new Font("Arial", Font.BOLD, 22);
    private final Font FONT_ENTRY = new Font("Arial", Font.BOLD, 20);
    public HighScoreState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

        backButton = new Button(300, 500, 200, 50, "Back to Menu",
                "Back to Menu", null, null);
    }

    @Override
    public void enter() {
        // Tải điểm cao mỗi khi vào màn hình này
        scores = HighScoreManager.loadHighScores();
        System.out.println("Loaded " + scores.size() + " high scores.");
    }

    @Override
    public void exit() {

    }

    @Override
    public void update() {
        // Kiểm tra click nút "Back"
        if (backButton.isHovering(mm.getMouseX(), mm.getMouseY())) {
            if (mm.isLeftJustPressed()) {
                manager.setState("menu");
            }
            return;
        }
        // Kiểm tra phím ESC để quay lại
        if (km.isKeyJustPressed(KeyEvent.VK_ESCAPE)) {
            manager.setState("menu");
            return;
        }
    }

    @Override
    public void render(Graphics2D g) {
        Properties languageProps = manager.getLanguageProps();
        languageProps.clear();

        String fileName = "language/lang_" + manager.getLangCode() + ".properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.err.println("Language file not found: " + fileName);
                return;
            }

            languageProps.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.setColor(Color.decode("#2C3E50"));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // Ve tieu de
        g.setColor(Color.WHITE);
        g.setFont(FONT_TITLE);
        RenderUtil.drawCenteredString(languageProps.getProperty("highScore.title", "HIGH SCORE"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, -220);

        // 3. Vẽ Tiêu đề cột
        g.setFont(FONT_HEADER);
        g.setColor(Color.YELLOW);
        int yStart = 150;
        int colRankStart = 60;    // Cột STT bắt đầu ở X=80
        int colRankWidth = 60;    // Cột STT rộng 80px

        int colScoreStart = 120;  // Cột SCORE bắt đầu ở X=160
        int colScoreWidth = 200;  // Cột SCORE rộng 200px
        
        int colLevelStart = 320;  // Cột LEVEL bắt đầu ở X=360
        int colLevelWidth = 210;  // Cột LEVEL rộng 150px

        int colTimeStart = 530;   // Cột TIME bắt đầu ở X=510
        int colTimeWidth = 200;   // Cột TIME rộng 200px

        RenderUtil.drawCenteredStringInColumn(g, "#", colRankStart, colRankWidth, yStart);
        RenderUtil.drawCenteredStringInColumn(g, languageProps.getProperty("highScore.score", "SCORE"),
                colScoreStart, colScoreWidth, yStart);
        RenderUtil.drawCenteredStringInColumn(g, languageProps.getProperty("highScore.maxLevel","MAX LEVEL"),
                colLevelStart, colLevelWidth, yStart);
        RenderUtil.drawCenteredStringInColumn(g, languageProps.getProperty("highScore.totalTime",
                        "TOTAL TIME"), colTimeStart, colTimeWidth, yStart);

        g.drawLine(colRankStart - 10, yStart + 10, colTimeStart + colTimeWidth - 10, yStart + 10);

        // 4. Vẽ danh sách điểm
        g.setFont(FONT_ENTRY);
        g.setColor(Color.WHITE);
        int yPos = yStart + 50;

        // Xác định độ rộng cho từng cột

        for (int i = 0 ; i < scores.size(); i++) {
            ScoreEntry entry = scores.get(i);

            // Format thời gian
            String timeFormatted = formatPlayTime(entry.getPlayTimeMillis());

            RenderUtil.drawCenteredStringInColumn(g, String.valueOf(i + 1) + ".", colRankStart, colRankWidth, yPos);
            RenderUtil.drawCenteredStringInColumn(g, String.valueOf(entry.getScore()), colScoreStart, colScoreWidth, yPos);
            RenderUtil.drawCenteredStringInColumn(g, String.valueOf(entry.getHighestLevel()), colLevelStart, colLevelWidth, yPos);
            RenderUtil.drawCenteredStringInColumn(g, timeFormatted, colTimeStart, colTimeWidth, yPos);

            yPos += 40; // Xuong dong
        }
        backButton.setText(languageProps.getProperty("highScore.back", "RETURN MENU"));
        backButton.draw(g);
    }
    
    // Hàm tiện ích format thời gian
    private String formatPlayTime(long millis) {
        // Chuyển mili giây sang Phút:Giây
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes);

        // Định dạng thành "MM:SS"
        return String.format("%02d:%02d", minutes, seconds);
    }
}
