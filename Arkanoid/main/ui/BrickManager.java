package ui;

import entities.*;
import util.Constants;
import util.LevelData;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

public class BrickManager {
    // Thuoc tinh dinh nghia cau truc gach bang 1 mang 2D
    // 0: NULL, 1: NormalBrick 2. StrongBrick, 3. ExplosiveBrick, 4.UnbreakableBrick

    /**
     * Tạo ra list quản lý bricks + quản lý chung.
     *
     * @param levelNumber chỉ số màn.
     * @param panelWidth độ rộng màn hình.
     * @param remainingBrickIndices
     * @return list gạch.
     */
    public ArrayList<Brick> createBricks(int levelNumber, int panelWidth, HashSet<Point> remainingBrickIndices) {
        ArrayList<Brick> brickList = new ArrayList<>();
        int[][] layout = LevelData.getLayoutForLevel(levelNumber);

        // Nếu level không tồn tại, trả về danh sách rỗng để tránh lỗi
        if (layout == null) {
            System.err.println("Error: Level " + levelNumber + " not found!");
            return brickList;
        }
        // Lay kich thuoc mang dong va cot
        int numRows = layout.length;
        int numCols = layout[0].length;
        
        // Can le cho toan bo khoi gach moi vien gach cach nhau 5 theo truc x
        double totalBricksWidth = numCols * Constants.BRICK_WIDTH + ((numCols) - 1) * 5;
        double startX = (Constants.SCREEN_WIDTH - totalBricksWidth) / 2;
        double startY = 60; // Vi tri bat dau theo truc y

        // Dat cac vien gach cach nhau
        for (int i= 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int brickCode = layout[i][j];
                // Neu trong thi bo qua
                if (brickCode == 0) {
                    continue;
                }

                // LOGIC KIỂM TRA KHI LOAD GAME
                boolean shouldCreate = true; // Mặc định là tạo gạch
                if (remainingBrickIndices != null) { // Nếu đang load game (danh sách không null)
                    // Kiểm tra xem vị trí (i, j) này có trong danh sách gạch còn lại không
                    if (!remainingBrickIndices.contains(new Point(i, j))) {
                        shouldCreate = false; // Không tạo gạch này nếu nó không còn lại
                    }
                }
                // KẾT THÚC KIỂM TRA
                if (shouldCreate) {
                    double x = startX + j * (Constants.BRICK_WIDTH + 5);
                    double y = startY + i * (Constants.BRICK_HEIGHT + 5);
                    switch (brickCode) {
                        case 1:
                            brickList.add(new NormalBrick(x, y, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT));
                            break;
                        case 2:
                            brickList.add(new StrongBrick(x, y, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT));
                            break;
                        case 3:
                            brickList.add(new ExplosiveBrick(x, y, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT));
                            break;
                        case 4:
                            brickList.add(new UnbreakableBrick(x, y, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT));
                            break;
                        default:
                            break;
                    }
                }   
            }
        }
        return brickList;
    }

    /**
     * Constructor mặc định nếu cần (ví dụ, nếu LevelState2 vẫn gọi new BrickManager()).
     */
    public BrickManager() {
    }
}
