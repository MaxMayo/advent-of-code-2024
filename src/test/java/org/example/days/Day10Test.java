package org.example.days;

import org.example.AbstractTest;
import org.example.Day10;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class Day10Test extends AbstractTest {

    public Day10Test() throws IOException, ClassNotFoundException {
        super(36);
        setPartOneRealResult(674);
        setExpectedExampleTwoResult(81);
        setPartTwoRealResult(1372);
    }

    static Stream<Arguments> partOneArgs() {
        return Stream.of(
                arguments("""
                        0123
                        1234
                        8765
                        9876
                        """, 1),
                arguments("""
                        ...0...
                        ...1...
                        ...2...
                        6543456
                        7.....7
                        8.....8
                        9.....9
                        """, 2),
                arguments("""
                        ..90..9
                        ...1.98
                        ...2..7
                        6543456
                        765.987
                        876....
                        987....
                        """, 4),
                arguments("""
                        10..9..
                        2...8..
                        3...7..
                        4567654
                        ...8..3
                        ...9..2
                        .....01
                        """, 3),
                arguments("""
                        89010123
                        78121874
                        87430965
                        96549874
                        45678903
                        32019012
                        01329801
                        10456732
                        """, 36)
        );
    }

    @ParameterizedTest
    @MethodSource("partOneArgs")
    void testPartOne(String map, long expectedTrailCount) {
        List<String> lines = Arrays.asList(map.split("\n"));
        Day10 day10 = new Day10(lines);
        long result = day10.partOne();
        assertEquals(expectedTrailCount, result);
    }
}