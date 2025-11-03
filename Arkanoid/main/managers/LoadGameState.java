package managers;

import entities.Button;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;
import util.Constants;
import util.GameStateData;
import util.RenderUtil;
import util.AssetManager;

public class LoadGameState extends GameState {
    private Button[] slotButtons = new Button[3];
    private GameStateData[] slotData = new GameStateData[3]; // Lưu dữ liệu xem trước
    private Button backButton;
    private final Font FONT_TITLE = new Font("Arial", Font.BOLD, 50);
    private final Font FONT_SLOT_NAME = new Font("Arial", Font.BOLD, 20); // Font cho "Save Game X"
    private final Font FONT_SLOT_INFO = new Font("Arial", Font.PLAIN, 16); // Font cho Level, Score, Lives
    private final Font FONT_EMPTY_SLOT = new Font("Arial", Font.ITALIC, 20); // Font cho slot trống
    private final int SLOT_WIDTH = 200;  // Chiều rộng của mỗi slot
    private final int SLOT_HEIGHT = 150; // 800 / 600 = 200 / 150
    private final int SPACING = 40; // Khoảng cách giữa các slot
    private final int TOTAL_SLOTS_WIDTH = 3 * SLOT_WIDTH + 2 * SPACING;
    private final int START_X = (Constants.SCREEN_WIDTH - TOTAL_SLOTS_WIDTH) / 2; // Căn giữa
    private final int START_Y = 180; // Vị trí Y bắt đầu cho các slot

    /**
     * Khởi tạo màn hình "Load Game", tạo 3 đối tượng Button (cho 3 ô lưu) và nút "Back" (Quay lại).
     *
     * @param manager
     */
    public LoadGameState(GameStateManager manager) {
        super(manager);
        km = manager.getKm();
        mm = manager.getMm();

        // Khởi tạo 3 nút lớn (là 3 hình chữ nhật)
        for (int i = 0; i < 3; i++) {
            slotButtons[i] = new Button(START_X + i * (SLOT_WIDTH + SPACING), START_Y, SLOT_WIDTH,
                    SLOT_HEIGHT + 30, "Slot " + (i + 1), "Slot" + (i + 1),
                    null, null);
            // Chiều cao tăng thêm 30 để có chỗ cho chữ "Save Game X" bên dưới ảnh
        }

        backButton = new Button(Constants.SCREEN_WIDTH / 2 - 100, START_Y + SLOT_HEIGHT + 85, 200, 50, "Back to Menu");
    }

    /**
     * Method được gọi một lần; gọi previewSaveFile() để đọc 3 file save và lấy thông tin xem trước (điểm, mạng).
     */
    public void enter() {
        // Tải dữ liệu xem trước cho cả 3 slot
        // Xem truoc gom co ten slot save, va khung anh nen mini
        slotData[0] = previewSaveFile(1);
        slotData[1] = previewSaveFile(2);
        slotData[2] = previewSaveFile(3);

        Properties languageProps = manager.getLanguageProps();

        backButton.setText(languageProps.getProperty("load.back", "RETURN MENU"));
    }

    /**
     * Method thoát màn hình.
     */
    public void exit() {
    }

    /**
     * Method cập nhật logic mỗi khung hình.
     */
    public void update() {
        // Kiem tra click vao 3 slot hay k
        for (int i = 0; i < slotButtons.length; i++) {
            if (slotButtons[i].isHovering(mm.getMouseX(),mm.getMouseY())) {
                if (mm.isLeftJustPressed()) {
                    // Chỉ load nếu slot đó có dữ liệu
                    if (slotData[i] != null) {
                        loadGame(slotData[i]); // Gọi hàm load với dữ liệu đã có
                    }
                    return; // Bam xong k xet cac nut tiep theo nua
                }
            }
        }
        // Kiểm tra click nút Back
        backButton.setHoveringState(backButton.isHovering(mm.getMouseX(), mm.getMouseY()));
        if (backButton.isHovering(mm.getMouseX(), mm.getMouseY())) {
            if (mm.isLeftJustPressed()) {
                manager.setState("menu");
                return;
            }
        }
        // Nhan ECS cung la thoat ra man hinh chinh 
        if (km.isKeyJustPressed(KeyEvent.VK_ESCAPE)) {
            manager.setState("menu");
        }
    }

