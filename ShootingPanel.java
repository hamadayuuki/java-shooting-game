import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.*;


public class ShootingPanel extends Panel implements Runnable, KeyListener{

    public BufferedImage image;   // 自分で画像を指定する
    static public Vector<Enemy> enemyList = new Vector<>();;   // static 変数, クラス内・外共有可能な変数にする必要がある, public ShootingPanel() では宣言しない(他のクラスの宣言時初期化されるから)
    static public ArrayList<Ball> playerBallList = new ArrayList<>();
    static public ArrayList<EnemyBall> enemyBallList = new ArrayList<>();

    Thread runner;		// 実行用スレッド

    int width = 800;
    int height = 800;	// パネルの大きさ
  	Image off;		// 裏描画用オフスクリーンイメージ
  	Graphics offg;		// そのグラフィックス

    Player player = new Player();
    Ball ball = new Ball();
    EnemyBall enemyBall;
    HP hp = new HP();
    Shooting shooting;

    public boolean isPressedSpaceKey = false;

    public static int score;

    public boolean isGameOver = false;

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image backgroundImage = toolkit.getImage(getClass().getResource("Pic/Background.png"));
    Image gameOverImage = toolkit.getImage(getClass().getResource("Pic/GameOver.png"));

    public ShootingPanel() {
        //super();
        enemyList = new Vector<Enemy>(); 
        playerBallList = new ArrayList<>();
        enemyBallList = new ArrayList<>();

        score = 0;

        setBackground(Color.white);
        addKeyListener(this);
    }

    // runner の初期化
    public void start(){
    	if(runner==null){
      		runner=new Thread(this);
      		runner.start();
    	}
  	}

    // start() が呼び出された時実行する
    public void run(){
        
    	while(runner!=null){
            moveAllEnemy();   // 敵の移動
            movePlayer();   // 主人公の移動
            movePlayerBall();   // 主人公の弾移動
            moveAllEnemyBall();   // 敵の弾移動
            repaint();   // update() を呼び出す
            try{
                runner.sleep(50);
            }	
            catch(InterruptedException e){}
    	}
  	}
    public void stop(){
        runner=null;
  	}

    // 敵の移動
    private void moveAllEnemy(){
        Enemy e;
        for (int i = 0; i < enemyList.size(); i++) {
            e = enemyList.get(i);
            e.move();
        }
    }

    // 敵と敵の弾を生成
    public void addEnemy( int enemyType ){
        switch(enemyType){
            case 0: 
                enemyList.addElement(new FirstEnemy());
                if(enemyList.size() > 0) {
                    addEnemyBall(enemyList.get(enemyList.size()-1), enemyType);
                }
                break;
            case 1:
                enemyList.addElement(new SecondEnemy()); 
                if(enemyList.size() > 0) {
                    addEnemyBall(enemyList.get(enemyList.size()-1), enemyType);
                }
                break;
            case 2:
                enemyList.addElement(new ThirdEnemy()); 
                if(enemyList.size() > 0) {
                    addEnemyBall(enemyList.get(enemyList.size()-1), enemyType);
                }
                break;
      }
	}

    // 敵を削除
    public void deleteEnemy(Enemy enemy){
        enemyList.remove(enemy);
	}

    // 主人公の移動
    public void movePlayer() {
        player.move();
    }
    // 主人公の弾を作成, Player.java から呼ばれる
    public void addPlayerBall() {
        playerBallList.add(new Ball());
    }
    public void movePlayerBall() {
        Ball ball;
        for(int i = 0; i < playerBallList.size(); i++) {
            ball = playerBallList.get(i);
            ball.move();
        }
    }
    // 主人公の弾を削除
    public void deletePlayerBall(Ball ball){
        playerBallList.remove(ball);
	}

    // 敵の弾を作成
    public void addEnemyBall(Enemy enemy, int enemyType) {
        switch(enemyType) {
            case 0:
                enemyBallList.add(new FirstEnemyBall(enemy.x, enemy.y));
                break;
            case 1:
                enemyBallList.add(new SecondEnemyBall(enemy.x, enemy.y));
                break;
            case 2:
                enemyBallList.add(new ThirdEnemyBall(enemy.x, enemy.y));
                break;
        }
        
    }
    public void moveAllEnemyBall() {
        EnemyBall enemyBall;
        for(int i = 0; i < enemyBallList.size(); i++) {
            enemyBall = enemyBallList.get(i);
            enemyBall.move();
        }
    }
    // 敵の弾を削除
    public void deleteEnemyBall(EnemyBall enemyBall){
        enemyBallList.remove(enemyBall);
	}

