package org.example;

import org.example.utils.AbstractDay;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Day08 extends AbstractDay {
    public Day08(List<String> lines) {
        super(lines);
    }

    private boolean isTower(char character) {
        return Character.isAlphabetic(character) || Character.isDigit(character);
    }

    private int width;
    private int height;
    private char[][] originalMap;

    @Override
    public long partOne() {
        // load map into grid
        width = lines.getFirst().length();
        height = lines.size();
        originalMap = new char[height][width];
        for (int i = 0; i < height; i++) {
            originalMap[i] = lines.get(i).toCharArray();
        }

        // note all tower locations and group them sensibly
        Map<Character, List<Point>> towerLocations = new HashMap<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                char character = originalMap[row][column];
                if (isTower(character)) {
                    if (!towerLocations.containsKey(character)) {
                        towerLocations.put(character, new ArrayList<>());
                    }
                    towerLocations.get(character).add(new Point(row, column));
                }
            }
        }

        // for each group of towers, find their antinodes. put them on a map if they fit.
        // for each group
        Map<Character, List<Point>> antinodes = new HashMap<>();
        towerLocations.entrySet().forEach(entry ->
                {
                    char character = entry.getKey();
                    antinodes.put(character, new ArrayList<>());
                    List<Point> towersForCharacter = entry.getValue();
                    int size = towersForCharacter.size();
                    for (int i = 0; i < size - 1; i++) {
                        Point towerA = towersForCharacter.get(i);
                        for (int j = i + 1; j < size; j++) {
                            Point towerB = towersForCharacter.get(j);
                            int dx = towerA.x - towerB.x;
                            int dy = towerA.y - towerB.y;
                            // antinode closer to A
                            Point antinodeA = new Point(towerA.x + dx, towerA.y + dy);
                            // antinode closer to B
                            Point antinodeB = new Point(towerB.x - dx, towerB.y - dy);
                            if (withinBounds(antinodeA)) antinodes.get(character).add(antinodeA);
                            if (withinBounds(antinodeB)) antinodes.get(character).add(antinodeB);
                        }
                    }
                }
        );

        return antinodes.values().stream()
                .filter(points -> !points.isEmpty())
                .flatMap(Collection::stream)
                .distinct()
                .count();
    }

    private boolean withinBounds(Point point) {
        return (point.x >= 0 && point.y >= 0 && point.x < width && point.y < height);
    }

    @Override
    public long partTwo() {
        // load map into grid
        width = lines.getFirst().length();
        height = lines.size();
        originalMap = new char[height][width];
        for (int i = 0; i < height; i++) {
            originalMap[i] = lines.get(i).toCharArray();
        }

        // note all tower locations and group them sensibly
        Map<Character, List<Point>> towerLocations = new HashMap<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                char character = originalMap[row][column];
                if (isTower(character)) {
                    if (!towerLocations.containsKey(character)) {
                        towerLocations.put(character, new ArrayList<>());
                    }
                    towerLocations.get(character).add(new Point(row, column));
                }
            }
        }

        // for each group of towers, find their antinodes. put them on a map if they fit.
        // for each group
        Map<Character, List<Point>> antinodes = new HashMap<>();
        towerLocations.entrySet().forEach(entry ->
                {
                    char character = entry.getKey();
                    antinodes.put(character, new ArrayList<>());
                    List<Point> towersForCharacter = entry.getValue();
                    int size = towersForCharacter.size();
                    // each tower is also an anti-node
                    if (towersForCharacter.size() > 1) {
                        towersForCharacter.forEach(tower -> antinodes.get(character).add(tower));
                    }
                    for (int i = 0; i < size - 1; i++) {
                        Point towerA = towersForCharacter.get(i);
                        for (int j = i + 1; j < size; j++) {
                            Point towerB = towersForCharacter.get(j);
                            int dx = towerA.x - towerB.x;
                            int dy = towerA.y - towerB.y;
                            // add A-end nodes until OOB
                            boolean withinBounds = true;
                            int k = 1;
                            while(withinBounds) {
                                Point antinodeA = new Point(towerA.x + (dx * k), towerA.y + (dy * k));
                                if (withinBounds(antinodeA)) {
                                    antinodes.get(character).add(antinodeA);
                                } else {
                                    withinBounds = false;
                                }
                                k++;
                            }
                            withinBounds = true;
                            k = 1;
                            // same for B-nodes
                            while(withinBounds) {
                                Point antinodeB = new Point(towerB.x - (dx * k), towerB.y - (dy * k));
                                if (withinBounds(antinodeB)) {
                                    antinodes.get(character).add(antinodeB);
                                } else {
                                    withinBounds = false;
                                }
                                k++;
                            }
                        }
                    }
                }
        );

        return antinodes.values().stream()
                .filter(points -> !points.isEmpty())
                .flatMap(Collection::stream)
                .distinct()
                .count();
    }
}
