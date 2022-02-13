import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Random;
//import javax.swing.*;

// 上下左右に動く, 弾を発射する
public class Player extends Panel{
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image playerImage = toolkit.getImage(getClass().getResource("Pic/Player.png"));

    static public int x = 340;
    static public int y = 700;
    static public boolean l_down, r_down, u_down, d_down;

    public HP hp = new HP();
    //public ShootingPanel shootingPanel = new ShootingPanel();
    static ShootingPanel shootingPanel = new ShootingPanel();

    static public int width = 70;
    static public int height = 70;

    public Player() { }

    // 主人公の描画
    public void draw(Graphics g) {
        g.drawImage(playerImage, x, y, width, height, this);
    }

    // 上下左右の移動
    public void move() {
        // ShootingPanel にて変数(*_down)は変化(true/false)する
        if(l_down) x -= 10;
    	if(r_down) x += 10;
    	if(u_down) y -= 10;
    	if(d_down) y += 10;

        if(y <= 0) y = 0;
        if(y >= 700) y = 700;
        if(x <= 0) x = 0;
        if(x >= 730) x = 730;

        confirmContactWithEnemy();
    }

    // 敵との衝突判定
    private void confirmContactWithEnemy() {
        Enemy e;
        for(int i = 0; i < shootingPanel.enemyList.size(); i++) {
            e = shootingPanel.enemyList.get(i);
            if(((e.x - width) <= x && x <= e.x + e.width) && (e.y <= y && y <= e.y + e.height)) {
                shootingPanel.deleteEnemy(e);   // 敵を削除
                if (e.type == 0 || e.type == 1) {
                    hp.decreaseHp(1);   // HP減少
                } else if (e.type == 2) {
                    hp.decreaseHp(3);   // HP減少
                }
            }
        }
    }

}