    // 画面の描画を行う
    public void update(Graphics g){ 
        paint(g);
    }
    public void paint(Graphics g) {

        // ゲームオーバー時の画面描画
        if (hp.hp <= 0) {
            // 初期化
            offg.clearRect(0,0,width,height);
            offg.drawImage(gameOverImage, 0, 0, width, height, this);
            offg = off.getGraphics();

            // Game Over という文字列を描画
            Font font = new Font("Arial" , Font.PLAIN , 40);   // 文字の大きさ
            offg.setColor(Color.WHITE);
            offg.setFont(font);
            FontMetrics metrics1 = offg.getFontMetrics(font);
            offg.drawString("Game Over" , 400 - (metrics1.stringWidth("Game Over")/2) , 200);   // 文字の場所 ("文字", x, y)

            // Your Score ? という文字列を描画
            font = new Font("Arial" , Font.PLAIN , 80);
            offg.setColor(Color.WHITE);
            offg.setFont(font);
            metrics1 = offg.getFontMetrics(font);
            offg.drawString("Your Score : " + score , 400 - (metrics1.stringWidth("Your Score : " + score)/2) , 400);
            
            // Press SPACE Key! という文字列を描画
            font = new Font("Arial" , Font.PLAIN , 40);
            offg.setColor(Color.GRAY);
            offg.setFont(font);
            metrics1 = offg.getFontMetrics(font);
            offg.drawString("Press ESC to ReStart" , 400 - (metrics1.stringWidth("Press ESC to ReStart")/2) , 600);

            isGameOver = true;
        } 
        
        // ゲーム中の画面描画
        else {
            // 画面の初期化, オフスクリーンイメージの取得
            if ( offg == null && width != getSize().width || height != getSize().height ){
                width=getSize().width;
                height=getSize().height;
                
                off=createImage(width,height);
                offg=off.getGraphics();
            }

            offg.clearRect(0,0,width,height);
            offg.drawImage(backgroundImage, 0, 0, width, height, this);

            // 敵の描画
            Enemy e;
            for (int i = 0; i < enemyList.size(); i++) {
                e = enemyList.get(i);
                e.draw(offg);
            }

            // 主人公の描画
            player.draw(offg);

            // 主人公の弾を描画
            Ball ball;
            for (int i = 0; i < playerBallList.size(); i++) {
                ball = playerBallList.get(i);
                ball.draw(offg);
            }

            // 敵の弾を描画
            EnemyBall enemyBall;
            for (int i = 0; i < enemyBallList.size(); i++) {
                enemyBall = enemyBallList.get(i);
                enemyBall.draw(offg);
            }

            // HPの描画
            hp.draw(offg);

            // Score の描画
            Font font = new Font("Arial" , Font.PLAIN , 30);
            offg.setColor(Color.WHITE);
            offg.setFont(font);
            FontMetrics metrics1 = offg.getFontMetrics(font);
            offg.drawString("Score : " + score , 700 - (metrics1.stringWidth("Your Score : " + score)/2) , 130);
        }
        
        g.drawImage(off , 0 , 0 , null);
    }

    // 主人公を動かすためのキー入力
    // KeyEventの登録
	// キーを押しているとき
	@Override
	public void keyPressed( KeyEvent e ){
		int  command = e.getKeyCode();
		switch( command ){
		case KeyEvent.VK_UP: player.u_down = true; break;
		case KeyEvent.VK_DOWN: player.d_down = true; break;
		case KeyEvent.VK_LEFT: player.l_down = true; break;
		case KeyEvent.VK_RIGHT: player.r_down = true; break;
		case KeyEvent.VK_SPACE: 
            if(!isPressedSpaceKey) {
                addPlayerBall();
                isPressedSpaceKey = true;
            }
            break;
		}
	}
	// キーを離した時
	@Override
	public void keyReleased( KeyEvent e ){
		int  command = e.getKeyCode();
		switch( command ){
		case KeyEvent.VK_UP: player.u_down = false; break;
		case KeyEvent.VK_DOWN: player.d_down = false; break;
		case KeyEvent.VK_LEFT: player.l_down = false; break;
		case KeyEvent.VK_RIGHT: player.r_down = false; break;
		case KeyEvent.VK_SPACE: 
            isPressedSpaceKey = false;
            break;
         case KeyEvent.VK_ESCAPE:
            // ゲームリセット
            if (isGameOver) {
                hp.hp = 10;
                score = 0;
                player.x = 340;
                player.y = 700;

                enemyList = new Vector<>();;   // static 変数, クラス内・外共有可能な変数にする必要がある, public ShootingPanel() では宣言しない(他のクラスの宣言時初期化されるから)
                playerBallList = new ArrayList<>();
                enemyBallList = new ArrayList<>();

                isGameOver = false;
            }
            break;
		}
       
	}
	// 以下はこのプログラムでは未使用
	// ただし，何もしなくてもOverrideする必要がある．
	@Override
	public void keyTyped( KeyEvent e ){ }

}
