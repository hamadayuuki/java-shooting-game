/*
    ・敵の座標や色,動きを管理する
    ・動きは敵の種類によって変化するため,他のクラスで管理
*/

import java.awt.*;
import java.util.Random;

public abstract class Enemy extends Panel {
    int x , y;   // 表示時に使用する座標
    static int type = 0;   // 敵の種類 (0: First, 1: Second, 2: Third)
    int width, height;   // 大きさ
    int dx, dy;   // 変化量
    int addEnemyBallCount;   // 弾を生成するタイミングを測るカウント
    int addEnemyBallEndPoint;   // 弾を生成するタイミング
    int dScore;   // 得点の増加量

    static ShootingPanel shootingPanel = new ShootingPanel();
    static EnemyController enemyController;
    
    Random random = new Random();

    public Enemy() { }

    // 描画
    public abstract void draw(Graphics g);

    // 移動
     public abstract void move();
}