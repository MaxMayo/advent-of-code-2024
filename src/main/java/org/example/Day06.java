package org.example;

import lombok.Getter;
import org.example.utils.AbstractDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day06 extends AbstractDay {

    public Day06(List<String> lines) {
        super(lines);
    }

    private class Grid {
        private final char[][] theGrid;
        private final int height;
        private final int width;
        public Grid(char[][] theGrid) {
            this.theGrid = theGrid;
            this.height = theGrid.length;
            this.width = theGrid[0].length;
        }

        public boolean isObstacle(Location location) {
            try {
                return theGrid[location.y()][location.x()] == '#';
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }

        boolean isWithinBounds(Location location) {
            return (location.x() >= 0 && location.x() < width && location.y() >= 0 && location.y() < height);
        }

        public void markVisited(Location location) {
            theGrid[location.y()][location.x()] = 'X';
        }
    }

    private record Location(int x, int y) {
        Location move(Direction direction) {
            return new Location(x + direction.getDx(), y + direction.getDy());
        }
    }

    @Getter
    private enum Direction {
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0);

        private final int dx;
        private final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        /**
         * Look at this table
         * <table>
         *     <tr><th>x</th><th>y</th></tr>
         *     <tr><td>0</td><td>1</td></tr>
         *     <tr><td>1</td><td>0</td></tr>
         *     <tr><td>0</td><td>-1</td></tr>
         *     <tr><td>-1</td><td>0</td></tr>
         *     <tr><td>0</td><td>1</td></tr>
         * </table>
         * When going clockwise by 90 degrees (down the table), the next x component is the previous y component.
         * The next y component is the negative of what the previous x component was.
         *
         * This would work if the top row wasn't where y=0. But y=0 there and ascends as you go down the table.
         * @return
         */
        Direction rotateClockwise() {
            int newDy = dx;
            int newDx = -dy;
            return Arrays.stream(Direction.values())
                    .filter(direction -> direction.getDx() == newDx && direction.getDy() == newDy)
                    .findFirst().orElseThrow();
        }

    }
    private List<Location> visitedLocations;
    private Grid grid;

    @Override
    public int partOne() {
        int height = lines.size();
        int width = lines.get(0).length();
        int startx = -1;
        int starty = -1;
        grid = new Grid(new char[height][width]);
        for (int i = 0; i <height; i++) {
            //load lines into grid
            grid.theGrid[i] = lines.get(i).toCharArray();
            // find ^ marking starting location
            if (lines.get(i).contains("^")) {
                startx = lines.get(i).indexOf('^');
                starty = i;
            }
        }

        visitedLocations = new ArrayList<>();
        Location currentLocation;
        Location nextLocation = new Location(startx, starty);
        Direction currentDirection = Direction.UP;
        while (grid.isWithinBounds(nextLocation)) {
            currentLocation = nextLocation;
            visitedLocations.add(currentLocation);
            grid.markVisited(currentLocation);
            // look in direction and rotate until clear
            while(grid.isObstacle(currentLocation.move(currentDirection))) {
                currentDirection = currentDirection.rotateClockwise();
            }
            nextLocation = currentLocation.move(currentDirection);
        }
        return (int) visitedLocations.stream().distinct().count();
    }

    @Override
    public int partTwo() {
        return 0;
    }
}
