import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Random;
//import javax.swing.*;

// 上下左右に動く, 弾を発射する
public class Player extends Panel{
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image playerImage = toolkit.getImage(getClass().getResource("Pic/Player.png"));

    static public int x = 100;
    static public int y = 300;
    public static boolean l_down, r_down, u_down, d_down;

    public Player() { }

    // 主人公の描画
    public void draw(Graphics g) {
        g.drawImage(playerImage, x, y, 50, 50, this);
    }

    // 上下左右の移動
    public void move() {
        // ShootingPanel にて変数(*_down)は変化(true/false)する
        if(l_down) x -= 10;
    	if(r_down) x += 10;
    	if(u_down) y -= 10;
    	if(d_down) y += 10;
        confirmContactWithEnemy();
    }

    public void confirmContactWithEnemy() {
        for(Enemy e: shootingPanel.enemyList) {
            if((e.x <= x && x <= e.x + 40) && (e.y <= y && y <= e.y + 25)) {
                hp.hp -= 1;
            }
        }
    }
}
