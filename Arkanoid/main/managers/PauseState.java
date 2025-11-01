package managers;

import util.AssetManager;
import util.Constants;
import util.GameStateData;
import entities.Button; // Import Button

// Cần Point
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream; // Import để ghi file
import java.io.IOException;     // Import để xử lý lỗi file
import java.io.ObjectOutputStream; // Import để ghi đối tượng
import java.util.ArrayList; // Import ArrayList
import java.util.List;     // Import List
import java.util.HashSet;
import java.util.Properties;

import entities.Brick;

import static util.RenderUtil.drawCenteredString; // DÒNG MỚI (ĐÚNG)

public class PauseState extends GameState {

    private List<Button> buttons; // Them danh sach button
    // Kích thước và vị trí của khung Pause
    private final int FRAME_X = 200;
    private final int FRAME_Y = 100;
    private final int FRAME_WIDTH = 400;
    private final int FRAME_HEIGHT = 450; // Tăng chiều cao khung để chứa nhiều nút hơn

    public PauseState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();
        
        // Them button
        buttons = new ArrayList<>();

        // Căn giữa các nút trong khung pause
        int buttonWidth = 200;
        int buttonHeight = 50;
        int startButtonX = FRAME_X + (FRAME_WIDTH - buttonWidth) / 2;
        int currentButtonY = FRAME_Y + 70; // Vị trí Y bắt đầu cho nút đầu tiên (Resume)
        int spacing = 60; // Khoảng cách giữa các nút

        // Resume Button 
        buttons.add(new Button(startButtonX, currentButtonY, buttonWidth, buttonHeight,
                "Resume", "Resume", null ,null));
        // Nút Save and Quit (lưu và về menu)
        // Them cac nut slot 1 2 3 va nut chuyen ve menu 
        currentButtonY += spacing;
        buttons.add(new Button(startButtonX, currentButtonY, buttonWidth, buttonHeight,
                "Save Slot 1", "Save Slot 1", null, null));

        currentButtonY += spacing;
        buttons.add(new Button(startButtonX, currentButtonY, buttonWidth, buttonHeight,
                "Save Slot 2", "Save Slot 2", null, null));

        currentButtonY += spacing;
        buttons.add(new Button(startButtonX, currentButtonY, buttonWidth, buttonHeight,
                "Save Slot 3", "Save Slot 3", null, null));
        
