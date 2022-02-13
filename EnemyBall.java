import java.awt.*;

public abstract class EnemyBall extends Panel {

    public int x , y;
    int dx, dy;
    int width, height;
    int type;

    static Enemy enemy;
    static ShootingPanel shootingPanel = new ShootingPanel();
    public HP hp = new HP();
    public Player player;

    public EnemyBall() { }

    public abstract void move();
    public abstract void draw(Graphics g);

    // プレイヤー との衝突判定
    public void confirmContactWithPlayer() {
        if(((player.x - width) <= x && x <= player.x + player.width) && (player.y <= y && y <= player.y + player.height)) {
            shootingPanel.deleteEnemyBall(this);   // 敵の弾を削除
            if (this.type == 0 || this.type == 1) {
                hp.decreaseHp(1);   // HP減少
            } else if (this.type == 2) {
                hp.decreaseHp(2);   // HP減少
            }
        }
    }

    // 主人公の弾 との衝突判定
    public void confirmContactWithPlayerBall() {
        Ball ball;
        for (int i = 0; i < shootingPanel.playerBallList.size(); i++) {
            ball = shootingPanel.playerBallList.get(i);
            if(((ball.x - width) <= x && x <= ball.x + ball.width) && (ball.y <= y && y <= ball.y + ball.height + 10)) {
                shootingPanel.deleteEnemyBall(this);   // 敵の弾を削除
                shootingPanel.deletePlayerBall(ball);
            }
        }
    }

}