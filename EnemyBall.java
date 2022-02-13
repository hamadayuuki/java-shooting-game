import java.awt.*;

public class EnemyBall extends Panel {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image playerImage = toolkit.getImage(getClass().getResource("Pic/Ball.png"));

    public int x , y;
    static Enemy enemy;
    public ShootingPanel shootingPanel;   // 変数の参照なら、これだけで良い
    public HP hp = new HP();
    public Player player;

    public EnemyBall(int enemyX, int enemyY) { 
        x = enemyX;
        y = enemyY;
        System.out.println(x);
        System.out.println(y);
    }

    public void move() {
        if(y <= 0) {
            y = 1000;
        } else {
            y += 20;
            confirmContactWithPlayer();
        }
    }

    public void confirmContactWithPlayer() {
        if((player.x <= x && x <= player.x + 40) && (player.y <= y && y <= player.y + 25)) {
            x = 1000;
            y = 1000;

            hp.decreaseHp(1);
        }
    }

    public void draw(Graphics g) {
        // Ball.png(360×600 : 3:10)
        g.drawImage(playerImage, x, y, 12, 40, this);
    }
}