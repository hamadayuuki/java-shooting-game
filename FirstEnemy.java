import java.util.Random;
import java.awt.*;

public class FirstEnemy extends Enemy {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image img = toolkit.getImage(getClass().getResource("Pic/FirstEnemy.png"));

    public FirstEnemy() {
        x = 0;
        y = 100;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, 50, 50, this);
    }

    // 縦移動
    public void move(){
        y += 10;
        // 画面外へ移動
        if(y >= 500) {
            x = 1000;
            y = 1000;
        }
    }
    
}
