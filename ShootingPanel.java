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

    int width,height;	// パネルの大きさ
  	Image off;		// 裏描画用オフスクリーンイメージ
  	Graphics offg;		// そのグラフィックス

    Player player = new Player();
    Ball ball = new Ball();
    EnemyBall enemyBall;
    HP hp = new HP();

    public boolean isPressedSpaceKey = false;

    public ShootingPanel() {
        //super();
        enemyList = new Vector<Enemy>(); 
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
            //System.out.println(playerBallList);
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
        // エラー
        for (Enemy e:enemyList){
            e.move();
        }
    }

    // 敵と敵の弾を生成
    public void addEnemy( int enemyType ){
		switch(enemyType){
      		case 0: 
              enemyList.addElement(new FirstEnemy()); 
              System.out.println(enemyList.size());
              if(enemyList.size() > 0) {
                addEnemyBall(enemyList.get(enemyList.size()-1));
              }
              break;
      		// case 1: circles.addElement(new WarpCircle()); break;
      		// case 2: circles.addElement(new ThroughCircle()); break;
			// case 3: circles.addElement(new HorizontalCircle()); break;
      		// case 4: circles.addElement(new VerticallyCircle()); break;
		}
	}

    // 敵を削除
    public void deleteEnemy(){
        System.out.println("delete Enemy");
	}

    // 主人公の移動
    public void movePlayer() {
        player.move();
    }
    // 主人公の弾を作成, Player.java から呼ばれる
    public void addPlayerBall() {
        System.out.println("addPlayerBall");
        playerBallList.add(new Ball());
    }
    public void movePlayerBall() {
        for(Ball ball:playerBallList) {
            ball.move();
        }
    }

    // 敵の弾を作成
    public void addEnemyBall(Enemy enemy) {
        enemyBallList.add(new EnemyBall(enemy.x, enemy.y));
    }
    public void moveAllEnemyBall() {
        for(EnemyBall enemyBall:enemyBallList) {
           enemyBall.move();
        }
    }

    // 画面の描画を行う
    public void update(Graphics g){ 
        paint(g);
    }
    public void paint(Graphics g) {
        // オフスクリーンイメージの取得
   		if ( offg == null && width != getSize().width || height != getSize().height ){
            width=getSize().width;
            height=getSize().height;
            
            off=createImage(width,height);
            offg=off.getGraphics();
        }

        offg.clearRect(0,0,width,height);

        // 敵の描画
        for(Enemy e:enemyList) {
            e.draw(offg);
        }
        // 主人公の描画
        player.draw(offg);
        // 主人公の弾を描画
        for(Ball ball: playerBallList) {
            ball.draw(offg);
        }
        // HPの描画
        hp.draw(offg);
        // 敵の弾を描画
        for(EnemyBall enemyBall: enemyBallList) {
            enemyBall.draw(offg);
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
		}

	}
	// 以下はこのプログラムでは未使用
	// ただし，何もしなくてもOverrideする必要がある．
	@Override
	public void keyTyped( KeyEvent e ){

	}
}
