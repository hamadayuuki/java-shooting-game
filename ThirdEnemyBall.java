import java.awt.*;
import java.util.Random;

public class ThirdEnemyBall extends EnemyBall {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image enemyBallImage = toolkit.getImage(getClass().getResource("Pic/ThirdEnemyBall.png"));

    Random random = new Random();

    public ThirdEnemyBall(int enemyX, int enemyY) { 
        type = 2;
        x = enemyX + 6;   // 敵の中心に表示させる
        y = enemyY + 10;
        int randomNum = random.nextInt(2);
        if (randomNum == 0)  dx = 20;
        else dx = -20;
        dy = 20;
        width = 21;   // 3
        height = 70;   // 10
    }

    // 下移動
    public void move() {
        if(y <= 0) {
            shootingPanel.deleteEnemyBall(this);
        } else {
            x += dx;
            y += dy;
            if (x <= 0 || x > 800 - width) {
                dx *= -1;
            }
            confirmContactWithPlayer();
            confirmContactWithPlayerBall();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(enemyBallImage, x, y, width, height, this);
    }

}
