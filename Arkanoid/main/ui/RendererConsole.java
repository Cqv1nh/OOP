package ui;

import entities.Ball;
import entities.Brick;
import entities.NormalBrick;
import entities.Paddle;
import util.Constants;

import java.io.Console;
import java.util.Arrays;
import java.util.List;

import static util.FileLevelLoader.loadLevelFromFile;


public class RendererConsole {
    private final int rateX = 10;
    private final int rateY = 30;
    private final int TerminalWidth = Constants.SCREEN_WIDTH / 10;
    private final int TerminalHeight = Constants.SCREEN_HEIGHT / 30;
    char[][] grid;

    /**
     * Tao mot ma tran kieu char 2d.
     */
    public RendererConsole() {
        this.grid = new char[TerminalHeight][TerminalWidth];
    }

    /**
     * Xoa cac ki tu trong ma tran 2d.
     */
    public void CharacterClear() {
        for (int i = 0; i < TerminalHeight; i++) {
            Arrays.fill(grid[i], 0, TerminalWidth, ' ');
        }
    }

    /**
     * Ve duong bien gioi han.
     */
    public void DrawBorder() {
        Arrays.fill(grid[0], 0, TerminalWidth, '#');
        Arrays.fill(grid[TerminalHeight - 1], 0, TerminalWidth, '#');

        for (int i = 0; i < TerminalHeight; i++) {
            grid[i][0] = '#';
            grid[i][TerminalWidth - 1] = '#';
        }
    }

    /**
     * Phuong thuc ve paddle.
     * @param paddle paddle.
     */
    public void DrawPaddle(Paddle paddle) {
        int TerminalX = (int) paddle.getX() / rateX;
        int TerminalY = TerminalHeight - 3;
        int TerminalPaddleWidth = Constants.PADDLE_WIDTH / rateX;

        int startX = Math.max(TerminalX - TerminalPaddleWidth / 2, 0);
        int endX = Math.min(TerminalX + TerminalPaddleWidth / 2, TerminalWidth - 1);
        Arrays.fill(grid[TerminalY], startX, endX, '=');
    }

    /**
     * Phuong thuc ve bong.
     * @param ball bong.
     */
    public void DrawBall(Ball ball) {
        int Ball_X = (int) ball.getX() / rateX ;
        int Ball_Y = (int) ball.getY() / rateY ;

        grid[Ball_Y][Ball_X] = 'O';
    }

    /**
     * Ve mot vien gach.
     * @param brick vien gach.
     */
    private void DrawSingleBrick(NormalBrick brick){
        int Pos_X = (int) brick.getX();
        int Pos_Y = (int) brick.getY();

        grid[Pos_X][Pos_Y] = '#';
    }

    /**
     * Ve toan bo vien gach.
     * @param bricks danh sach cac vien gach.
     */
    public void DrawNormalBrick(List<NormalBrick> bricks) {
        for (NormalBrick brick : bricks) {
            DrawSingleBrick(brick);
        }
    }

    /**
     * In ra terminal tu ma tran 2d.
     */
    public void display() {

        StringBuilder s = new StringBuilder();

        for (int i = 0; i < TerminalHeight; i++) {
            s.append(grid[i]);
            s.append('\n');
        }

        System.out.print(s);
    }

    public void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {

                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        Paddle paddle = new Paddle(Constants.INIT_PADDLE_X,
                Constants.INIT_PADDLE_Y, Constants.PADDLE_WIDTH,
                Constants.PADDLE_HEIGHT,0, 0, "None");

        List<NormalBrick> bricks = loadLevelFromFile("src/resources/level1.txt"); 

        RendererConsole render = new RendererConsole();

        render.CharacterClear();

        render.DrawBorder();
        render.DrawPaddle(paddle);
        render.DrawNormalBrick(bricks);

        render.display();

    }
}