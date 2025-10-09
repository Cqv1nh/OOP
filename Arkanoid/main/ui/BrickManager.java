// package ui;

import entities.Brick;
import entities.NormalBrick;
import entities.StrongBrick;
import util.Constants;

import java.util.ArrayList;

// Tao class tao cac brick va quan ly chung, dc goi trong GameWindow
public class BrickManager {
    public ArrayList<Brick> createBricks() {
        ArrayList<Brick> brickList = new ArrayList<>();
        // Dat cac vien gach cach nhau
        for (int i = 60; i <= 85; i = i + Constants.BRICK_HEIGHT + 5) {
            for (int j = 55; j <= 705; j = j + Constants.BRICK_WIDTH + 5) {
                brickList.add(new NormalBrick(j, i, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT));
            }
        }
        for (int j = 55; j <= 705; j = j + Constants.BRICK_WIDTH + 5) {
                brickList.add(new StrongBrick(j, 110, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT));
        }    
        
        return brickList;
    }
}
