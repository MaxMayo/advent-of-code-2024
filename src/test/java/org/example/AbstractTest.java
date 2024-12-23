package org.example;

import lombok.Setter;
import org.example.utils.AbstractDay;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractTest {

    private final String day;
    private final Class<? extends AbstractDay> classToTest;

    private static final Path exampleFileBasePath = Path.of("src/test/resources/");
    private final Path exampleFilePath;
    protected final List<String> exampleList;

    private final Path exampleFilePath2;
    protected final List<String> exampleList2;

    private static final Path realFileBasePath = Path.of("src/main/resources/");
    private final Path realFilePath;
    protected final List<String> realList;

    protected final long expectedExampleOneResult;
    @Setter
    protected long expectedExampleTwoResult = -1;

    @Setter
    protected long partOneRealResult = -1;
    @Setter
    protected long partTwoRealResult = -1;

    public AbstractTest(long exampleOneResult) throws IOException, ClassNotFoundException {
        // get which day it is by looking at class name
        Pattern dayNumberPattern = Pattern.compile("Day(?<dayNumber>\\d+)Test");
        String className = this.getClass().getSimpleName();
        Matcher matcher = dayNumberPattern.matcher(className);
        if (matcher.matches()) {
            this.day = matcher.toMatchResult().group("dayNumber");
        } else {
            throw new ClassNotFoundException(className + "does not match pattern " + dayNumberPattern);
        }


        // get class being tested by day
        Class<?> foundClass = Class.forName("org.example.Day" + day);
        this.classToTest = (Class<? extends AbstractDay>) foundClass;

        // get input filepaths by day
        this.exampleFilePath = exampleFileBasePath.resolve(day + ".txt");
        this.exampleFilePath2 = exampleFileBasePath.resolve(day + "_2.txt");
        this.realFilePath = realFileBasePath.resolve(day + ".txt");

        // load files to list on startup so problems are caught immediately and the proper code doesn't need to handle it
        this.exampleList = Collections.unmodifiableList(Files.readAllLines(exampleFilePath));
        if(exampleFilePath2.toFile().exists()) {
            this.exampleList2 = Collections.unmodifiableList(Files.readAllLines(exampleFilePath2));
        } else {
            this.exampleList2 = Collections.emptyList();
        }
        this.realList = Collections.unmodifiableList(Files.readAllLines(realFilePath));

        // Needed for first run. Others will be needed for their test runs.
        this.expectedExampleOneResult = exampleOneResult;
    }

    private AbstractDay getInstanceOfAbstractDay(List<String> lines) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<? extends AbstractDay> constructor = (Constructor<? extends AbstractDay>) Arrays.stream(classToTest.getConstructors()).findFirst().get();
        return constructor.newInstance(lines);
    }

    @Test
    @DisplayName("Part One: example")
    @Order(1)
    void testPartOneExample() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var testedDay = getInstanceOfAbstractDay(exampleList);
        long actualResult = testedDay.partOne();
        assertEquals(expectedExampleOneResult, actualResult);
    }

    @Test
    @DisplayName("Part One: real")
    @Order(2)
    void testPartOneReal() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var testedDay = getInstanceOfAbstractDay(realList);
        long actualResult = testedDay.partOne();
        System.out.println("Result for part 1 real: " + actualResult);
        assertEquals(partOneRealResult, actualResult);
    }

    @Test
    @DisplayName("Part Two: example")
    @Order(3)
    void testPartTwoExample() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var myList = exampleList2.isEmpty() ? exampleList : exampleList2;
        var testedDay = getInstanceOfAbstractDay(myList);
        long result = testedDay.partTwo();
        assertEquals(expectedExampleTwoResult, result);
    }

    @Test
    @DisplayName("Part Two: real")
    @Order(4)
    void testPartTwoReal() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var testedDay = getInstanceOfAbstractDay(realList);
        long result = testedDay.partTwo();
        System.out.println("Result for part 2 real: " + result);
        assertEquals(partTwoRealResult, result);
    }
}
