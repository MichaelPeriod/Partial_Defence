package Model;

import Renderables.Tiles.TileBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;

/*
* Used to design map for creatures
* */

/*
* Map generation:
* Find valid starting points
*   designate into quadrants
*   select spots on edge
* Find center
* Start DFS traveling for each path
*   weight towards center
*   if hit other path travel back in dfs randomly
*   add path to list of paths
* Make each path
* */

public class MazeGenerator {
    private final int centerReservedSpots = 3;
    private final int designatedPathways = 4;
    private TileBuilder[][] map;

    public MazeGenerator(@NotNull Point _mazeSize) {
        map = new TileBuilder[_mazeSize.x][_mazeSize.y];
    }

    public TileBuilder[][] generate() {
        clearMap();
        Point starts[] = selectStartingPositions();
        Point center[][] = getCenter();
        ArrayList<Point> allPaths = new ArrayList<>();
        for (Point start : starts) {
            for (Point p : generatePath(start, center)) {
                allPaths.add(p);
            }
        }

        for (Point pos : allPaths) {
            map[pos.y][pos.x] = TileBuilder.Empty;
        }

        return map;
    }

    private ArrayList<Point> generatePath(Point start, Point[][] goal) {
        //Try more to generate basic paths ignoring collision

        ArrayList<Point> path = new ArrayList<>();

        Point goalCenter = goal[goal.length / 2][goal[0].length / 2];

        final int maxTravel = 4;

        Point currentPos = start;
        Point lastPos = currentPos;
        Point momentum = new Point(0, 0);
        while (!pointWithinRange(currentPos, goal)) {
            path.add(lastPos);
            if (momentum.x == 0 && momentum.y == 0) {
                if(Math.random() < .35){ //random direction
                    double dir = Math.random();
                    if(dir < .25){
                        momentum.x -= Math.random() * maxTravel;
                    } else if(dir < .5){
                        momentum.x += Math.random() * maxTravel;
                    } else if(dir < .75){
                        momentum.y -= Math.random() * maxTravel;
                    } else {
                        momentum.y += Math.random() * maxTravel;
                    }

                    if(momentum.x + currentPos.x > map[0].length - 1 ||
                            momentum.x + currentPos.x < 0 ||
                            momentum.y + currentPos.y > map.length - 1 ||
                            momentum.y + currentPos.y < 0) {

                        momentum = new Point(0,0);
                        continue;
                    }

                } else if (Math.abs(currentPos.x - goalCenter.x) > Math.abs(currentPos.y - goalCenter.y)) {
                    if (currentPos.x > goalCenter.x) {
                        momentum.x -= Math.random() * maxTravel;
                    } else {
                        momentum.x += Math.random() * maxTravel;
                    }
                } else {
                    if (currentPos.y > goalCenter.y) {
                        momentum.y -= Math.random() * maxTravel;
                    } else {
                        momentum.y += Math.random() * maxTravel;
                    }
                }
            }

            if (momentum.x > 0) {
                currentPos.translate(1, 0);
                momentum.translate(-1, 0);
            } else if (momentum.x < 0){
                currentPos.translate(-1, 0);
                momentum.translate(1, 0);
            }else if (momentum.y > 0){
                currentPos.translate(0, 1);
                momentum.translate(0, -1);
            }else{
                currentPos.translate(0,-1);
                momentum.translate(0, 1);
            }

            //Add points between the before and after
            //Sometimes just go a random direction

            lastPos = new Point(currentPos);

            //            if(currentPos.x < 0 || currentPos.x >= map[0].length ||
//               currentPos.y < 0 || currentPos.y >= map.length)
//                currentPos = lastPos;
//            else
//                path.add(currentPos);
//
//            //calculate weights
//            double totalDistance = currentPos.distance(goal);
//            double weightX = 0, weightY = 0;
//            if(Math.abs(currentPos.x - goal.x) > Math.abs(currentPos.y - goal.y)){
//                weightX = totalDistance / map.length;
//                if(currentPos.x - goal.x > 0)
//                    weightX *= -1;
//            } else {
//                weightY = totalDistance / map[0].length;
//                if(currentPos.y - goal.y > 0)
//                    weightY *= -1;
//            }
//
//            //Select direction randomly
//            double directionX = (2*(Math.random() - .5)) + weightX;
//            double directionY = (2*(Math.random() - .5)) + weightY;
//            int i = 0;
//            if(Math.abs(directionX) > Math.abs(directionY)){
//                if(directionX > 0)
//                    currentPos = new Point(currentPos.x + 1, currentPos.y);
//                else
//                    currentPos = new Point(currentPos.x - 1, currentPos.y);
//            } else {
//                if(directionY > 0)
//                    currentPos = new Point(currentPos.x, currentPos.y + 1);
//                else
//                    currentPos = new Point(currentPos.x, currentPos.y - 1);
//            }
        }

        return path;
    }

