package com.salon.Server;



import com.salon.Server.Handlers.UserHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection implements Runnable{
    private final int PORT;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;

    public ServerConnection(int port) {
        this.PORT = port;
    }

    @Override
    public void run() {
        openServerSocket();
        while (!isStopped()) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected...");
                new Thread(new UserHandler(clientSocket)).start();
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server stopped.");
                    break;
                }
                e.printStackTrace();
            }
        }
    }

    private void openServerSocket() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized boolean isStopped() {
        return isStopped;
    }

    public synchronized void setStopped(boolean stopped) {
        isStopped = stopped;
    }
}
