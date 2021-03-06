import java.util.Random;
import java.awt.*;

public class FirstEnemy extends Enemy {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image img = toolkit.getImage(getClass().getResource("Pic/FirstEnemy.png"));

    public FirstEnemy() {
        
        type = 0;   // 敵の種類
        
        // 初期座標
        x = random.nextInt(750);
        y = 0;

        // 大きさ
        width = 50;
        height = 50;

        // 変化量
        dx = 0;
        dy = 10;

        addEnemyBallCount = 0;
        addEnemyBallEndPoint = 50;
        dScore = 1;
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
        if(y >= 800) {
            shootingPanel.deleteEnemy(this);
        } 
        // 画面内の時, 移動
        else {
            y += dy;
        }
    }

    // 敵の弾生成を呼び出す
    public void callAddEnemyBall() {
        shootingPanel.addEnemyBall(this, 0);
    }
    
}
