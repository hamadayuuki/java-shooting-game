
import javax.swing.*;
import java.awt.event.*;

public class ShootingFrame extends JFrame {

    public ShootingPanel panel;   // クラス ShootingPanel を使用するために定義
    public Shooting shooting;
    public Player player;

    public ShootingFrame() {

        panel = new ShootingPanel();   // 画面を呼び出す

        this.add(panel);   // 画面を追加
        panel.start();

        player = new Player();
        this.add(player);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                shooting.isLoop = false;
            }
        });

        // キーの入力検知機能を Keyboard に付与する
        this.addKeyListener(new Keyboard());

        this.setTitle("ShootingGame");
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}



