package com.javabyexamples.java.concurrency.threadsafety.atomicity.racecondition.readmodifywrite;

import com.javabyexamples.java.concurrency.common.annotation.NotThreadSafe;

@NotThreadSafe
public class Incrementor3 implements Incrementor {

    private volatile int counter = 0;

    @Override
    public void increment() {
        counter++;
    }

    @Override
    public void reset() {
        counter = 0;
    }

    @Override
    public int get() {
        return counter;
    }
}
