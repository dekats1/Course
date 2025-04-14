package com.salon.Server.Handlers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AdminHandler extends RoleHandler {
    public AdminHandler(Socket clientSocket, ObjectInputStream in, ObjectOutputStream out) {
        super(clientSocket,in,out);
    }

    @Override
    public void handle() throws IOException, ClassNotFoundException {
        while (true) {

        }

    }
}
