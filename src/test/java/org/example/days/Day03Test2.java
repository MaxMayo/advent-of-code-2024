package org.example.days;

import org.example.Day03;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test2 {

    @Test
    @DisplayName("Part Two: example 2")
    void testPartTwoExample() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var testedDay = new Day03(List.of("xmul(2,4)&mul\n[3,7]!^don't()_\nmul(5,5)+mul(32,64](mul(11\n,8)undo()?mul(8,5))"));
        long result = testedDay.partTwo();
        assertEquals(48, result);
    }

}
