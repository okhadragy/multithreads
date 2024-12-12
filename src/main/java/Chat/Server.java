package Chat;
import java.io.*;
import java.net.*;
import javafx.scene.control.TextArea;
import Services.*;

public class Server extends Thread {
    private int port;
    private ServerSocket serverSocket;
    private String openedPageUser;
    private TextArea chatArea;
    private ChatService chatService;

    public Server(int port, String openedPageUser, TextArea chatArea, ChatService chatService) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
        this.openedPageUser = openedPageUser;
        this.chatArea = chatArea;
        this.chatService = chatService;
        System.out.println("Server started and listening on port "+this.port+"...");
    }

    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && ! serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket, openedPageUser, chatArea, chatService).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {}   
            }
        }
    }
}
