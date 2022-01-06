package com.server.number_finding_game;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ChatServerThread extends Thread {
    private String userName = "";
    private NewServer newServer = null;
    private Socket socket = null;
    private SocketAddress ID = null;

    private BufferedInputStream bis = null;
    private DataInputStream dis = null;
    private BufferedOutputStream bos = null;
    private DataOutputStream dos = null;

    public ChatServerThread(NewServer _New_server, Socket _socket) {
        super();
        newServer = _New_server;
        socket = _socket;
        ID = socket.getRemoteSocketAddress();
    }

    public SocketAddress getID() {
        return ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void send(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException e) {
            System.out.println("Client " + socket.getRemoteSocketAddress() + " error sending : " + e.getMessage());
            newServer.remove(ID);
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Client " + socket.getRemoteSocketAddress() + " connected to server...");

            bis = new BufferedInputStream(socket.getInputStream());
            dis = new DataInputStream(bis);
            bos = new BufferedOutputStream(socket.getOutputStream());
            dos = new DataOutputStream(bos);

            while (true) {
                //gui cho player biet no dang o lobby nao, reset sau khi lobby isEnd
                newServer.handle(ID, dis.readUTF());
            }
        } catch (IOException e) {
            newServer.remove(ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        System.out.println("Client " + socket.getRemoteSocketAddress() + " disconnect from server...");
        socket.close();
        dis.close();
        dos.close();
    }
}
