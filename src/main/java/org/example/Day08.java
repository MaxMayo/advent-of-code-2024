package org.example;

import org.example.utils.AbstractDay;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day08 extends AbstractDay {
    public Day08(List<String> lines) {
        super(lines);
    }

    private boolean isTower(char character) {
        return Character.isAlphabetic(character) || Character.isDigit(character);
    }

    @Override
    public long partOne() {
        // load map into grid
        int width = lines.getFirst().length();
        int height = lines.size();
        char[][] originalMap = new char[height][width];
        for(int i = 0; i < height; i++) {
            originalMap[i] = lines.get(i).toCharArray();
        }

        // note all tower locations and group them sensibly
        Map<Character, List<Point>> towerLocations = new HashMap<>();
        for(int row = 0; row < height; row++) {
            for(int column = 0; column < width; column++) {
                char character = originalMap[row][column];
                if(isTower(character)) {
                    if(!towerLocations.containsKey(character)) {
                        towerLocations.put(character, new ArrayList<>());
                    }
                    towerLocations.get(character).add(new Point(row, column));
                }
            }
        }

        // for each group of towers, find their antinodes. put them on a map if they fit.



        return 0;
    }

    @Override
    public long partTwo() {
        return 0;
    }
}