        currentButtonY += spacing; 
        buttons.add(new Button(startButtonX, currentButtonY, buttonWidth, buttonHeight,
                "Back to Menu", "Back to Menu", null, null));

    }

    @Override
    public void enter() {
        Properties languageProps = manager.getLanguageProps();
        for (int i = 1; i <= 3; i++) {
            String msg = languageProps.getProperty("pause.slot", "Save Slot {0}");
            msg = msg.replace("{0}", String.valueOf(i)); // handle {0} placeholder
            buttons.get(i).setText(msg);
        }

        buttons.get(0).setText(languageProps.getProperty("pause.resume", "Resume"));
        buttons.getLast().setText(languageProps.getProperty("pause.back", "Return Menu"));
    }
    
    @Override
    public void exit() {
        km.clearAllKeys();
    }

    @Override
    public void update() {
        // Nhấn ESC hoặc R để Resume (quay lại game)
        if (km.isKeyJustPressed(KeyEvent.VK_R) || km.isKeyJustPressed(KeyEvent.VK_ESCAPE)) {
            manager.setState("level"); // Quay lại LevelState2
            return;
        }

        // Them xu ly click Button
        for (Button button : buttons) {
            button.setHoveringState(button.isHovering(mm.getMouseX(), mm.getMouseY()));
            if (button.isHovering(mm.getMouseX(), mm.getMouseY())) {
                if (mm.isLeftJustPressed()) {
                    handleButtonClick(button.getFunction());
                    return; // Thoát sớm sau khi xử lý click
                }
            }
        }

    }

    @Override
    public void render(Graphics2D g) {

        Properties languageProps = manager.getLanguageProps();

        // --- Vẽ nền mờ (Tùy chọn) ---

        // Lấy state level hiện tại để vẽ nó phía sau (nếu muốn hiệu ứng mờ)
        LevelState2 levelState = manager.getCurrentLevelState();
        if (levelState != null) {
            levelState.render(g); // Vẽ lại màn hình game
        }
    
        g.drawImage(AssetManager.menuBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);

        // Vẽ chữ "GAME PAUSED"
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        // điều chỉnh Y offset cho phù hợp với khung mới
        drawCenteredString(languageProps.getProperty("pause.message", "GAME PAUSED"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, -220); // Offset trong khung

        // === VẼ CÁC BUTTON ===
        for (Button button : buttons) {
            button.draw(g); // Vẽ từng button
        }
        // === KẾT THÚC VẼ ===

        // Vẽ hướng dẫn nhỏ ở dưới 
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        drawCenteredString(languageProps.getProperty("pause.instruction" , "Press ESC or R to Resume"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, 200);
    }

    // Ham xu ly click
    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "Resume":
                manager.setState("level"); // Quay lại game
                break;
            case "Save Slot 1":
                saveGame(1); // Gọi hàm save với số slot
                manager.setState("menu");
                break;
            case "Save Slot 2":
                saveGame(2); // Gọi hàm save với số slot
                manager.setState("menu");
                break;
            case "Save Slot 3":
                saveGame(3); // Gọi hàm save với số slot
                manager.setState("menu");
                break;
            case "Back to Menu":
                manager.setState("menu"); // Quay về menu chính
                break;
            default:
                System.err.println("Unknown pause button: " + buttonText);
                break;
        }
    }


    // === THÊM HÀM LƯU GAME ===
    // Co them chi so luu danh cho 3 file luu
    // loadGame nam trong LoadGameState
    private void saveGame(int slotNumber) {
        LevelState2 lastActiveLevelState = manager.getLastLevelStateInstance(); // Lấy state level hiện tại
        if (lastActiveLevelState != null) {
            int level = manager.getCurrentLevel();
            int score = lastActiveLevelState.getScore(); // <-- LẤY TỪ lastActiveLevelState
            int lives = lastActiveLevelState.getLives();

            // Lấy danh sách gạch còn lại
            HashSet<Point> remainingBrickIndices = new HashSet<>();
            ArrayList<Brick> currentBricks = lastActiveLevelState.getBricks();

            // Lấy layout gốc để biết vị trí tương ứng
            int[][] originalLayout = util.LevelData.getLayoutForLevel(level);
            if (originalLayout != null) {
                // Tính toán startX, startY giống như trong BrickManager để so sánh vị trí
                int numRows = originalLayout.length;
                int numCols = originalLayout[0].length;
                double totalBricksWidth = numCols * Constants.BRICK_WIDTH + ((numCols) - 1) * 5;
                double startX = (Constants.SCREEN_WIDTH - totalBricksWidth) / 2;
                double startY = 60; // Vi tri bat dau theo truc y

                // Duyệt qua layout gốc
                for (int r = 0; r < numRows; r++) {
                    for (int c = 0; c < numCols; c++) {
                        if (originalLayout[r][c] > 0) { // Nếu vị trí này có gạch trong layout gốc
                            // Tính tọa độ pixel dự kiến của gạch này
                            double expectedX = startX + c * (Constants.BRICK_WIDTH + 5);
                            double expectedY = startY + r * (Constants.BRICK_HEIGHT + 5);

                            // Kiểm tra xem có viên gạch nào trong danh sách hiện tại khớp vị trí này không
                            for (Brick brick : currentBricks) {
                                // So sánh vị trí co sai so nho
                                if (Math.abs(brick.getX() - expectedX) < 1 && Math.abs(brick.getY() - expectedY) < 1) {
                                    remainingBrickIndices.add(new Point(r, c)); // Lưu chỉ số (hàng, cột)
                                    break; // Tìm thấy, chuyển sang vị trí layout tiếp theo
                                }
                            }
                        }
                    }
                }
            } else {
                System.err.println("Cannot save brick state: Original layout not found for level " + level);
                remainingBrickIndices = null; 
            }
            // Tao doi tuong du lieu
            GameStateData data = new GameStateData(level, score, lives, remainingBrickIndices);
            // Sua ten file luu
            String filename = "savegame" + slotNumber + ".dat";
            try (FileOutputStream fos = new FileOutputStream(filename);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                oos.writeObject(data);
                System.out.println("Game saved successfully! Level: " + level + ", Score: " + score + ", Lives: " + lives);
            } catch (IOException e) {
                System.err.println("Error saving game:" + e.getMessage());
                e.printStackTrace();
            }

        } else {
            System.err.println("Cannot save game: Not in a level state!");
            return; // Thoát nếu không ở trong level state
        }
 
    }
}
