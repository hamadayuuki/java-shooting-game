/*
    ・敵の座標や色,動きを管理する
    ・動きは敵の種類によって変化するため,他のクラスで管理
*/

import java.awt.*;
import java.util.Random;

public abstract class Enemy extends Panel {
    int x , y;
    static int type = 0;
    static Color color;
    static ShootingPanel shootingPanel = new ShootingPanel();
    Random random;
    static EnemyController enemyController;

    public Enemy() { }

    // 描画
    public abstract void draw(Graphics g);

    // 移動
     public abstract void move();
}