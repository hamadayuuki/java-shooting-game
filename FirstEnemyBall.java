import java.awt.*;

public class FirstEnemyBall extends EnemyBall {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image enemyBallImage = toolkit.getImage(getClass().getResource("Pic/FirstEnemyBall.png"));

    public FirstEnemyBall(int enemyX, int enemyY) { 
        type = 0;
        x = enemyX + 6;   // 敵の中心に表示させる
        y = enemyY + 10;
        dx = 0;
        dy = 15;
        width = 12;   // 3
        height = 40;   // 10
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
