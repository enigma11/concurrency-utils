package com.gdiama;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PausableExecutorBehaviour implements Pausable {

    private volatile boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();

    protected void beforeExecute(Thread t) {
        pauseLock.lock();
        try {
            while (isPaused) unpaused.await();
        } catch (InterruptedException ie) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    public boolean isPaused() {
        boolean isPausedCopy = false;
        pauseLock.lock();
        try {
            isPausedCopy = isPaused;
        } finally {
            pauseLock.unlock();
        }
        return isPausedCopy;
    }


}