    /**
     * Vẽ nền, tiêu đề "LOAD GAME", và lặp qua 3 ô lưu để vẽ.
     *
     * @param g
     */
    public void render(Graphics2D g) {
        // Ve nen 
        g.drawImage(AssetManager.menuBackground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);

        Properties languageProps = manager.getLanguageProps();
        // Ve tieu de
        g.setColor(Color.YELLOW);
        g.setFont(FONT_TITLE);
        RenderUtil.drawCenteredString(languageProps.getProperty("load.title", "LOAD GAME"),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, g, 0, -220);

        // Vẽ 3 slot
        for (int i = 0; i< slotButtons.length; i++) {
            Button slot = slotButtons[i];
            GameStateData data = slotData[i];

            // Lấy tọa độ của button
            int x = (int) slot.getX();
            int y = (int) slot.getY();
            int w = (int) slot.getWidth();
            int h = (int) slot.getHeight();
            
            // Các nút slot chỉ đóng vai trò làm logic kiểm tra xem nút có được nhấn hay không thôi.
            // Vẽ khung tổng thể của slot (để bao gồm cả ảnh và chữ)
            g.setColor(Color.decode("#3498DB")); // Màu nền cho slot
            g.fillRect(x, y, w, h);
            g.setColor(Color.WHITE); // Viền trắng
            g.drawRect(x, y, w, h);

            // Kiểm tra trạng thái hover để highlight
            if (slot.isHovering(mm.getMouseX(), mm.getMouseY())) {
                 g.setColor(new Color(255, 255, 255, 50)); // Lớp phủ trắng trong suốt khi hover
                 g.fillRect(x, y, w, h);
            }

            // VẼ ẢNH NỀN MINI
            // Vị trí vẽ ảnh nền mini (bên trong khung slot, chừa chỗ cho chữ ở dưới)
            int imgY = y + 5; // Cách mép trên 5 pixel
            int imgH = SLOT_HEIGHT - 30 - 5; // Chiều cao ảnh = chiều cao slot trừ đi khoảng trống chữ và padding
            g.setColor(Color.BLACK); // Nền đen cho ảnh nếu ảnh không load được
            g.fillRect(x + 5, imgY, SLOT_WIDTH - 10, imgH); // Vẽ nền đen trước

            if (data != null) {
                // ve anh nen mini
                // Lay level thong qua du lieu data (GameStateManager)
                // Trong GameStateData , cac bien deu de o public
                BufferedImage bgMini = AssetManager.levelBackgrounds.get(data.currentLevel);
                if (bgMini != null) {
                    // Vẽ ảnh nền thu nhỏ vào khung ảnh mini trong slot
                    g.drawImage(bgMini, x + 5, imgY, SLOT_WIDTH - 10, imgH, null);
                }

                // Ve thong tin save
                g.setColor(Color.WHITE);
                g.setFont(FONT_SLOT_INFO);
                // Vị trí cho thông tin chi tiết (bên dưới ảnh)
                int infoY = imgY + imgH + 10; // Khoảng cách từ đáy ảnh
                g.drawString(languageProps.getProperty("load.level", "Level") + ": " + data.currentLevel,
                        x + 10, infoY);
                g.drawString(languageProps.getProperty("load.score", "Score") + ": " + data.score,
                        x + 80, infoY);
                g.drawString(languageProps.getProperty("load.lives", "Lives") + ": " + data.lives,
                        x + 10, infoY + 20); // Lives bên dưới Level
                // VẼ TÊN SLOT "Save Game X"
                g.setColor(Color.decode("#F1C40F")); // Màu vàng cam cho tên slot
                g.setFont(FONT_SLOT_NAME);
                RenderUtil.drawCenteredStringInColumn(g, languageProps.getProperty("load.saveGame", "SAVE GAME ") + (i + 1),
                        x, w, y + h - 10); // Căn giữa dưới cùng

            } else {
                // Nếu slot trống
                g.setColor(Color.LIGHT_GRAY);
                g.setFont(FONT_EMPTY_SLOT);
                // Vẽ chữ "Empty Slot" lên phần ảnh
                RenderUtil.drawCenteredStringInColumn(g,
                        languageProps.getProperty("load.empty" , "[EMPTY SLOT]"),
                        x + 5, SLOT_WIDTH - 10, imgY + imgH / 2 + 5);

                // Vẽ tên slot bên dưới
                g.setColor(Color.WHITE);
                g.setFont(FONT_SLOT_NAME);
                RenderUtil.drawCenteredStringInColumn(g, languageProps.getProperty("load.saveGame") + (i + 1),
                        x, w, y + h - 10);
            }
        }
        // Chỉ có nút backButton mới sử dụng phương thức vẽ đc cài đặt từ trước
        backButton.draw(g);
    }

    /**
     * Method đọc file save để lấy dữ liệu xem trước (hoặc trả về null).
     */
    public GameStateData previewSaveFile(int slotNumber) {
        String filename = "savegame" + slotNumber + ".dat";
        File saveFile = new File(filename);
        if (!saveFile.exists()) {
            return null; // Slot trong khong can bao loi
        } 

        try (FileInputStream fis = new FileInputStream(saveFile);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameStateData) ois.readObject(); // Ep kieu
        } catch (Exception e) {
            System.err.println("Error previewing save file " + slotNumber + ": " + e.getMessage());
            return null; // File hong 
        }
    }

    /**
     * Method thực hiện logic load game.
     *
     * @param loadedData du dieu da dc xem qua trong ham previewSaveFile
     */
    public void loadGame(GameStateData loadedData) {
        if (loadedData == null) {
            return;
        }

        System.out.println("Loading game from slot...");
        // Đặt cờ báo hiệu đang load game
        manager.setLoadingGameFlag(true);

        // 1. Tải level tương ứng VỚI DỮ LIỆU ĐÃ LOAD
        // 2. Gọi loadLevel phiên bản đầy đủ, truyền TẤT CẢ dữ liệu đã load
        manager.loadLevel(loadedData.currentLevel,
                          loadedData.score,
                          loadedData.lives,
                          loadedData.remainingBrickIndices);
        manager.setGameRunStartTime(System.currentTimeMillis()); // Reset thoi gian choi, phu thuoc cho xep hang
    }
}
