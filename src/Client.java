import javax.swing.*;
import java.awt.*;

public class Client extends JPanel implements Runnable {
    public final int pixelSize = 4;
    public final int tileSize = 16;
    public final int tileSizeInPixels = pixelSize * tileSize;
    public final Point tileMapSize;
    public final Point screenSize;
    public final int FPS = 60;

    public Client(Point _tileMapSize){
        tileMapSize = _tileMapSize;
        screenSize = new Point(tileMapSize.x * tileSizeInPixels,
                               tileMapSize.y * tileSizeInPixels);
        //Set up window on initialization
        this.setPreferredSize(new Dimension(screenSize.x, screenSize.y));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
//      this.addKeyListener(InputManager.current());
//      this.addMouseListener(InputManager.current());
//      this.addMouseMotionListener(InputManager.current());
    }

    public void Setup(){
        //Start the game on asynchronous thread
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void paint(Graphics g) {
        //Draw window and create 2d drawing plane
        super.paint(g);
    }

    public void run(){ //Update frame
        //Declare variables
        final double drawInterval = 1000000000/(float)FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer;

        while(true){
            //Get time
            currentTime = System.nanoTime();

            //Figure out if time is past new frame time
            timer = currentTime - lastTime;
            delta += timer / drawInterval;
            lastTime = currentTime;
            if(delta <= 1) continue;

            //Draw frame and restart time till next frame
            repaint();
            delta--;
        }
    }
}
