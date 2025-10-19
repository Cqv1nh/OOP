package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {

    // Khai bao bien luu tru hinh anh
    public static BufferedImage  extraLife, expandPaddle, multiBall, increaseSpeed;
    // Dung map de luu tru cac anh nen , key = so level
    public static Map<Integer, BufferedImage> levelBackgrounds = new HashMap<>();
    public static Map<Integer, BufferedImage> ball = new HashMap<>();
    public static Map<Integer, BufferedImage> paddle = new HashMap<>();
    public static Map<Integer, BufferedImage> normalBrick = new HashMap<>();
    public static Map<Integer, BufferedImage> strongBrick = new HashMap<>();
    public static Map<Integer, BufferedImage> strongBrick_1hit = new HashMap<>();
    public static Map<Integer, BufferedImage> explosiveBrick = new HashMap<>();
    public static Map<Integer, BufferedImage> unbreakableBrick = new HashMap<>();
    


    // Them cac powerUp khac neu can

    /**
     * Tải tất cả các hình ảnh từ thư mục /resources/images vào bộ nhớ.
     * Phương thức này nên được gọi một lần duy nhất khi game khởi động.
     */
    public static void loadImages() {
        
        // Tải hình ảnh power-up. Sửa code:
        extraLife = loadImage("/resources/images/PowerUp/extralife.png");
        expandPaddle = loadImage("/resources/images/PowerUp/expandpaddle.png");
        multiBall = loadImage("/resources/images/PowerUp/multiball.png");
        increaseSpeed = loadImage("/resources/images/PowerUp/increasespeed.png");

        // Tải ảnh nền cho level 1 (giả sử tên file là BackGround.jpg)
        // Tải 5 ảnh nền cho 5 level
        // Đảm bảo bạn có các file BackGround1.jpg, BackGround2.jpg,... trong thư mục resources/images
        levelBackgrounds.put(1, loadImage("/resources/images/Level1/BackGround1.jpg"));
        levelBackgrounds.put(2, loadImage("/resources/images/Level2/BackGround2.jpg"));
        levelBackgrounds.put(3, loadImage("/resources/images/Level3/BackGround3.jpg"));
        levelBackgrounds.put(4, loadImage("/resources/images/Level4/BackGround4.jpg"));
        levelBackgrounds.put(5, loadImage("/resources/images/Level5/BackGround5.jpg"));

        // Tai ball
        ball.put(1, loadImage("/resources/images/Level1/BallLevel1.png"));
        ball.put(2, loadImage("/resources/images/Level2/BallLevel2.png"));
        ball.put(3, loadImage("/resources/images/Level3/BallLevel3.png"));
        ball.put(4, loadImage("/resources/images/Level4/BallLevel4.png"));
        ball.put(5, loadImage("/resources/images/Level5/BallLevel5.png"));

        // Tai paddle
        paddle.put(1, loadImage("/resources/images/Level1/PaddleLevel1.png"));
        paddle.put(2, loadImage("/resources/images/Level2/PaddleLevel2.png"));
        paddle.put(3, loadImage("/resources/images/Level3/PaddleLevel3.png"));
        paddle.put(4, loadImage("/resources/images/Level4/PaddleLevel4.png"));
        paddle.put(5, loadImage("/resources/images/Level5/PaddleLevel5.png"));

        // Tai brick
        normalBrick.put(1, loadImage("/resources/images/Level1/NormalBrickLevel1.png"));
        normalBrick.put(2, loadImage("/resources/images/Level2/NormalBrickLevel2.png"));
        normalBrick.put(3, loadImage("/resources/images/Level3/NormalBrickLevel3.png"));
        normalBrick.put(4, loadImage("/resources/images/Level4/NormalBrickLevel4.png"));
        normalBrick.put(5, loadImage("/resources/images/Level5/NormalBrickLevel5.png"));

        strongBrick.put(1, loadImage("/resources/images/Level1/StrongBrick1Level1.png"));
        strongBrick.put(2, loadImage("/resources/images/Level2/StrongBrick1Level2.png"));
        strongBrick.put(3, loadImage("/resources/images/Level3/StrongBrick1Level3.png"));
        strongBrick.put(4, loadImage("/resources/images/Level4/StrongBrick1Level4.png"));
        strongBrick.put(5, loadImage("/resources/images/Level5/StrongBrick1Level5.png"));


        strongBrick_1hit.put(1, loadImage("/resources/images/Level1/StrongBrick2Level1.png"));
        strongBrick_1hit.put(2, loadImage("/resources/images/Level2/StrongBrick2Level2.png"));
        strongBrick_1hit.put(3, loadImage("/resources/images/Level3/StrongBrick2Level3.png"));
        strongBrick_1hit.put(4, loadImage("/resources/images/Level4/StrongBrick2Level4.png"));
        strongBrick_1hit.put(5, loadImage("/resources/images/Level5/StrongBrick2Level5.png"));

        // explosiveBrick
        explosiveBrick.put(3, loadImage("/resources/images/Level3/ExplosiveBrickLevel3.png"));
        explosiveBrick.put(4, loadImage("/resources/images/Level4/ExplosiveBrickLevel4.png"));
        explosiveBrick.put(5, loadImage("/resources/images/Level5/ExplosiveBrickLevel5.png"));
        
        // UnbreakableBrick
        unbreakableBrick.put(2, loadImage("/resources/images/Level2/UnbreakableBrickLevel2.png"));
        unbreakableBrick.put(3, loadImage("/resources/images/Level3/UnbreakableBrickLevel3.png"));
        unbreakableBrick.put(4, loadImage("/resources/images/Level4/UnbreakableBrickLevel4.png"));
        unbreakableBrick.put(5, loadImage("/resources/images/Level5/UnbreakableBrickLevel5.png"));
    }

    /**
     * Phương thức trợ giúp để tải một tệp hình ảnh từ đường dẫn bên trong resources.
     * @param path Đường dẫn đến tệp, bắt đầu bằng dấu "/".
     * @return Một đối tượng BufferedImage hoặc null nếu không tìm thấy.
     */
    private static BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try (InputStream is = AssetManager.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Không thể tải hình ảnh: " + path);
            } else {
                image = ImageIO.read(is);
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc tệp hình ảnh: " + path);
            e.printStackTrace();
        }
        return image;
    }
}