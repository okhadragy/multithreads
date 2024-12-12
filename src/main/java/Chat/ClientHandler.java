package Chat;
import java.io.*;
import java.net.*;

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage;

            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Received from client "+clientSocket.getPort()+": " + clientMessage);
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