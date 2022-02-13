/*
    Shootiong から呼び出される, start() を呼び出される

    敵の生成・削除の"呼び出し" を担当
    ShootingPanel の addEnemy() を呼び出し、enemyList に敵を追加
        → ShootingPanel() で敵が生成される
*/

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.*;
import java.util.Random;

public class EnemyController implements Runnable{

    Boolean isLoop;
    Thread runner;
    long timer;

    public static ShootingPanel shootingPanel;    // 敵の生成や削除,描画

    public Random random = new Random();

    public EnemyController() { 
        isLoop = true;
        shootingPanel = new ShootingPanel();
        timer = 0;
    }

    // Shooting から呼び出される
    // → run() を呼び出す
    public void start() {
    	if(runner==null){
      		runner=new Thread(this);   // 非同期処理を呼び出す
      		runner.start();
    	}
    }

    public void run() {
        // 敵の生成する関数(ShootingPanel.addEnemy())を呼び出す
        while(isLoop) {
            // 10 * 1000
            if(System.currentTimeMillis() - timer > 10 * 100) {
                timer = System.currentTimeMillis();
                int randomNum = random.nextInt(10);

                // 0〜3
                if (randomNum%10 < 4) {
                    shootingPanel.addEnemy(0);   // FirstEnemy
                } 
                // 4〜7
                else if (4 <= randomNum%10 && randomNum%10 < 7) {
                    shootingPanel.addEnemy(1);   // SecondEnemy
                } 
                // 8・9
                else {
                    shootingPanel.addEnemy(2);   // ThirdEnemy
                } 
                
            }
        }
    }

}
