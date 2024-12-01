package org.example;

import org.example.utils.AbstractDay;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Day01 extends AbstractDay {

    public Day01(List<String> lines) {
        super(lines);
    }

    @Override
    public int partOne() {
        PriorityQueue<Integer> left = new PriorityQueue<>();
        PriorityQueue<Integer> right = new PriorityQueue<>();

        lines.stream()
                .map(s -> s.split("\\s+"))
                .forEach(array -> {
                    left.add(Integer.parseInt(array[0]));
                    right.add(Integer.parseInt(array[1]));
                });

        int sum = 0;
        int size = left.size();
        for (int i = 0; i < size; i++) {
            sum += Math.abs(left.poll() - right.poll());
        }

        return sum;
    }

    @Override
    public int partTwo() {
        List<Integer> left = new ArrayList();
        List<Integer> right = new ArrayList();

        lines.stream().map(
                s -> s.split("\\s+")
        ).forEach(array -> {
           left.add(Integer.parseInt(array[0]));
           right.add(Integer.parseInt(array[1]));
        });

        int size = left.size();
        int sum = 0;
        for (int i = 0; i < size; i++) {
            int current = left.get(i);
            sum += current * occurrencesInList(current, right);
        }
        return sum;
    }

    public static int occurrencesInList(int candidate, List<Integer> list) {
        return (int) list.stream().filter(num -> num.equals(candidate)).count();
    }

}
