import java.util.Random;
import java.awt.*;

public class SecondEnemy extends Enemy {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image img = toolkit.getImage(getClass().getResource("Pic/SecondEnemy.png"));
    
    public SecondEnemy() {

        type = 1;   // 敵の種類

        // 初期座標
        x = random.nextInt(750);
        y = 150;

        // 大きさ
        width = 80;
        height = 80;

        // 変化量
        dx = 20;
        dy = 0;

        addEnemyBallCount = 0;
        addEnemyBallEndPoint = 30;
        dScore = 3;
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
        
        // 画面外の時, 削除の関数を呼び出す
        if(y >= 500) {
            shootingPanel.deleteEnemy(this);
        } 
        // 画面内の時, 移動
        else {
            x += dx;
            if(x < 0 || x > 750) {
                dx *= -1;
            }
        }
    }

    // 敵の弾生成を呼び出す
    public void callAddEnemyBall() {
        shootingPanel.addEnemyBall(this, 1);
    }
    
}
