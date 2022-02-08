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
        
        // 画面外の時
        if(y >= 500) {
            x = 2000;
            y = 2000;
        } 
        // 画面内の時
        else {
            y += 10;
        }
    }
    
}
