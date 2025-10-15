package ui;

import entities.*;
import util.Constants;
import util.LevelData;

import java.util.ArrayList;
// Nang cap BrickManager voi LevelData nhan dau vao la so nguyen chi so level
// Tao class tao cac brick va quan ly chung, dc goi trong GameWindow
public class BrickManager {

    //Thuoc tinh dinh nghia cau truc gach bang 1 mang 2D 
    // 0: NULL, 1: NormalBrick 2. StrongBrick, 3. ExplosiveBrick, 4.UnbreakableBrick
    

    public ArrayList<Brick> createBricks(int levelNumber, int panelWidth) {
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
        return brickList;
    }
}
