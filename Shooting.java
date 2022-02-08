/*

    ・画面の生成
    ・ShootingPanel を呼び出す
        → ShootingPanel から他の画面を呼び出している

*/

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


public class Shooting extends Frame implements KeyListener{
    public static ShootingFrame  shootingFrame;
    public static boolean isLoop;
    public static Keyboard keyboard;
    public static ShootingPanel shootingPanel;    // 敵の生成や削除,描画
    public static EnemyController EnemyController;

    public Shooting() {

        // 画面を描画する
        shootingPanel = new ShootingPanel();
        add(shootingPanel);
		shootingPanel.start();
        
        // 敵の生成を行う
        EnemyController = new EnemyController();
		EnemyController.start();
        

        //KeyListener の登録
		addKeyListener(this);
		addWindowListener( new WindowEventHandler() );

        // 主人公の生成

        
        //shootingFrame = new ShootingFrame();

        /*
        shootingFrame = new ShootingFrame();
        isLoop = true;
        
        Graphics graphics = shootingFrame.panel.image.getGraphics();   // 左上(0,0) , 右下(X_Max,Y_Max)
        shootingPanel = new ShootingPanel();
        shootingPanel.start();
    
        long startTime;
        int fps = 30;

        ShootingScreenEnum screenType = ShootingScreenEnum.START;

        int playerX = 0; 
        int playerY = 0;
        ArrayList<Ball> playerBallList = new ArrayList<>();
        ArrayList<Ball> enemyBallList = new ArrayList<>();
        ArrayList<Enemy> enemyList = new ArrayList<>();

        boolean isShootedBall = false;

        Random random = new Random();

        int score = 0;
        int level = 0;
        long levelUpTimer = 0;   // レベルの上がる時間
        int playerHp = 5;
        int enemyType = 0;   // 敵の種類
        
        // 背景
        // ShootingPanel の image を使い描画を行っている
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0 , 0 , 800 , 800);

        while(isLoop) {

            

            
            switch (screenType) {

                // ゲームスタート
                case START:
                    // 初期化
                    score = 0;
                    level = 0;
                    levelUpTimer = 0;

                    // START という文字列を描画
                    Font font = new Font("HG行書体" , Font.PLAIN , 40);
                    graphics.setColor(Color.BLACK);
                    graphics.setFont(font);
                    FontMetrics metrics1 = graphics.getFontMetrics(font);
                    graphics.drawString("START" , 400 - (metrics1.stringWidth("START")/2) , 150);

                    // Press SPACE Key! という文字列を描画
                    font = new Font("HG行書体" , Font.PLAIN , 20);
                    graphics.setColor(Color.RED);
                    graphics.setFont(font);
                    metrics1 = graphics.getFontMetrics(font);
                    graphics.drawString("Press SPACE Key!" , 400 - (metrics1.stringWidth("Press SPACE Key!")/2) , 200);

                    // スペースキー押されたら画面遷移
                    // ゲーム開始
                    if(keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
                        screenType = ShootingScreenEnum.GAME;

                        playerBallList = new ArrayList<>();
                        enemyBallList = new ArrayList<>();
                        enemyList = new ArrayList<>();

                        // 主人公の初期位置を初期化
                        playerX = 400;
                        playerY = 700;
                        level = 0;
                    }

                    break;
               
                // ゲーム中
                case GAME:
                    // レベルアップ, 10秒ごと
                    if(System.currentTimeMillis() - levelUpTimer > 10 * 1000) {
                        levelUpTimer = System.currentTimeMillis();
                        level ++;
                    }

                    // 主人公を描画
                    graphics.setColor(Color.BLACK);
                    graphics.fillRect(playerX+20 , playerY-10 , 10 , 10);   // 本体_上
                    graphics.fillRect(playerX , playerY , 50 , 15);   // 本体_下
                    

                    // 作成したボール全てを描画する
                    //for(Ball ball : ballList) {
                    graphics.setColor(Color.BLACK);
                    for(int i = 0; i < playerBallList.size(); i++) {
                        Ball playerBall = playerBallList.get(i);
                        graphics.setColor(Color.BLACK);
                        graphics.fillRect(playerBall.x , playerBall.y , 5 , 5);
                        playerBall.y -= 20;
                        // 画面外にいくと弾を削除
                        if (playerBall.y < 0) {
                            playerBallList.remove(i);
                            i--;
                        }

                        // 敵との当たり判定
                        for (int j = 0; j < enemyList.size(); j++) {
                            Enemy enemy = enemyList.get(j);
                            if ( (playerBall.x >= enemy.x && playerBall.x <= enemy.x + 30 + 5) && (playerBall.y >= enemy.y && playerBall.y <= enemy.y + 20 + 5) ) {
                                enemyList.remove(j);   // 敵 を削除
                                j--;
                                playerBallList.remove(i);   // 主人公の弾 を削除
                                i--;
                                score += 1;   // スコアを増やす
                            }
                        }
                    }

                    // ランダムな位置に敵を発生させる
                    // 条件? Trueの時 ： Falseの時
                    if(random.nextInt(level<10 ? 30-level*2:10) == 1) {
                        //enemyList.add(new Enemy(random.nextInt(800) , 0));
                        shootingPanel.addEnemy(enemyType);
                    }

                    // 敵を全て表示
                    //for(Enemy enemy : enemyList) {
                    graphics.setColor(Color.RED);
                    for(int i = 0; i < enemyList.size(); i++) {
                        Enemy enemy = enemyList.get(i);
                        graphics.setColor(Color.RED);
                        graphics.fillRect(enemy.x , enemy.y , 30 , 10);
                        graphics.fillRect(enemy.x + 10 , enemy.y + 10 , 10 , 10);
                        enemy.y += 10;

                        // 画面外にいくと敵を削除
                        if (enemy.y > 800) {
                            enemyList.remove(i);
                            i--;
                        }

                        // 敵の弾を作成
                        if(random.nextInt(level<10 ? 80-level*3:30) == 1) {
                            enemyBallList.add(new Ball(enemy.x , enemy.y));
                        }

                         // 敵 と 主人公 の当たり判定
                         if( (enemy.x >= playerX && enemy.x <= playerX+30 && enemy.y>=playerY && enemy.y<=playerY+20) || (enemy.x+30 >= playerX && enemy.x+30 <= playerX+30 && enemy.y+20>=playerY && enemy.y+20<=playerY+20) ) {
                            playerHp -= 1;
                            if (playerHp <= 0) {
                                screenType = ShootingScreenEnum.GAMEOVER;
                            }
                        }
                    }

                    // 敵の弾を全てを描画する
                    //for(Ball ball : ballList) {
                    for(int i = 0; i < enemyBallList.size(); i++) {
                        Ball enemyBall = enemyBallList.get(i);
                        graphics.setColor(Color.RED);
                        graphics.fillRect(enemyBall.x , enemyBall.y , 5 , 5);
                        enemyBall.y += 20;

                        // 画面外にいくと弾を削除
                        if (enemyBall.y < 0) {
                            enemyBallList.remove(i);
                            i--;
                        }

                        // 敵の弾 と 主人公 の当たり判定
                        if(enemyBall.x >= playerX && enemyBall.x <= playerX+30 && enemyBall.y>=playerY && enemyBall.y<=playerY+20) {
                            playerHp -= 1;
                            if (playerHp <= 0) {
                                screenType = ShootingScreenEnum.GAMEOVER;
                            }
                        }
                    }

                    // スコアの表示
                    graphics.setColor(Color.BLACK);
                    graphics.setFont(new Font("SansSerif" , Font.PLAIN , 20));
                    graphics.drawString("SCORE : " + score , 400 , 300);
                    graphics.drawString("LEVEL : " + level , 400 , 350);

                    // 上移動
                    if( keyboard.isKeyPressed(KeyEvent.VK_UP) && 0 < playerX ) {
                        playerY -= 10;
                    }
                    // 下移動
                    if( keyboard.isKeyPressed(KeyEvent.VK_DOWN) && playerX < (800-50) ) {
                        playerY += 10;
                    }
                    // 左移動
                    if( keyboard.isKeyPressed(KeyEvent.VK_LEFT) && 0 < playerX ) {
                        playerX -= 10;
                    }
                    // 右移動
                    if( keyboard.isKeyPressed(KeyEvent.VK_RIGHT) && playerX < (800-50) ) {
                        playerX += 10;
                    }

                    // 弾発射
                    // ボタン押し続けることでの連射を禁止 (isShootedBall)
                    if(Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
                        if(!isShootedBall) {
                            playerBallList.add( new Ball(playerX + 25 , playerY) );   // ボールを作成する
                            isShootedBall = true;
                        }
                    }
                    if(Keyboard.isKeyReleased(KeyEvent.VK_SPACE)) {
                        isShootedBall = false;
                    }

                    break;

                // ゲームオーバー
                case GAMEOVER:
                    // 塗りつぶし
                    // ShootingPanel の image を使い描画を行ってい

                    // Game Over という文字列を描画
                    font = new Font("HG行書体" , Font.PLAIN , 40);
                    graphics.setColor(Color.BLACK);
                    graphics.setFont(font);
                    metrics1 = graphics.getFontMetrics(font);
                    graphics.drawString("Game Over" , 400 - (metrics1.stringWidth("Game Over")/2) , 150);

                    // Your Score ? という文字列を描画
                    font = new Font("HG行書体" , Font.PLAIN , 20);
                    graphics.setFont(font);
                    metrics1 = graphics.getFontMetrics(font);
                    graphics.drawString("Your Score" + score , 400 - (metrics1.stringWidth("Your Score" + score)/2) , 250);
                    
                    // Press SPACE Key! という文字列を描画
                    font = new Font("HG行書体" , Font.PLAIN , 40);
                    graphics.setColor(Color.RED);
                    graphics.setFont(font);
                    metrics1 = graphics.getFontMetrics(font);
                    graphics.drawString("Press ESC to Return Start Screen" , 400 - (metrics1.stringWidth("Press ESC to Return Start Screen")/2) , 400);

                    // スペースキー押されたら画面遷移
                    // ゲーム開始
                    if(keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                        screenType = ShootingScreenEnum.START;
                    }
                    break;

            }

            startTime = System.currentTimeMillis();
            
            // 描画
            // バッファした結果を表示
            shootingFrame.panel.draw();

            try {
                long runTime = System.currentTimeMillis() - startTime;
                if(runTime <= (1000/fps)) {
                    // 画面描画の待機時間を設定
                    Thread.sleep( (1000/fps) - runTime );
                }
                
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(System.currentTimeMillis() - startTime);   // 実行時間を計測
            
        }
        */
    }

