import DataPackets.*;
import Model.Model;
import Tiles.T_Wall;
import View.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;

public class Controller implements Runnable {
    private final int tickRate = 128;
    private final Point tileMapSize = new Point(16, 9);
    private final boolean isRunning = true;

    private final GameView view;
    private final Model model;

    public Controller(JFrame window){
        //Add a window to console
        view = new GameView(tileMapSize);
        window.add(view);

        //Display window
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //Build Model.Model and View
        model = new Model(tileMapSize);
        model.getTilemap(0).GrassFill();

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

        while(isRunning){
            //Get time
            currentTime = System.nanoTime();

            //Figure out if time is past new frame time
            timer = currentTime - lastTime;
            delta += timer / tickInterval;
            lastTime = currentTime;
            if(delta <= 1) continue;

            HandleInputs();
            model.Update();
            UpdateScreen();

            //Draw frame and restart time till next frame
            delta--;
        }
    }

    private void HandleInputs(){
        Queue<InputInfo> inputs = view.getInputQueue();

        while(!inputs.isEmpty()){
            InputInfo i = inputs.poll();

            switch (i.input){
                case Click -> {
                    Point screenPos = i.mouseEvent.getPoint();
                    Point tilePos = view.screenPosToTilePos(screenPos);

                    model.getTilemap(1).SetTile(new T_Wall(tilePos), tilePos);
                }
            }
        }
    }

    private void UpdateScreen(){
        for(RenderInfo renderInfo : model.getTileUpdates()){
            view.addToQueue(renderInfo);
        }
    }

    @Override
    public void run() {
        gameTick();
    }
}
