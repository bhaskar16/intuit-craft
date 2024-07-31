package com.qbot.utility;

public class IDGenerator {
    private static IDGenerator instance;
    private int nextId;

    private IDGenerator() {
        this.nextId = 1;
    }

    public static synchronized IDGenerator getInstance() {
        if (instance == null) {
            instance = new IDGenerator();
        }
        return instance;
    }

    public synchronized int getNextId() {
        return nextId++;
    }
}
