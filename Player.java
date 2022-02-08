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
    static public boolean l_down, r_down, u_down, d_down;

    public HP hp = new HP();
    public ShootingPanel shootingPanel;

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

        if(y <= 0) y = 0;
        if(y >= 720) y = 720;
        if(x <= 0) x = 0;
        if(x >= 750) x = 750;

        confirmContactWithEnemy();
    }

    private void confirmContactWithEnemy() {
        for(Enemy e: shootingPanel.enemyList) {
            if((e.x <= x && x <= e.x + 40) && (e.y <= y && y <= e.y + 25)) {
                e.x = 2000;
                e.y = 2000;
                hp.decreaseHp(1);
            }
        }
    }
}
