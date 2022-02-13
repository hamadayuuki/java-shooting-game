import java.util.Random;
import java.awt.*;

public class ThirdEnemy extends Enemy {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image img = toolkit.getImage(getClass().getResource("Pic/ThirdEnemy.png"));
    
    public ThirdEnemy() {

        type = 2;   // 敵の種類

        // 初期座標
        x = random.nextInt(680);
        y = 150;

        // 大きさ
        width = 100;
        height = 100;

        // 変化量
        int randomNum = random.nextInt(2);
        if (randomNum == 0) dx = 10;
        else dx = -10;
        dy = 2;

        addEnemyBallCount = 0;
        addEnemyBallEndPoint = 10;
        dScore = 5;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, this);
    }

    // 縦移動
    public void move(){

        // 敵の弾生成 のタイミング
        addEnemyBallCount += 1;
        if(addEnemyBallCount%addEnemyBallEndPoint == 0) {
            callAddEnemyBall();
        }
        
        // 画面外の時, 削除の関数 を呼び出す
        if(y >= 800) {
            shootingPanel.deleteEnemy(this);
        } 
        // 画面内の時, 移動
        else {
            x += dx;
            y += dy;
            if (x <= 0 || x >= 800 - width -30) {
                dx *= -1;
            }
        }
    }

    // 敵の弾生成を呼び出す
    public void callAddEnemyBall() {
        shootingPanel.addEnemyBall(this, 2);
    }
    
}
