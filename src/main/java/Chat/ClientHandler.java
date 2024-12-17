package Chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Services.AuthService;
import Services.ChatService;
import Services.CustomerService;
import Services.PermissionService;
import gui.ChatPage;
import javafx.scene.control.*;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ArrayList<Socket> users;
    private ChatService chatService;

    public ClientHandler(Socket socket, ArrayList<Socket> users, ChatService chatService) {
        this.clientSocket = socket;
        this.users = users;
        this.chatService = chatService;
    }

    private void sendToAll(String message) {
        ArrayList<Socket> removedSockets = new ArrayList<>();
        for (Socket socket : users) {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
            } catch (SocketException e) {
                removedSockets.add(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        synchronized (users) {
            users.removeAll(removedSockets);
        }
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
            while (clientSocket.isBound() && !clientSocket.isClosed()) {
                String clientMessage = in.readLine();
                if (clientMessage == null) {
                    continue;
                }
                System.out.println("Received from client (" + clientSocket.getInetAddress() + ","
                        + clientSocket.getPort() + "): " + clientMessage);
                String[] parts = clientMessage.split("\u0001", 3);
                String from = parts[0].trim();
                String to = parts[1].trim();
                String content = parts[2].trim();
                chatService.create(from, to, content);
                sendToAll(clientMessage);
            }
            System.out.println("client handler closed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}