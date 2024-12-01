package org.example.utils;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractDay {

    private final int day;

    protected final List<String> lines;
    protected final AbstractDay heldInstance;

    public AbstractDay(List<String> lines) {
        String className = this.getClass().getSimpleName();
        this.day = Integer.parseInt(className.substring(className.length() - 2));
        this.lines = lines;
        this.heldInstance = this;
    }

    public abstract int partOne();

    public abstract int partTwo();

}
