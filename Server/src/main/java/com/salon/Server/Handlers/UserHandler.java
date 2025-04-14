package com.salon.Server.Handlers;

import com.salon.Server.Services.AuthRequest;
import com.salon.Server.Services.AuthResponse;
import com.salon.Server.Services.AuthService;

import java.io.*;
import java.net.Socket;

public class UserHandler implements Runnable {
    private final Socket userSocket;
    private final AuthService authService;

    public UserHandler(Socket clientSocket) {
        this.userSocket = clientSocket;
        this.authService = new AuthService();
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(userSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(userSocket.getInputStream())) {

            out.flush();

            while (true) {
                Object request = in.readObject();
                System.out.println("Received request: " + request);

                if (!(request instanceof AuthRequest authRequest)) {
                    out.writeObject(new AuthResponse(false, null, "Invalid request format"));
                    continue;
                }

                System.out.println("Auth attempt for user: " + authRequest.getUsername());

                AuthResponse authResponse = authService.authenticate(authRequest.getUsername(), authRequest.getPassword());

                out.writeObject(authResponse);
                System.out.println("Auth response sent: " + authResponse.isSuccess());

                if (authResponse.isSuccess()) {
                    RoleHandler handler = createRoleHandler(authResponse.getRole(), userSocket, in, out);

                    if (handler != null) {
                        handler.handle();
                    }
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            try {
                userSocket.close();
                System.out.println("Connection closed");
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }


    private RoleHandler createRoleHandler(String role, Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        System.out.println("Creating handler for role: " + role);
        return switch (role.toLowerCase()) {
            case "admin" -> new AdminHandler(socket, in, out);
            case "seller" -> new SellerHandler(socket, in, out);
            case "manager" -> new ManagerHandler(socket, in, out);
            default -> null;
        };
    }
}