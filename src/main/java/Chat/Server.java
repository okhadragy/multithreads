package Chat;
import java.io.*;
import java.net.*;

public class Server extends Thread {
    private int port;
    private ServerSocket serverSocket;

    public Server(int port) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("Server started and listening on port "+this.port+"...");
    }

    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && ! serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
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
