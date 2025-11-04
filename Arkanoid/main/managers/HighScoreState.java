package managers;

import entities.Button;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import util.Constants;
import util.HighScoreManager;
import util.ScoreEntry;
import util.RenderUtil;
import util.AssetManager;

public class HighScoreState extends GameState {
    private ArrayList<ScoreEntry> scores;
    private Button backButton;
    private final Font FONT_TITLE = new Font("Arial",Font.BOLD,50);
    private final Font FONT_HEADER = new Font("Arial", Font.BOLD, 22);
    private final Font FONT_ENTRY = new Font("Arial", Font.BOLD, 20);

    /**
     * Constructor.
     *
     * @param manager Đối tượng quản lý trạng thái game chính.
     */
    public HighScoreState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();
        backButton = new Button(300, 500, 200, 50, "Back to Menu",
                "Back to Menu", null, null);
    }

    /**
     * Method tải điểm cao mỗi khi vào màn hình.
     */
    @Override
    public void enter() {
        scores = HighScoreManager.loadHighScores();
        System.out.println("Loaded " + scores.size() + " high scores.");
    }

    /**
     * Method exit.
     */
    @Override
    public void exit() {
    }

    /**
     * Method cập nhật trạng thái.
     */
    @Override
    public void update() {
        backButton.setHoveringState(backButton.isHovering(mm.getMouseX(), mm.getMouseY()));
        if (backButton.isHovering(mm.getMouseX(), mm.getMouseY())) {
            if (mm.isLeftJustPressed()) {
                manager.setState("menu");
            }
            return;
        }
        if (km.isKeyJustPressed(KeyEvent.VK_ESCAPE)) {
            manager.setState("menu");
            return;
        }
    }

    /**
     * Vẽ ra màn hình.
     *
     * @param g Đối tượng đồ họa Java.
     */
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
        g.drawImage(AssetManager.menuBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
        g.setColor(Color.YELLOW);
        g.setFont(FONT_TITLE);
        RenderUtil.drawCenteredString(languageProps.getProperty("highScore.title", "HIGH SCORE"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, -220);

        // Vẽ tiêu đề cột
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

        // Vẽ danh sách điểm
        g.setFont(FONT_ENTRY);
        g.setColor(Color.WHITE); 
        int yPos = yStart + 50;

        for (int i = 0 ; i < scores.size(); i++) {
            ScoreEntry entry = scores.get(i);
            String timeFormatted = formatPlayTime(entry.getPlayTimeMillis());
            RenderUtil.drawCenteredStringInColumn(g, String.valueOf(i + 1) + ".", colRankStart, colRankWidth, yPos);
            RenderUtil.drawCenteredStringInColumn(g, String.valueOf(entry.getScore()), colScoreStart, colScoreWidth, yPos);
            RenderUtil.drawCenteredStringInColumn(g, String.valueOf(entry.getHighestLevel()), colLevelStart, colLevelWidth, yPos);
            RenderUtil.drawCenteredStringInColumn(g, timeFormatted, colTimeStart, colTimeWidth, yPos);
            yPos += 40;
        }
        backButton.setText(languageProps.getProperty("highScore.back", "RETURN MENU"));
        backButton.draw(g);
    }

    /**
     * Method định dạng thời gian.
     *
     * @param millis ms.
     * @return dạng phút: giây.
     */
    private String formatPlayTime(long millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes, seconds);
    }
}
