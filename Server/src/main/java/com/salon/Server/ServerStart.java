package com.salon.Server;

import com.salon.Server.BD.DataBaseConnection;

public class ServerStart {
    public static void main(String[] args) {
        int PORT_WORK = 8888;
        ServerConnection server = new ServerConnection(PORT_WORK);
        if(DataBaseConnection.testConnection()){
            System.out.println("BD connected");
        }
        else{
            System.out.println("BD not connected");
        }
        new Thread(server).start();
    }
}
//3100