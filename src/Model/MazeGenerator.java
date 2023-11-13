package Model;

import Renderables.Tiles.TileBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;

/*
* Used to design map for creatures
* */
public class MazeGenerator {
    private final int centerReservedSpots = 3;
    private final int designatedPathways = 4;
    private TileBuilder[][] map;
    public MazeGenerator(@NotNull Point _mazeSize){
        map = new TileBuilder[_mazeSize.x][_mazeSize.y];
    }

    public TileBuilder[][] generate(){
        clearMap();
        Point starts[] = selectStartingPositions();
        Point center[] = getCenter();
        HashMap<Integer, Point> allWalkways = new HashMap<>();
        for(Point start : starts){
            HashMap<Integer, Point> currentWalkway = new HashMap<>();
            Stack<Point> stack = new Stack<>();
            stack.push(start);
            currentWalkway.put(start.y * map.length + start.x, start);

            while(!pointWithinRange(stack.peek(), center)){
                Point curr = stack.peek();
                //continue
                //Map inspiration in art test
            }
//            while(!stack.peek().equals())
            //DFS till located center
            //If within that process another path is hit,
            //Save and backtrack some number of positions
        }

        for(Point pos : getCenter()){
            map[pos.y][pos.x] = TileBuilder.Empty;
        }

        return map;
    }

    private boolean pointWithinRange(Point curr, Point[] goal){
        return (curr.x >= goal[0].x && curr.x <= goal[goal.length - 1].x &&
                curr.y >= goal[0].y && curr.y <= goal[goal.length - 1].y);
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

    private Point[] getCenter(){
        Point toReturn[] = new Point[centerReservedSpots * centerReservedSpots];
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
                toReturn[(i - topLeft.y) * centerReservedSpots + (j - topLeft.x)] = new Point(j, i);
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