    private boolean pointWithinRange(Point curr, Point[][] goal){
        return (curr.x >= goal[0][0].x && curr.x <= goal[goal.length - 1][goal[0].length - 1].x &&
                curr.y >= goal[0][0].y && curr.y <= goal[goal.length - 1][goal[0].length - 1].y);
    }

    private Point[] selectStartingPositions(){
        Point pos[] = new Point[designatedPathways];
        int currentQuadrant = (int) Math.floor(Math.random() * 4); //0 top left then counterclockwize
        int quadrantSize = Math.floorDiv(Math.min(map.length, map[0].length), 2) - 1;

        for(int i = 0; i < designatedPathways; i++){
            Point currentPos = new Point(0, 0);
            boolean onSide = (Math.random() > .5);

            //Possibilities
                //In quadrant
                    //1 - sw x=0 y=1-half, !sw x=1-half y=0
                    //2 - sw x=0 y=half-end, !sw x=1-half y=end
                    //3 - sw x=end y=1-half, !sw x=half-end y=0
                    //4 - sw x=end y=half-end, !sw x=half-end y=end
                //Within quadrant
                    //On side wall
                    //Not on side wall

                    //sw x=0  !sw x=1-half - 1, 2
                    //sw x=end  !sw x=half-end - 3, 4
                    //sw y=1-half !sw y=0 - 1, 3
                    //sw y=half-end !sw y=end - 2, 4

            int randomPosOnWall = (int) (Math.random() * quadrantSize + 1);
            Point mapLengths = new Point(map[0].length - 1, map.length - 1);
            if(currentQuadrant < 2){ //left side
                if(onSide)
                    currentPos.x = 0;
                else
                    currentPos.x = randomPosOnWall;
            } else { //right size
                if(onSide)
                    currentPos.x = mapLengths.x;
                else
                    currentPos.x = mapLengths.x - randomPosOnWall;
            }

            if(currentQuadrant % 2 == 1) { //odd walls
                if(onSide)
                    currentPos.y = randomPosOnWall;
                else
                    currentPos.y = 0;
            } else { //even walls
                if(onSide)
                    currentPos.y = mapLengths.y - randomPosOnWall;
                else
                    currentPos.y = mapLengths.y;
            }

            boolean unique = true;
            for(Point other : pos){
                if(other == null) break;
                if(other.equals(currentPos)){
                    i--;
                    unique = false;
                    break;
                }
            }
            if(unique)
                pos[i] = currentPos;
            else
                continue;

            currentQuadrant++;
            if(currentQuadrant >= 4) currentQuadrant = 0;
        }

        return pos;
    }

    private Point[][] getCenter(){
        Point toReturn[][] = new Point[centerReservedSpots][centerReservedSpots];
        Point centerPoint =
                new Point(
                        Math.ceilDiv(map[0].length, 2),
                        Math.ceilDiv(map.length, 2)
                );
        int spaceBefore = Math.ceilDiv(centerReservedSpots, 2);
        int spaceAfter = Math.floorDiv(centerReservedSpots, 2);

        Point topLeft = new Point(centerPoint.x - spaceBefore,
                                  centerPoint.y - spaceBefore);
        Point bottomRight = new Point(centerPoint.x + spaceAfter,
                                      centerPoint.y + spaceAfter);

        for(int i = topLeft.y; i < bottomRight.y; i++){
            for(int j = topLeft.x; j < bottomRight.x; j++){
                toReturn[j - topLeft.x][i - topLeft.y] = new Point(j, i);
            }
        }

        return toReturn;
    }

    private void clearMap(){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = TileBuilder.Wall;
            }
        }
    }
}
