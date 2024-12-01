package org.example.days;

import org.example.AbstractTest;

import java.io.IOException;

class Day01Test extends AbstractTest {

    public Day01Test() throws IOException, ClassNotFoundException {
        super(11);
        setExpectedExampleTwoResult(31);
        setPartOneRealResult(1579939);
        setPartTwoRealResult(20351745);
    }

}