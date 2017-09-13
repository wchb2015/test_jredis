package org.concurrent;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PrimeGenerator implements Runnable {

    private final List<BigInteger> primes = new ArrayList<>();

    private volatile boolean cancelled;

    @Override
    public void run() {

        BigInteger p = BigInteger.ONE;

        while (!cancelled) {

            p = p.nextProbablePrime();

            synchronized (this) {
                primes.add(p);
            }
        }
    }

    public void cancel() {
        cancelled = true;
    }

    public synchronized List<BigInteger> get() {

        return new ArrayList<>(primes);
    }

    public static void main(String[] args) {
        PrimeGenerator generator = new PrimeGenerator();

        new Thread(generator).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            generator.cancel();
        }

        System.out.println(generator.get());
    }
}
