import java.awt.*;

public class Ball extends Panel {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image playerImage = toolkit.getImage(getClass().getResource("Pic/Ball.png"));

    public int x , y;

    Player player;

    public ShootingPanel shootingPanel;   // 変数の参照なら、これだけで良い

    public Ball() { 
        player = new Player();
        x = player.x;
        y = player.y;
    }

    public void move() {
        if(y >= 680) {
            y = 1000;
        } else {
            y -= 20;
            confirmContactWithEnemy();
        }
    }

    public void confirmContactWithEnemy() {
        for(Enemy e: shootingPanel.enemyList) {
            if((e.x <= x && x <= e.x + 40) && (e.y <= y && y <= e.y + 25)) {
                y = 1000;
                e.y = 2000;
            }
        }
    }

    public void draw(Graphics g) {
        // Ball.png(360×600 : 3:10)
        g.drawImage(playerImage, x, y, 12, 40, this);
    }
}