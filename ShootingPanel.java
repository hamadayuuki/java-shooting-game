import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.*;


public class ShootingPanel extends Panel implements Runnable, KeyListener{

    public BufferedImage image;   // 自分で画像を指定する
    static public Vector<Enemy> enemyList;   // static 変数, クラス内・外共有可能な変数にする必要がある, public ShootingPanel() では宣言しない(他のクラスの宣言時初期化されるから)

    Thread runner;		// 実行用スレッド

    int width,height;	// パネルの大きさ
  	Image off;		// 裏描画用オフスクリーンイメージ
  	Graphics offg;		// そのグラフィックス

    Player player = new Player();

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
            moveAllEnemy();
            movePlayer();
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
        // エラー
        for (Enemy e:enemyList){
            e.move();
        }
    }

    // 敵を生成
    public void addEnemy( int enemyType ){
		switch(enemyType){
      		case 0: enemyList.addElement(new FirstEnemy()); break;
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

    public void movePlayer() {
        player.move();
    }

    // 描画
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
		case KeyEvent.VK_SPACE: System.out.println("空白 UP"); break;
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
		case KeyEvent.VK_SPACE: System.out.println("空白 Down"); break;
		}

	}
	// 以下はこのプログラムでは未使用
	// ただし，何もしなくてもOverrideする必要がある．
	@Override
	public void keyTyped( KeyEvent e ){

	}
}
