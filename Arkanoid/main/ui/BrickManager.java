package ui;

import entities.*;
import util.Constants;

import java.util.ArrayList;

// Tao class tao cac brick va quan ly chung, dc goi trong GameWindow
public class BrickManager {

    //Thuoc tinh dinh nghia cau truc gach bang 1 mang 2D 
    // 0: NULL, 1: NormalBrick 2. StrongBrick, 3. ExplosiveBrick, 4.UnbreakableBrick
    private static final int[][] LEVEL_1_LAYOUT = {
        {0, 1, 1, 1, 1, 4, 4, 1, 1, 1, 0},
        {1, 1, 2, 2, 2, 4, 4, 2, 2, 1, 1},
        {1, 3 ,3 ,1, 3, 1, 3, 1, 3, 3, 1}
    };

    public ArrayList<Brick> createBricks() {
        ArrayList<Brick> brickList = new ArrayList<>();
        // Lay kich thuoc mang dong va cot
        int numRows = LEVEL_1_LAYOUT.length;
        int numCols = LEVEL_1_LAYOUT[0].length;
        
        // Can le cho toan bo khoi gach moi vien gach cach nhau 5 theo truc x
        double totalBricksWidth = numCols * Constants.BRICK_WIDTH + ((numCols) - 1) * 5;
        double startX = (Constants.SCREEN_WIDTH - totalBricksWidth) / 2;
        double startY = 60; // Vi tri bat dau theo truc y

        // Dat cac vien gach cach nhau
        for (int i= 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int brickCode = LEVEL_1_LAYOUT[i][j];
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
