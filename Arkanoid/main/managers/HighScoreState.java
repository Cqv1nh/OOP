package managers;

import entities.Button;
import util.Constants;
import util.HighScoreManager;
import util.ScoreEntry;
import util.RenderUtil;
import util.AssetManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit; // Dùng để format thời gian

public class HighScoreState extends GameState {
    private ArrayList<ScoreEntry> scores;
    private Button backButton; // Nut tro ve menu

    private final Font FONT_TITLE = new Font("Arial",Font.BOLD,50);
    private final Font FONT_HEADER = new Font("Arial", Font.BOLD, 22);
    private final Font FONT_ENTRY = new Font("Arial", Font.BOLD, 20);
    public HighScoreState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

        backButton = new Button(Constants.SCREEN_WIDTH / 2 - 100, 400, 200, 50, "Back to Menu");
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
        backButton.setHoveringState(backButton.isHovering(mm.getMouseX(), mm.getMouseY()));
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
        // 1. Vẽ nền
        g.drawImage(AssetManager.menuBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);

        // Ve tieu de
        g.setColor(Color.YELLOW);
        g.setFont(FONT_TITLE);
        RenderUtil.drawCenteredString("HIGH SCORES", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, -220);

        // 3. Vẽ Tiêu đề cột
        g.setFont(FONT_HEADER);
        g.setColor(Color.YELLOW);
        int yStart = 150;
        int colRankStart = 80;    // Cột STT bắt đầu ở X=80
        int colRankWidth = 80;    // Cột STT rộng 80px

        int colScoreStart = 160;  // Cột SCORE bắt đầu ở X=160
        int colScoreWidth = 200;  // Cột SCORE rộng 200px
        
        int colLevelStart = 360;  // Cột LEVEL bắt đầu ở X=360
        int colLevelWidth = 150;  // Cột LEVEL rộng 150px

        int colTimeStart = 510;   // Cột TIME bắt đầu ở X=510
        int colTimeWidth = 200;   // Cột TIME rộng 200px

        RenderUtil.drawCenteredStringInColumn(g, "STT", colRankStart, colRankWidth, yStart);
        RenderUtil.drawCenteredStringInColumn(g, "SCORE", colScoreStart, colScoreWidth, yStart);
        RenderUtil.drawCenteredStringInColumn(g, "MAX LEVEL", colLevelStart, colLevelWidth, yStart);
        RenderUtil.drawCenteredStringInColumn(g, "TOTAL TIME", colTimeStart, colTimeWidth, yStart);

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
