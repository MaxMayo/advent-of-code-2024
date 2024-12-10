package org.example;

import org.example.utils.AbstractDay;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Day10 extends AbstractDay {
    public Day10(List<String> lines) {
        super(lines);
    }

    private int[][] grid;
    private int height;
    private int width;

    private void loadGrid() {
        width = lines.getFirst().length();
        height = lines.size();
        grid = new int[height][width];
        for (int i = 0; i < lines.size(); i++) {
            char[] line = lines.get(i).toCharArray();
            for (int j = 0; j < width; j++) {
                grid[i][j] = line[j] - '0';
            }
        }
    }

    @Override
    public long partOne() {
        // load lines into grid
        loadGrid();

        // load trail starts
        List<Point> trailStarts = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (grid[row][column] == 0) {
                    trailStarts.add(new Point(column, row));
                }
            }
        }

        long sum = 0L;
        // for each trail-start, count trails
        for (Point trailStart : trailStarts) {
            List<Point> foundTrailEnds = new ArrayList<>();
            doTrail(trailStart.y, trailStart.x, 0, foundTrailEnds);
            sum += foundTrailEnds.stream().distinct().count();
        }

        // return sum of trails
        return sum;
    }

    private long doTrail(int row, int column, int currentHeight, List<Point> foundTrailEnds) {
        // off map or not one level higher
        if(!isValidStep(row, column, currentHeight)) return 0L;
        if(currentHeight == 9) {
            foundTrailEnds.add(new Point(column, row));
            return 1L;
        }
        long sumTrails = 0L;
        int nextHeight = currentHeight + 1;

        // above
        sumTrails += doTrail(row - 1, column, nextHeight, foundTrailEnds);
        // right
        sumTrails += doTrail(row, column + 1, nextHeight, foundTrailEnds);
        // below
        sumTrails += doTrail(row + 1, column, nextHeight, foundTrailEnds);
        // left
        sumTrails += doTrail(row, column - 1, nextHeight, foundTrailEnds);

        return sumTrails;
    }

    private boolean isWithinBounds(int row, int column) {
        return (row >= 0 && column >= 0 && row < height && column < width);
    }

    // if within bounds and one greater than current
    private boolean isValidStep(int row, int column, int heightLookedFor) {
        if(!isWithinBounds(row, column)) return false;
        // doing it this way for debugging purposes :|
        if(grid[row][column] == heightLookedFor) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long partTwo() {
        // load lines into grid
        loadGrid();

        // load trail starts
        List<Point> trailStarts = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (grid[row][column] == 0) {
                    trailStarts.add(new Point(column, row));
                }
            }
        }

        long sum = 0L;
        // for each trail-start, count trails
        for (Point trailStart : trailStarts) {
            List<Point> foundTrailEnds = new ArrayList<>();
            sum += doTrail(trailStart.y, trailStart.x, 0, foundTrailEnds);
//            sum += foundTrailEnds.stream().distinct().count();
        }

        // return sum of trails
        return sum;
    }
}
