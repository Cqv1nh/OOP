package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class AssetManager {

    // Khai bao bien luu tru hinh anh
    public static BufferedImage ball, paddle, extraLife, expandPaddle, multiBall, increaseSpeed, strongBrick_1hit;
    public static BufferedImage normalBrick, strongBrick, explosiveBrick, unbreakableBrick;
    // Them cac powerUp khac neu can

    /**
     * Tải tất cả các hình ảnh từ thư mục /resources/images vào bộ nhớ.
     * Phương thức này nên được gọi một lần duy nhất khi game khởi động.
     */
    public static void loadImages() {
        ball = loadImage("/resources/images/Ball.png");
        // Bạn chưa có hình cho paddle, nên ta sẽ tạm dùng hình viên gạch xanh
        paddle = loadImage("/resources/images/paddleRed.png");
        
        // Tải hình ảnh gạch
        normalBrick = loadImage("/resources/images/element_green_rectangle_glossy.png");
        strongBrick = loadImage("/resources/images/element_blue_rectangle_glossy.png");
        strongBrick_1hit = loadImage("/resources/images/element_red_rectangle_glossy.png");
        explosiveBrick = loadImage("/resources/images/element_yellow_rectangle_glossy.png");
        unbreakableBrick = loadImage("/resources/images/element_grey_rectangle_glossy.png");
    
        // Tải hình ảnh power-up. Sửa code:
        extraLife = loadImage("/resources/images/extralife.png");
        expandPaddle = loadImage("/resources/images/expandpaddle.png");
        multiBall = loadImage("/resources/images/multiball.png");
        increaseSpeed = loadImage("rescources/images/increasespeed.png");
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