import javax.swing.*;
import java.awt.*;

public class GameView extends Client {
    @Override
    public void Setup(){
        super.Setup();
    }

    @Override
    public void paint(Graphics g) {
        //Draw window and create 2d drawing plane
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        //Draw grid
        g2d.setColor(Color.BLUE);
        g2d.fillRect(300, 100, 300, 300);
    }
}
