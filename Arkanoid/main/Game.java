import engine.CollisionDetector;
import engine.KeyboardInputTerminal;
import entities.Ball;
import entities.NormalBrick;
import entities.Paddle;
import ui.RendererConsole;
import util.Constants;
// import engine.KeybroadInputJPanel; // remove if not used

import static util.FileLevelLoader.loadLevelFromFile;
import engine.PaddleController;
import util.FileLevelLoader;

import java.io.IOException;
import java.util.List;

public class Game {
    private static boolean isRunning = true;
    public static int frameCounter = 0;
    public void stopGame() {
        isRunning = false;
    }

    public static void main(String[] args) {
        try {
            Paddle paddle = new Paddle(
                    Constants.INIT_PADDLE_X,
                    Constants.INIT_PADDLE_Y,
                    Constants.PADDLE_WIDTH,
                    Constants.PADDLE_HEIGHT,
                    0, 0, "None"
            );

            Ball ball = new Ball (5,5);

            RendererConsole render = new RendererConsole();


            //List<NormalBrick> bricks = FileLevelLoader.loadLevelFromFile("resources/level1.txt");

            KeyboardInputTerminal kit = new KeyboardInputTerminal();
            PaddleController pc = new PaddleController(kit);

            kit.start(); // start listening for key presses
            render.CharacterClear();

            while (isRunning) {
                render.clearConsole();

                if (!pc.movePaddle(paddle)) {
                    isRunning = false;
                }

                ball.move();
                CollisionDetector.checkWallCollision(ball);

                render.DrawBorder();
                render.DrawPaddle(paddle);
                render.DrawBall(ball);
                //render.DrawNormalBrick(bricks);

                render.display();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                render.CharacterClear();
                frameCounter++;
            }
            kit.stop(); // stop input thread safely when game ends

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(frameCounter);
    }
}
