import javax.swing.*;
import java.awt.*;

public class Controller implements Runnable {
    final int tickRate = 128;
    final Point tileMapSize = new Point(16, 9);

    final GameView view;
    final Model model;

    public Controller(JFrame window){
        //Add a window to console
        view = new GameView(tileMapSize);
        window.add(view);

        //Display window
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //Build Model and View
        model = new Model(tileMapSize);
        model.GrassFill();
        model.SetTile(new T_Wall(new Point(0, 0)), 0, 0);

        view.Setup();

        Thread tickThread = new Thread(this);
        tickThread.start();
    }

    private void gameTick(){ //Runs every tick
        //Declare variables
        final double tickInterval = 1000000000/(float)tickRate;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer;

        while(true){
            //Get time
            currentTime = System.nanoTime();

            //Figure out if time is past new frame time
            timer = currentTime - lastTime;
            delta += timer / tickInterval;
            lastTime = currentTime;
            if(delta <= 1) continue;

            model.Update();

            for(RenderInfo r : model.getRenderInfo()){
                view.addToQueue(r);
            }

            //Draw frame and restart time till next frame
            delta--;
        }
    }

    @Override
    public void run() {
        gameTick();
    }
}
