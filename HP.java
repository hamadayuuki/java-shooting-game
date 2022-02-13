import java.awt.*;
import java.awt.event.*;

public class HP extends Panel {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image hpImage = toolkit.getImage(getClass().getResource("Pic/Heart.png"));
    // 画面左上
    int x = 700;
    int y = 50;

    static public int hp = 5;

    public HP() { }

    public void draw(Graphics g) {
        for(int i = 0; i < hp; i++) {
            g.drawImage(hpImage, x - (i*45), y, 50, 50, this);
        }
    }

    public void decreaseHp(int decreaseNum) {
        // HPが0以下にならないようにする
        if(hp - decreaseNum >= 0) {
            hp -= decreaseNum;
        } else {
            hp = 0;
        }
    }

    public void resetHp() {
        hp = 5;
    }
}
