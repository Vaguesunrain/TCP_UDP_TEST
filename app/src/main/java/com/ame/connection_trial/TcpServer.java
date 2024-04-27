package com.ame.connection_trial;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    ServerSocket serverSocket;
    Socket clientSocket;
    int port;
    OutputStream dataOutputStream;
    InputStream dataInputStream;
    public String Server_start(){

        try {
            serverSocket = new ServerSocket(port);
            // 在服务器端为客户端设置读取超时时间

            clientSocket=serverSocket.accept();
            clientSocket.setSoTimeout(2000);
            dataOutputStream = clientSocket.getOutputStream();
            dataInputStream = clientSocket.getInputStream();
            return clientSocket.getInetAddress().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return "exception!!!";
        }
    }
    public Boolean wait_link(){
        System.out.println("someone is disconnected,wait_link");
        try {
            clientSocket = serverSocket.accept();
            clientSocket.setSoTimeout(2000);
            System.out.println("someone is connected");
            System.out.println(clientSocket.getInetAddress().getHostAddress());
            dataOutputStream = clientSocket.getOutputStream();
            dataInputStream = clientSocket.getInputStream();
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
    public void  wirte_data(String data){
        try {
            dataOutputStream.write(data.getBytes());
            dataOutputStream.flush(); // 刷新输出流
            System.out.println("Sent message???: " + data);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error sending message: " + e.getMessage());
        }
    }

    public String read_data(){
        byte[] buffer = new byte[1024];
        int bytesRead;
        try {
            if ((bytesRead = dataInputStream.read(buffer)) != -1) {
                String receivedMessage = new String(buffer, 0, bytesRead);
                System.out.println("Received message: " + receivedMessage);
                return receivedMessage;
            }else{
            System.out.println("Client disconnected");

            return null;}
        } catch (IOException e) {
            System.out.println("Error reading from client: " + e.getMessage());

            return null;
        }

    }


    public void Server_stop(){

        try {
            serverSocket.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
