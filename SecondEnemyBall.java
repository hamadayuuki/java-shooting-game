import java.awt.*;

public class SecondEnemyBall extends EnemyBall {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image enemyBallImage = toolkit.getImage(getClass().getResource("Pic/SecondEnemyBall.png"));

    public SecondEnemyBall(int enemyX, int enemyY) { 
        type = 1;
        x = enemyX + 6;   // 敵の中心に表示させる
        y = enemyY + 10;
        dx = 0;
        dy = 30;
        width = 18;   // 3
        height = 60;   // 10
    }

    // 下移動
    public void move() {
        if(y <= 0) {
            shootingPanel.deleteEnemyBall(this);
        } else {
            y += dy;
            confirmContactWithPlayer();
            confirmContactWithPlayerBall();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(enemyBallImage, x, y, width, height, this);
    }

}
