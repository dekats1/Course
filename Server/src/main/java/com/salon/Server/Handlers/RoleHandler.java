package com.salon.Server.Handlers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class RoleHandler implements Handler {
    protected Socket userSocket;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected Boolean exit = false;

    public RoleHandler(Socket userSocket, ObjectInputStream in, ObjectOutputStream out) {
        this.userSocket = userSocket;
        this.in = in;
        this.out = out;
        this.exit = false;
    }

    public abstract void handle() throws IOException, ClassNotFoundException;
}
