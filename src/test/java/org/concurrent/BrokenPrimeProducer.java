package org.concurrent;


import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BrokenPrimeProducer extends Thread {

    private final BlockingQueue<BigInteger> queue;

    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;

        while (!cancelled) {
            try {
                queue.put(p = p.nextProbablePrime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void cancel() {
        cancelled = true;
    }

    public static void main(String[] args) {
        BlockingQueue<BigInteger> primes = new ArrayBlockingQueue(10000);

        BrokenPrimeProducer primeProducer = new BrokenPrimeProducer(primes);

        primeProducer.start();

    }
}
