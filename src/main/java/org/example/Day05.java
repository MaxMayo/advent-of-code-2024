package org.example;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.utils.AbstractDay;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day05 extends AbstractDay {

    public Day05(List<String> lines) {
        super(lines);
    }

    @RequiredArgsConstructor
    @Getter
    public class Rule implements Predicate<String> {

        private final String first;
        private final String second;

        @Override
        public boolean test(String s) {
            if (s.contains(first) && s.contains(second)) {
                return s.indexOf(first) < s.indexOf(second);
            } else {
                return true;
            }
        }
    }

    @Override
    public long partOne() {
        List<Predicate> rules = new ArrayList<>();
        List<String> pagesList = new ArrayList<>();

        boolean loadingRules = true;
        for (String line : lines) {
            if (line.isBlank() || line.isEmpty()) {
                loadingRules = false;
            } else {
                if (loadingRules) {
                    String[] arr = line.split("\\|");
                    rules.add(new Rule(arr[0], arr[1]));
                } else {
                    pagesList.add(line);
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < pagesList.size(); i++) {
            String pages = pagesList.get(i);
            boolean ordered = rules.stream().allMatch(p -> p.test(pages));
            if (ordered) {
                String[] arr = pages.split(",");
                int size = arr.length;
                int index = (size - 1) / 2;
                sum += Integer.parseInt(arr[index]);
            }
        }

        return sum;
    }

    @Override
    public long partTwo() {
        List<Predicate> rules = new ArrayList<>();
        List<String> pagesList = new ArrayList<>();
        List<String> reorderedLines = new ArrayList<>();

        boolean loadingRules = true;
        for (String line : lines) {
            if (line.isBlank() || line.isEmpty()) {
                loadingRules = false;
            } else {
                if (loadingRules) {
                    String[] arr = line.split("\\|");
                    rules.add(new Rule(arr[0], arr[1]));
                } else {
                    pagesList.add(line);
                }
            }
        }

        List<String> failedLines = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < pagesList.size(); i++) {
            String pages = pagesList.get(i);
            boolean ordered = rules.stream().allMatch(p -> p.test(pages));
            if (!ordered) {
                failedLines.add(pages);
            }
        }

        for (int i = 0; i < failedLines.size(); i++) {
            String failedLine = failedLines.get(i);
            List<Predicate> allRules = rules.stream()
                    .collect(Collectors.toList());

            // swap unordered ones and run all rules again
            for (int j = 0; j < allRules.size(); j++) {
                Rule rule = (Rule) allRules.get(j);
                if(!rule.test(failedLine)) {
                    String newLine = failedLine.replace(rule.getFirst(), "first");
                    newLine = newLine.replace(rule.getSecond(), "second");
                    newLine = newLine.replace("first", rule.getSecond());
                    newLine = newLine.replace("second", rule.getFirst());
                    failedLine = newLine;
                    j = 0;
                }
            }
            String finalLine = failedLine;
            reorderedLines.add(finalLine);
            // hopefully never raised
            if (rules.stream().noneMatch(rule -> rule.test(finalLine))) {
                System.err.println("awh shiet");
            }
            String[] arr = finalLine.split(",");
            int size = arr.length;
            int index = (size - 1) / 2;
            sum += Integer.parseInt(arr[index]);
        }


        return sum;
    }
}
