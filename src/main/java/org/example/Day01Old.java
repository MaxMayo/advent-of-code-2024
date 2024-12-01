package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Day01Old {

    public int sum() throws IOException {
        PriorityQueue<Integer> left = new PriorityQueue<>();
        PriorityQueue<Integer> right = new PriorityQueue<>();
        int count = 0;

        Stream<String> lines = Files.lines(Path.of("src/main/resources/01/input.txt"));

        lines.forEach(line -> {
            String[] nums = line.split("\\s+");
            left.add(Integer.parseInt(nums[0]));
            right.add(Integer.parseInt(nums[1]));
        });

        count = left.size();

        int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += Math.abs(left.poll() - right.poll());
        }
        return sum;
    }

    public int sum2() throws IOException {
        List<Integer> left = new ArrayList<>(), right = new ArrayList<>();
        int count = 0;

        Stream<String> lines = Files.lines(Path.of("src/main/resources/01/input.txt"));

        lines.map(s -> s.split("\\s+"))
                .forEach(pair -> {
                    left.add(Integer.parseInt(pair[0]));
                    right.add(Integer.parseInt(pair[1]));
                });

        count = left.size();
        int sum = 0;

        for (int i = 0; i < count; i++) {
            int leftNum = left.get(i);
            int timesInRight = (int) right.stream().filter(rightNum -> rightNum.equals(leftNum)).count();
            sum += leftNum * timesInRight;
        }

        return sum;
    }

}
