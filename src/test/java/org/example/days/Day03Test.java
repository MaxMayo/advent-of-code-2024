package org.example.days;

import org.example.AbstractTest;
import org.example.Day03;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test extends AbstractTest {

    public Day03Test() throws IOException, ClassNotFoundException {
        super(161);
        setPartOneRealResult(174336360); // not 28476021, too low
        setExpectedExampleTwoResult(48);
        setPartTwoRealResult(88802350); // not 129087088, too high
    }

//    @Test
//    @DisplayName("Part Two: example 2")
//    void testPartTwoExample() throws InvocationTargetException, InstantiationException, IllegalAccessException {
//        var testedDay = new Day03(List.of("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"));
//        int result = testedDay.partTwo();
//        assertEquals(48, result);
//    }



}