    // KeyEventの登録
	// キーを押しているとき
	@Override
	public void keyPressed( KeyEvent e ){
		int  command = e.getKeyCode();
		switch( command ){
		case KeyEvent.VK_UP:
			System.out.println("↑ UP");
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("↓ UP");
		case KeyEvent.VK_LEFT:
			System.out.println("← UP");
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("→ UP");
			break;
		case KeyEvent.VK_SPACE:
			System.out.println("空白 UP");
			break;
		}
	}
	
	// キーを離した時
	@Override
	public void keyReleased( KeyEvent e ){
		int  command = e.getKeyCode();
		switch( command ){
		case KeyEvent.VK_UP:
			System.out.println("↑ DOWS");
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("↓ DOWN");
		case KeyEvent.VK_LEFT:
			System.out.println("← DOWN");
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("→ DOWN");
			break;
		case KeyEvent.VK_SPACE:
			System.out.println("空白 DOWN");
			break;
		}

	}
	
	// 以下はこのプログラムでは未使用
	// ただし，何もしなくてもOverrideする必要がある．
	@Override
	public void keyTyped( KeyEvent e ){

	}

    public static void main( String [] args ){
		Shooting appli = new Shooting();
		// サイズ設定、可視化
		appli.setSize( 800, 800 );
		appli.setVisible( true );

	}
}

// アプリケーション用のウインドウリスナー
class WindowEventHandler extends WindowAdapter {
    public void windowClosing( WindowEvent ev ){
      System.exit(0);
    }
  }
  
