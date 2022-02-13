import java.awt.*;

public class Ball extends Panel {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image playerImage = toolkit.getImage(getClass().getResource("Pic/Ball.png"));

    public int x , y;

    static Player player;

    static ShootingPanel shootingPanel = new ShootingPanel();

    int width, height;

    public Ball() { 
        x = player.x + 20;
        y = player.y;

        width = 12;
        height = 40;
    }

    public void move() {
        if(y <= 0) {
            shootingPanel.deletePlayerBall(this);
        } else {
            y -= 20;
            confirmContactWithEnemy();
        }
    }

    // 敵との衝突判定
    public void confirmContactWithEnemy() {
        Enemy e;
        for(int i = 0; i < shootingPanel.enemyList.size(); i++) {
            e = shootingPanel.enemyList.get(i);
            if(((e.x - width) <= x && x <= e.x + e.width) && (e.y <= y && y <= e.y + e.height)) {
                shootingPanel.score += e.dScore;
                shootingPanel.deletePlayerBall(this);   // 主人公の弾を削除
                shootingPanel.deleteEnemy(e);   // 敵を削除
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, x, y, width, height, this);
    }

}