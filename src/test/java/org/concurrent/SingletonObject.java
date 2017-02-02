package org.concurrent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonObject {
    private static SingletonObject instance;

    private SingletonObject() {
    }

    public static SingletonObject getInstance() {
        if (instance == null) {
            instance = new SingletonObject();
        }
        return instance;
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCounts = 100;
        int testCounts = 10000;
        for (int i = 0; i < testCounts; i++) {
            test(threadCounts);
        }
    }

    public static void test(int threadCounts) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch startFlag = new CountDownLatch(1);
        final CountDownLatch counter = new CountDownLatch(threadCounts);
        final Set<String> instanceSet = Collections.synchronizedSet(new HashSet<String>());
        for (int i = 0; i < threadCounts; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        startFlag.await();
                    } catch (InterruptedException e) {
                    }
                    instanceSet.add(SingletonObject.getInstance().toString());
                    counter.countDown();
                }
            });
        }
        startFlag.countDown();
        counter.await();
        SingletonObject.instance = null;
        if (instanceSet.size() > 1) {
            System.out.print("{");
            for (String instance : instanceSet) {
                System.out.print("[" + instance + "]");
            }
            System.out.println("}");
        }
        executorService.shutdown();
    }
}
