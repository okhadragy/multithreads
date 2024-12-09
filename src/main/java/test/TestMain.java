package test;
import java.util.Date;
import java.util.Scanner;

import Entity.Gender;
import Entity.PaymentMethod;
import Entity.Role;
import Services.*;

public class TestMain {
    public static void main(String[] args) {
        AuthService auth = new AuthService();
        PermissionService permission = new PermissionService(auth);
        AdminService adminService = new AdminService(auth,permission);
        CategoryService categoryService = new CategoryService(auth,permission);
        ProductService productService = new ProductService(auth,permission,categoryService);
        CustomerService customerService = new CustomerService(auth,permission,productService);
        OrderService orderService = new OrderService(auth,permission,productService);
        

        Scanner i = new Scanner(System.in);
        String username, pass;
        do {
            System.out.print("Enter Username: ");
            username = i.nextLine();
            System.out.print("Enter Password: ");
            pass = i.nextLine();
        } while (!auth.Login(username, pass));

        System.out.println(auth.getLoggedInUser());
        System.out.println(adminService.get(username));
        System.out.println(customerService.getAll());
        customerService.addInterest("omar", "1");
        customerService.addInterest("omar", "2");
        productService.create("jacket", "ioijoijo", null, 70, null);
        customerService.addInterest("omar", "3");
        customerService.addToCart("omar", "1");
        orderService.convertToOrder(customerService.get("omar").getCartId());
        orderService.pay("3", PaymentMethod.bank);
        System.out.println(orderService.getAll());
    }
}
