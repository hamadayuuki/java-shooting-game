
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;



public class ShootingPanel extends JPanel {

    public BufferedImage image;   // 自分で画像を指定する

    public ShootingPanel() {
        super();
        this.image = new BufferedImage(800 , 800 , BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image , 0 , 0 , this);
    }

    // 呼び出されると画面が更新される
    public void draw() {
        this.repaint();
    }
}
