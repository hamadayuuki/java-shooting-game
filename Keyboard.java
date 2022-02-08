
import java.awt.event.*;
import java.util.ArrayList;

// ShootingFrame にて宣言済み
public class Keyboard extends KeyAdapter{

    public static ArrayList<Integer> inputStrList = new ArrayList<>();
    
    // 初期化
    public Keyboard() { }

    public boolean isKeyPressed(int keyCode) {
        return inputStrList.contains(keyCode);
    }

    public static boolean isKeyReleased(int keyCode) {
        return !inputStrList.contains(keyCode);
    }


    // キーが押された時
    @Override
    public void keyPressed(KeyEvent e) {
        if( !inputStrList.contains(e.getKeyCode() )) {
            inputStrList.add( e.getKeyCode() );
        }
    }

    // キーから手が離れた時
    @Override
    public void keyReleased(KeyEvent e) {
        inputStrList.remove( (Integer)e.getKeyCode() );
    }
}
