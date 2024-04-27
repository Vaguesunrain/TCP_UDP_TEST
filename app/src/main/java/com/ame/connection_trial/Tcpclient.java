package com.ame.connection_trial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Tcpclient {
    String ip;
    int port;
    Socket client;
    InputStream inputStream;
    OutputStream outputStream;

    public boolean connect(String ip, int port){

        try {
            client = new Socket(ip, port);
            inputStream = client.getInputStream();
            outputStream = client.getOutputStream();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean check_status(){
        try {
            return client.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void send(String msg){
        try {
            outputStream.write(msg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String receive(){

        try {
            byte[] buffer = new byte[1024];
            int length = inputStream.read(buffer);
            return new String(buffer, 0, length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void close_connect(){
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
