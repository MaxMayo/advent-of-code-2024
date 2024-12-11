package org.example;

import java.util.ArrayList;
import org.example.utils.AbstractDay;

import java.util.List;

public class Day11 extends AbstractDay {
    public Day11(List<String> lines) {
        super(lines);
    }

    @Override
    public long partOne() {
        long sumStones = 0;
        int targetDepth = 25;
        String[] startingStones = lines.getFirst().split(" ");
        for (int i = 0; i < startingStones.length; i++) {
            sumStones += numStones(startingStones[i], targetDepth);
        }
        return sumStones;
    }

    // recursion 🎺🎺
    public long numStones(String stone, int targetDepth) {
        if (targetDepth == 0) return 1L;
        long sumStones = 0;
        List<String> newStones = new ArrayList<>();
        if(stone.equals("0")) {
            newStones.add("1");
        } else if (stone.length() % 2 == 0) {
            newStones.add(String.valueOf(Long.parseLong(stone.substring(0, stone.length() / 2))));
            newStones.add(String.valueOf(Long.parseLong(stone.substring(stone.length() / 2))));
        } else {
            newStones.add(String.valueOf(Long.parseLong(stone) * 2024));
        }
        for (String newStone : newStones) {
            sumStones += numStones(newStone, targetDepth - 1);
        }
        return sumStones;
    }

    @Override
    public long partTwo() {
        return 0;
    }
}
