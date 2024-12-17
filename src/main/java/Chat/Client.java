package Chat;
import java.io.*;
import java.net.*;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import Services.ChatService;
import gui.ChatPage;

public class Client implements Runnable{
    private InetAddress host;
    private final int PORT = 122;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ChatPage chatPage;
    private ChatService chatService;

    public Client(ChatPage chatPage, ChatService chatService){
        try {
            host = InetAddress.getLocalHost();
        } catch (IOException e) {
            host = InetAddress.getLoopbackAddress();
        }
        this.chatPage = chatPage;
        this.chatService = chatService;
    }

    public void sendMessage(String userMessage) {
        if (out !=null) {
            out.println(chatService.getLoggedInUser().getUsername()+"\u0001"+chatPage.getUsername()+"\u0001"+userMessage);
        }
    }

    public void closeConnection(){
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        if (out != null) {
            out.close();
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {}
        }
    }

    @Override
    public void run() {
        try
        {
            this.socket = new Socket(host.getHostAddress(), PORT);
            System.out.println(socket);
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                String[] parts = serverMessage.split("\u0001", 3);
                String from = parts[0].trim();
                String to = parts[1].trim();
                String content = parts[2].trim();
                System.out.println(serverMessage);
                System.out.println(from);
                System.out.println(to);
                if (chatPage.getUsername().equals(from) && chatService.getLoggedInUser().getUsername().equals(to)) {
                    Platform.runLater(() -> {
                        chatPage.receiveMessage(content);
                    });   
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}