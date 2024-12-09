package org.example.days;

import org.example.AbstractTest;
import org.example.Day09;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test extends AbstractTest {
    public Day09Test() throws IOException, ClassNotFoundException {
        super(1928L);
        setPartOneRealResult(6331212425418L);
        setExpectedExampleTwoResult(2858L);
        setPartTwoRealResult(6363268339304L);
    }

    @Test
    void runPart1Cheap() {
        Day09 day09 = new Day09(List.of("12345"));
        // 0 . . 1 1 1 . . . . 2 2 2 2 2
        // 0 2 2 1 1 1 2  2  2 . . . . . .
        // 0 2 4 3 4  5  12  14  16    -> 60
        // 0 1 2 3 4  5  6   7   8
        // 0 2 6 9 13 18 30 44 60
        long result = day09.partOne();
        assertEquals(60, result);
        System.out.println(result);
    }
}
