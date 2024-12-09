package org.example.utils;

import java.util.List;

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

    public abstract long partOne();

    public abstract long partTwo();

}
