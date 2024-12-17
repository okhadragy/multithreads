package Chat;

import java.io.*;
import java.net.*;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import Services.ChatService;
import gui.ChatPage;

public class Client implements Runnable {
    private InetAddress host;
    private final int PORT = 122;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ChatPage chatPage;
    private ChatService chatService;
    private boolean running = true;

    public Client(ChatPage chatPage, ChatService chatService) {
        try {
            host = InetAddress.getLocalHost();
        } catch (IOException e) {
            host = InetAddress.getLoopbackAddress();
        }
        this.chatPage = chatPage;
        this.chatService = chatService;
    }

    public void sendMessage(String userMessage) {
        if (out != null) {
            out.println(chatService.getLoggedInUser().getUsername() + "\u0001" + chatPage.getUsername() + "\u0001"
                    + userMessage);
        }
    }

    public void closeConnection() {
        if (in != null) {
            System.out.println("hi input");
            try {
                in.close();
                System.out.println("input is closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if (out != null) {
            try {
                out.close();
                System.out.println("Output is closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }

        if (socket != null) {
            try {
                socket.close();
                System.out.println("Socket is closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void close(){
        running = false; 
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket(host.getHostAddress(), PORT);
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println(socket);

            while (socket.isBound() && !socket.isClosed() && running) {
                try {
                    String serverMessage = in.readLine();
                    if (serverMessage == null) {
                        continue;
                    }

                    String[] parts = serverMessage.split("\u0001", 3);
                    if (parts.length != 3) {
                        System.err.println("Invalid message format: " + serverMessage);
                    }
                    String from = parts[0].trim();
                    String to = parts[1].trim();
                    String content = parts[2].trim();


                    if (chatPage.getUsername().equals(from) &&
                            chatService.getLoggedInUser().getUsername().equals(to)) {
                        Platform.runLater(() -> chatPage.receiveMessage(content));
                    }

                } catch (IOException e) {
                    continue;
                }
            }
            System.out.println("Client is closed");

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            closeConnection();
        }
    }
}