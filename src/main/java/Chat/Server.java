package Chat;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javafx.scene.control.TextArea;
import Services.*;
import gui.ChatPage;

public class Server {
    private static AuthService auth = new AuthService(null,null);
    private static PermissionService permission = new PermissionService(auth);
    private static AdminService adminService = new AdminService(auth,permission);
    private static CategoryService categoryService = new CategoryService(auth,permission);
    private static ProductService productService = new ProductService(auth,permission,categoryService);
    private static CustomerService customerService = new CustomerService(auth,permission,productService,null);
    private static OrderService orderService = new OrderService(auth,permission,productService,customerService);
    private static ChatService chatService = new ChatService(auth, permission, adminService, customerService);
    private static final int PORT = 122;
    private static ArrayList<Socket> users = new ArrayList<>();
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        auth.setCustomerService(customerService);
        auth.setAdminService(adminService);
        customerService.setOrderService(orderService);

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started and listening on port "+PORT+"...");
            while (serverSocket.isBound() && ! serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                synchronized (users) {
                    users.add(clientSocket);
                }
                new Thread(new ClientHandler(clientSocket,users,chatService)).start();
            }
            System.out.println("Server closed");
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
