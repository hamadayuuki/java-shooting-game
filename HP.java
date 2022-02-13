import java.awt.*;
import java.awt.event.*;

public class HP extends Panel {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image hpImage = toolkit.getImage(getClass().getResource("Pic/Heart.png"));
    // 画面左上
    int x = 700;
    int y = 50;
    int width = 40;
    int height = 40;

    static public int hp = 10;

    public HP() { }

    // 描画
    public void draw(Graphics g) {
        for(int i = 0; i < hp; i++) {
            g.drawImage(hpImage, x - (i*(width - 5)), y, width, height, this);
        }
    }

    // HPの減少
    public void decreaseHp(int decreaseNum) {
        // HPが0以下にならないようにする
        if(hp - decreaseNum >= 0) {
            hp -= decreaseNum;
        } else {
            hp = 0;
        }
    }

    public void resetHp() {
        hp = 10;
    }

}
