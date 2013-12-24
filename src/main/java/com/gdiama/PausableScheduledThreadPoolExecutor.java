package com.gdiama;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class PausableScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor implements Pausable {

    private PausableExecutorBehaviour pausableExecutorBehaviour = new PausableExecutorBehaviour();

    public PausableScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    public PausableScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    public PausableScheduledThreadPoolExecutor(int corePoolSize, RejectedExecutionHandler handler) {
        super(corePoolSize, handler);
    }

    public PausableScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }

    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pausableExecutorBehaviour.beforeExecute(t);
    }

    public void pause() {
        pausableExecutorBehaviour.pause();
    }

    public void resume() {
        pausableExecutorBehaviour.resume();
    }

    public boolean isPaused() {
        return pausableExecutorBehaviour.isPaused();
    }

}
