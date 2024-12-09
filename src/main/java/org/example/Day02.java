package org.example;

import org.example.utils.AbstractDay;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.List;

public class Day02 extends AbstractDay {


    public Day02(List<String> lines) {
        super(lines);
    }

    @Override
    public long partOne() {
        return (int) lines.stream().map(s -> s.split("\\s"))
                .map(stringArray -> {
                    List<Integer> integerList = new ArrayList<>();
                    for (int i = 0; i < stringArray.length; i++) {
                        integerList.add(Integer.parseInt(stringArray[i]));
                    }
                    return integerList;
                })
                //works
                .filter(level -> isSafe(level))
                //experiment
                .filter(report -> {
                    try {
                        return isNumVarianceWithinBounds(report);
                    } catch (InvalidAlgorithmParameterException e) {
                        return false;
                    }
                })
                .filter(report -> {
                    try {
                        return areAllInSameDirection(report);
                    } catch (InvalidReportException e) {
                        return false;
                    }
                })
                .count();


    }

    public static boolean isSafe(List<Integer> level) {
        boolean goesUp = (level.get(0) - level.get(1)) < 0;
        int previous;
        int current = level.get(0);
        for (int i = 1; i < level.size(); i++) {
            previous = current;
            current = level.get(i);
            if (goesUp) {
                if (current < previous) {
                    // goes down?
                    return false;
                } else if ((current - previous) > 3 || (current - previous) < 1) {
                    // is not within limit?
                    return false;
                }
            } else {
                //goes down
                if (current > previous) {
                    //goes up
                    return false;
                } else if ((previous - current) > 3 || (previous - current) < 1) {
                    return false;
                }
            }
        }
        System.out.println("Safe line is: " + level.toString());
        return true;
    }

    // remove first dodgy one and try again
    public static boolean isSafeDampened(List<Integer> level, boolean dampened, Boolean isGoesUpKnown) {
        boolean goesUp = isGoesUpKnown != null ? isGoesUpKnown : (level.get(0) - level.get(1)) < 0;
        System.out.println("goes up? : " + goesUp);
        if (level.get(0).equals(level.get(1))) {
            if (dampened) return false;
            return isSafeDampened(level.subList(1, level.size()), true, null);
        }
        int previous;
        int current = level.get(0);
        for (int i = 1; i < level.size(); i++) {
            previous = current;
            current = level.get(i);
            if ((goesUp && ((current - previous) > 3 || (current - previous) < 1)) || (!goesUp && ((previous - current) > 3 || (previous - current) < 1))) {
                // is not within limit?
                if (dampened) {
                    System.out.println("Bad line: " + level);
                    System.out.println();
                    return false;
                }
                List<Integer> levelWithDampening = List.copyOf(level.subList(0, i));
                List<Integer> levelWithDampeningSecond = List.copyOf(level.subList(i + 1, level.size()));
                List<Integer> combined = new ArrayList<>();
                combined.addAll(levelWithDampening);
                combined.addAll(levelWithDampeningSecond);
                List<Integer> levelWithDampening2 = List.copyOf(level.subList(0, i - 1));
                List<Integer> levelWithDampeningSecond2 = List.copyOf(level.subList(i, level.size()));
                List<Integer> combined2 = new ArrayList<>();
                combined2.addAll(levelWithDampening2);
                combined2.addAll(levelWithDampeningSecond2);
//                System.out.println("Dampening line: " + level);
//                System.out.println("Removing: " + current);
                return isSafeDampened(combined, true, goesUp) || isSafeDampened(combined2, true, goesUp);
            }
        }

        System.out.println("Safe line is: " + level);
        System.out.println();
        return true;
    }

    public boolean dampen(boolean dampened) {
//        if (dampened) {
//            //already dampened
//            return false;
//        } else {
//            return
//        }
        return true;
    }

    @Override
    public long partTwo() {
        return (int) lines.stream().map(s -> s.split("\\s"))
                .map(stringArray -> {
                    List<Integer> integerList = new ArrayList<>();
                    for (int i = 0; i < stringArray.length; i++) {
                        integerList.add(Integer.parseInt(stringArray[i]));
                    }
                    return integerList;
                })
//                .filter(level -> isSafeDampened(level, false, null))
//                .filter(report -> reportFilter(report, false))
                //works
                .filter(this::brute)
                .count();
    }

    private boolean isNumVarianceWithinBounds(List<Integer> record) throws InvalidAlgorithmParameterException {
        boolean withinBounds = true;
        for (int i = 0; i < record.size() - 1; i++) {
            int variance = (Math.abs(record.get(i) - record.get(i + 1)));
            if (variance > 3 || variance < 1)
                throw new InvalidAlgorithmParameterException("Variance out of bounds: " + variance);
        }
        return true;
    }

    private boolean areAllInSameDirection(List<Integer> record) throws InvalidReportException {
        boolean isUpwards;
        int diff = record.get(1) - record.get(0);
        if (diff > 0) {
            isUpwards = true;
        } else if (diff < 0) {
            isUpwards = false;
        } else {
            throw new InvalidReportException("First two numbers are identical, can't/won't resolve direction");
        }
        for (int i = 0; i < record.size() - 1; i++) {
            int current = record.get(i);
            int next = record.get(i + 1);
            int difference = next - current;
            if (isUpwards) {
                // goes down
                if (difference < 0) throw new InvalidReportException("wrong direction");
            } else {
                // goes up
                if (difference > 0) throw new InvalidReportException("wrong direction");
            }
        }
        return true;
    }

    public class InvalidReportException extends Exception {
        public InvalidReportException(String message) {
            super(message);
        }
    }

    private boolean brute(List<Integer> report) {
        if (isSafe(report)) {
            return true;
        } else {
            for (int i = 0; i < report.size(); i++) {
                var sublistFirst = report.subList(0, i);
                var sublistSecond = report.subList(i + 1, report.size());
                List<Integer> copy = new ArrayList<>();
                copy.addAll(sublistFirst);
                copy.addAll(sublistSecond);
                if (isSafe(copy)) {
                    return true;
                }
            }
        }
        return false;
    }

//    private boolean reportFilter(List<Integer> report, boolean attempt2) {
//        int first = report.get(0);
//        int second = report.get(1);
//
//        // remove first if identical
//        if (first == second) {
//            if (attempt2) return false;
//            return reportFilter(copyListWithoutIndex(report, 0), true);
//        }
//        int previous;
//        int current = report.get(0);
//        boolean ascending = second > first;
//        for (int i = 1; i < report.size(); i++) {
//            previous = current;
//            current = report.get(i);
//
//            if (ascending) {
//                // is 3 or less
//
//            } else {
//
//            }
//
//            if ((ascending && current - previous > 1 && current - previous < 3) ||
//                    (!ascending && previous - current > 1 && previous - current < 3)) {
//                //good
//            } else {
//
//            }
//
//        }
//    }

    private static List<Integer> copyListWithoutIndex(List<Integer> report, int levelIndex) {
        var copy = List.copyOf(report);
        copy.remove(levelIndex);
        return copy;
    }
}
