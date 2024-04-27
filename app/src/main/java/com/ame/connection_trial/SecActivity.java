package com.ame.connection_trial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;

public class SecActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private int counter = 0;
    TcpServer tcpServer = new TcpServer();
    private TextView textView ;
    private String Sysmsg = "  system message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        Server_thread server_thread = new Server_thread();
        EditText ip_ed = findViewById(R.id.editTextText0);
        EditText port_ed = findViewById(R.id.editTextText1);
        EditText Message_ed = findViewById(R.id.editTextText2);

        textView = new TextView(this);
        Button connect_btn = findViewById(R.id.button3);
        Button disconnect_btn = findViewById(R.id.button4);
        Button sendbtn = findViewById(R.id.button5);
        ScrollView text_layout = findViewById(R.id.textlayout);
        textView.setText("  \n");
        text_layout.addView(textView);
        connect_btn.setText("Start Server");
        disconnect_btn.setText("Shut Server");


        connect_btn.setOnClickListener(v -> {
            tcpServer.port =Integer.parseInt( port_ed.getText().toString());
            //thread

            server_thread.start();
        });

        sendbtn.setOnClickListener(v-> {
            String data=Message_ed.getText().toString();
            if(tcpServer.clientSocket.isClosed()==false){
            tcpServer.wirte_data(data);
            System.out.println("send data: " + data);
            textView.append("  T:  "+data+"\n");}
            else textView.append(Sysmsg+":  no link\n");
        });

        disconnect_btn.setOnClickListener(v -> {
            tcpServer.Server_stop();
            server_thread.interrupt();
            textView.append(Sysmsg+":  server stop\n");
        });

    }


    private class Server_thread extends Thread{

        @Override
        public void run() {

            handler.post(() -> textView.append(Sysmsg+":  server start\n"));

            String ip_msg=tcpServer.Server_start();

           handler.post(() -> textView.append(Sysmsg+":  ip  "+ip_msg+"is connected\n"));
            while (true) {
                if (tcpServer.clientSocket.isClosed()) {
                    runOnUiThread(() -> textView.append(Sysmsg+":  someone "+"is disconnected\n"));
                    // 进行重新连接的逻辑
                    if(tcpServer.wait_link()) {
                        runOnUiThread(() -> textView.append(Sysmsg + ":  ip  " + tcpServer.clientSocket.getInetAddress().getHostAddress() + "is connected\n"));
                    }

                }
                else{
                    String data = tcpServer.read_data();
                    if (data != null) {
                        System.out.println("received data: " + data);
                        runOnUiThread(() -> textView.append("  R:  "+data+"\n"));
                    }
                    else {
                        try {
                            tcpServer.clientSocket.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }
}