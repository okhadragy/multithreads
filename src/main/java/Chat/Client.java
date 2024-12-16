package Chat;
import java.io.*;
import java.net.*;

import Services.ChatService;

public class Client {
    private InetAddress host;
    private int port;
    private Socket socket;
    private ChatService chatService;

    public Client(InetAddress host, int port, ChatService chatService) throws IOException{
        this.host = host;
        this.port = port;
        this.chatService = chatService;
    }

    public void sendMessage(String userMessage) {
        BufferedReader in = null;
        PrintWriter out = null;
        try
        {
            this.socket = new Socket(host.getHostAddress(), port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(chatService.getLoggedInUser().getUsername()+"\u0001"+userMessage);
            String serverResponse = in.readLine();
            System.out.println("Server says: " + serverResponse);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (out != null) {
                out.close();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {}
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}