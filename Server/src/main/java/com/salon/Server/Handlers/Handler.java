package com.salon.Server.Handlers;

import java.io.IOException;

public interface Handler {
    public abstract void handle() throws IOException, ClassNotFoundException;
}
