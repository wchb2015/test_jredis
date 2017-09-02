package org.concurrent;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskExecutionWebServer {

    private static final int port = 9999;

    private static final int threadNum = 100;

    private static final Executor executor = Executors.newFixedThreadPool(threadNum);


    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(port);

        while (true) {
            final Socket connection = socket.accept();

            Runnable task = () -> {
                System.out.println("haha..." + Thread.currentThread().getName());
            };


            executor.execute(task);
        }
    }
}
