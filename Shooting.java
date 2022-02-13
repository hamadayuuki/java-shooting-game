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
    public static boolean isLoop;
    public static ShootingPanel shootingPanel;    // 敵の生成や削除,描画
    public static EnemyController EnemyController;

    public static Shooting appli;   // 画面描画, 画面遷移時に呼び出す
    ShootingScreenEnum screenType = ShootingScreenEnum.GAME;   // 画面の種類

    Graphics graphics;

    public Shooting() {

        //KeyListener の登録
        addKeyListener(this);
        addWindowListener( new WindowEventHandler() );

        // 画面を描画する
        shootingPanel = new ShootingPanel();
        add(shootingPanel);
        shootingPanel.start();
        
        // 敵の生成を行う
        EnemyController = new EnemyController();
        EnemyController.start();

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
	public void keyTyped( KeyEvent e ){ }

    public static void main( String [] args ){
		appli = new Shooting();
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
  
