package org.example.days;

import org.example.AbstractTest;
import org.example.Day01;
import org.example.utils.AbstractDay;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test extends AbstractTest {

    private AbstractDay abstractDay;

    public Day01Test() throws IOException, ClassNotFoundException {
        super(11, 31);
        this.partOneRealResult = 1579939;
        this.partTwoRealResult = 20351745;
    }

//    @Test
//    void partOneExample() {
//        abstractDay = new Day01(this.exampleList);
//        int result = abstractDay.partOne();
//        System.out.println("Example result is " + result);
//        assertEquals(11, result);
//    }
//
//    @Test
//    void partOneReal() {
//        abstractDay = new Day01(this.realList);
//        int result = abstractDay.partOne();
//        System.out.println("Real result is : " + result);
//        assertEquals(1579939, result);
//    }
//
//    @Test
//    void partTwoExample() {
//        abstractDay = new Day01(this.exampleList);
//        int result = abstractDay.partTwo();
//        assertEquals(31, result);
//    }
//
//    @Test
//    void partTwoReal() {
//        abstractDay = new Day01(this.realList);
//        int result = abstractDay.partTwo();
//        assertEquals(20351745, result);
//    }


}