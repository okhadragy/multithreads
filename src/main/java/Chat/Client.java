package Chat;
import java.io.*;
import java.net.*;

public class Client extends Thread {
    private InetAddress host;
    private int port;
    private Socket socket;

    public Client(InetAddress host, int port) throws IOException{
        this.host = host;
        this.port = port;
        this.socket = new Socket(host, port);
        System.out.println("Connection started with Server:("+this.host+","+this.port+")");
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String userMessage;
            
            while (true) {
                System.out.print("Enter message to send to server (or type 'exit' to quit): ");
                userMessage = userInput.readLine();
                if (userMessage.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(userMessage);
                String serverResponse = in.readLine();
                System.out.println("Server says: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}