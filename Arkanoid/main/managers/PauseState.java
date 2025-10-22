package managers;

import util.Constants;
import util.GameStateData;
import entities.Button; // Import Button

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream; // Import để ghi file
import java.io.IOException;     // Import để xử lý lỗi file
import java.io.ObjectOutputStream; // Import để ghi đối tượng
import java.util.ArrayList; // Import ArrayList
import java.util.List;     // Import List

import static util.RenderUtil.drawCenteredString; // DÒNG MỚI (ĐÚNG)

public class PauseState extends GameState {

    private List<Button> buttons; // Them danh sach button

    public PauseState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();
        
        // Them button
        buttons = new ArrayList<>();
        // Resume Button 
        buttons.add(new Button(300, 200, 200, 50, "Resume"));
        // Nút Save and Quit (lưu và về menu)
        buttons.add(new Button(300, 270, 200, 50, "Save and Quit"));
    }

    @Override
    public void enter() {

    }
    
    @Override
    public void exit() {
        km.clearAllKeys();
    }

    @Override
    public void update() {
        // km.update(); Khong can goi lai update
        // mm.update();
        // Dùng isKeyJustPressed ===
        // Nhấn ESC hoặc R để Resume (quay lại game)
        if (km.isKeyJustPressed(KeyEvent.VK_R) || km.isKeyJustPressed(KeyEvent.VK_ESCAPE)) {
            manager.setState("level"); // Quay lại LevelState2
            return;
        }

        // Them xu ly click Button
        for (Button button : buttons) {
            if (button.isHovering(mm.getMouseX(), mm.getMouseY())) {
                if (mm.isLeftJustPressed()) {
                    handleButtonClick(button.getText()); 
                    return; // Thoát sớm sau khi xử lý click
                }
            }
        }

    }

    @Override
    public void render(Graphics2D g) {
        // --- Vẽ nền mờ (Tùy chọn) ---
        // Lấy state level hiện tại để vẽ nó phía sau (nếu muốn hiệu ứng mờ)
        LevelState2 levelState = manager.getCurrentLevelState();
        if (levelState != null) {
            levelState.render(g); // Vẽ lại màn hình game
        }

        // Vẽ một lớp màu đen bán trong suốt lên trên
        g.setColor(new Color(0, 0, 0, 150)); // Màu đen với độ trong suốt alpha=150
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        // --- Kết thúc vẽ nền mờ ---


        // Vẽ khung Pause
        g.setColor(Color.decode("#FAB12F"));
        g.fillRect(200, 100, 400, 300); // Tọa độ và kích thước khung Pause
        g.setColor(Color.BLACK);
        g.drawRect(200, 100, 400, 300); // Viền khung

        // Vẽ chữ "GAME PAUSED"
        g.setColor(Color.decode("#DD0303"));
        g.setFont(new Font("Arial", Font.BOLD, 36));
        // Căn giữa trong khung Pause
        drawCenteredString("GAME PAUSED", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 0, -120); // Điều chỉnh Y offset

        // === VẼ CÁC BUTTON ===
        for (Button button : buttons) {
            button.draw(g); // Vẽ từng button
        }
        // === KẾT THÚC VẼ ===

        // Vẽ hướng dẫn nhỏ ở dưới (tùy chọn)
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        drawCenteredString("Press ESC or R to Resume", Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                g, 0, 150); // Điều chỉnh Y offset
    }

    // Ham xu ly click
    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "Resume":
                manager.setState("level"); // Quay lại game
                break;
            case "Save and Quit":
                saveGame(); // Gọi hàm lưu game
                manager.setState("menu"); // Quay về menu chính
                break;
            default:
                System.err.println("Unknown pause button: " + buttonText);
                break;
        }
    }


    // === THÊM HÀM LƯU GAME ===
    private void saveGame() {
        
        // LevelState2 currentLevelState = manager.getCurrentLevelState();
        // if (currentLevelState == null) {
        //     System.out.println("Cannot save game: Not in a level state!");
        //     return;
        // }

        // Lấy dữ liệu hiện tại
        int level = manager.getCurrentLevel(); // Lấy từ GameStateManager là chính xác nhất
        int score = manager.getScore();
        int lives = manager.getLives();

        if (level <= 0) {
            System.err.println("Cannot save game: Invalid game data (perhaps not started?).");
             return; // Không lưu nếu dữ liệu không hợp lệ
        }
        // Tao doi tuong du lieu
        GameStateData data = new GameStateData(level, score, lives);

        try (FileOutputStream fos = new FileOutputStream("savegame.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(data);
            System.out.println("Game saved successfully! Level: " + level + ", Score: " + score + ", Lives: " + lives);
        } catch (IOException e) {
            System.err.println("Error saving game:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
