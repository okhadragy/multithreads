package Chat;
import java.io.*;
import java.net.*;

import Services.ChatService;
import javafx.scene.control.*;

class ClientHandler extends Thread {
    private Socket clientSocket;
    private String openedPageUser;
    private TextArea chatArea;
    private ChatService chatService;

    public ClientHandler(Socket socket, String openedPageUser, TextArea chatArea, ChatService chatService) {
        this.clientSocket = socket;
        this.openedPageUser = openedPageUser;
        this.chatArea = chatArea;
        this.chatService = chatService;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);)
        {
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Received from client "+clientSocket.getPort()+": " + clientMessage);
                String[] parts = clientMessage.split("\u0001", 2);
                String from = parts[0].trim();
                String content = parts[1].trim();
                chatService.create(from,chatService.getLoggedInUser().getUsername(),content);
                if (openedPageUser.equals(from)) {
                    chatArea.setText(content);
                }
                out.println("Echo: " + clientMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {}   
            }
        }
    }
}