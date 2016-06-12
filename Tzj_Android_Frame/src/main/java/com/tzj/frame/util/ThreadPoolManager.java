package com.tzj.frame.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p> FileName： ThreadPoolManager</p>
 * <p>
 * Description：线程池管理
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 */
public class ThreadPoolManager {

    private ExecutorService executorService;

    private static ThreadPoolManager threadPoolManager;

    /**
     * 线程池中最多的线程数量
     */
    private static int threadNum;

    static {
        threadNum = Runtime.getRuntime().availableProcessors() * 4;
    }

    private ThreadPoolManager() {
        executorService = Executors.newFixedThreadPool(threadNum);
    }

    public static ThreadPoolManager getInstance() {
        if (threadPoolManager == null) {
            synchronized (ThreadPoolManager.class) {
                if (threadPoolManager == null) {
                    threadPoolManager = new ThreadPoolManager();
                }
            }
        }
        return threadPoolManager;
    }

    /**
     * 往线程池中添加任务
     *
     * @param runnable
     */
    public void addTask(Runnable runnable) {
        executorService.execute(runnable);
    }
}
