package org.example;

import java.util.Arrays;
import java.util.List;
import org.example.utils.AbstractDay;

public class Day07 extends AbstractDay {

    public Day07(List<String> lines) {
        super(lines);
    }

    public record Equation(long testValue, List<Long> values) {

        public static Equation from(String line) {
            String[] split1 = line.split(":");
            long testValue = Long.parseLong(split1[0]);
            List<Long> values = Arrays.stream(
                split1[1]
                    .trim()
                    .split("\\s")
                )
                .map(Long::parseLong)
                .toList();
            return new Equation(testValue, values);
        }

    }

    //recursion?
    public long numPossibleVariants(Equation equation) {
        long target = equation.testValue();
        long numMatches = numVariantsFound(target, equation.values().getFirst(), equation.values.size() - 2, equation.values());
        return numMatches > 0 ? target : 0;
    }

    private long numVariantsFound(long target, long currentValue, int depthRemaining, List<Long> values) {
        if(depthRemaining == -1) {
            if (currentValue == target) {
                return 1;
            } else {
                return 0;
            }
        }
        int nextValueIndex = values.size() - depthRemaining - 1;
        long nextValue = values.get(nextValueIndex);
        long sum = 0;
        sum += numVariantsFound(target, currentValue + nextValue, depthRemaining - 1, values);
        sum += numVariantsFound(target, currentValue * nextValue, depthRemaining - 1, values);
        return sum;
    }

    @Override
    public long partOne() {
        return lines.stream()
            .map(Equation::from)
            .map(this::numPossibleVariants)
            .reduce(0L, Long::sum);
    }

    @Override
    public long partTwo() {
        return 0;
    }
}
