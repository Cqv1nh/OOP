package util;

public final class Constants {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final String SCREEN_TITLE = "Arkaniod";

    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 20;
    public static final int PADDLE_SPEED = 10;

    public static final int INIT_PADDLE_X = (SCREEN_WIDTH - PADDLE_WIDTH) / 2;
    public static final int INIT_PADDLE_Y = 500;

    public static final int BALL_DIAMETER = 20;
 
    public static final double BALL_SPEED = 5.0;
    public static final int INIT_BALL_X = INIT_PADDLE_X + (PADDLE_WIDTH - BALL_DIAMETER) / 2;
    public static final int INIT_BALL_Y = INIT_PADDLE_Y - BALL_DIAMETER;

    public static final int BRICK_WIDTH = 40;
    public static final int BRICK_HEIGHT = 20;

    public static final int POWERUP_WIDTH = 20;
    public static final int POWERUP_HEIGHT = 20;

    public static final int PADDLE_EXPAND_AMOUNT = 20; // Độ dài tăng thêm thanh.
    public static final int POWERUP_DURATION = 7000; // 7000 milis.
}