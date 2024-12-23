package gui;

import java.util.Stack;
import Entity.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;

public class AdminUsersPage {

    private final Central mainApp;

    public AdminUsersPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage){

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

        // FOR ANY SCROLLABLE PAGE, USE THESE LINES:
        ScrollPane sp = new ScrollPane(bp);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // remove horizontal bar
        Platform.runLater(() -> sp.lookup(".viewport").setStyle("-fx-background-color: transparent;"));
        sp.setFitToWidth(true); // extend the scrollpane on the entire view
        sp.setStyle("-fx-background-color: black; -fx-border-color: transparent"); // make it black
        
        // nav

        Image logo = new Image(getClass().getResource("/assets/multithreadsLogo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(85);
        logoView.setPreserveRatio(true);

        Text usersButton = new Text("USERS");
        usersButton.setFill(Color.WHITE);
        usersButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Text productButton = new Text("PRODUCTS");
        productButton.setFill(Color.WHITE);
        productButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Text categoryButton = new Text("CATEGORIES");
        categoryButton.setFill(Color.WHITE);
        categoryButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Text ordersButton = new Text("ORDERS");
        ordersButton.setFill(Color.WHITE);
        ordersButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Text chatButton = new Text("CHAT");
        chatButton.setFill(Color.WHITE);
        chatButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox controlBox = new HBox(40);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.getChildren().addAll(productButton, categoryButton, usersButton, ordersButton, chatButton);

        HBox navbar = new HBox(50);
        navbar.getChildren().addAll(logoView, controlBox);
        navbar.setStyle("-fx-background-color: black;");
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(15, 15, 15, 15));

        Image navbarDeco = new Image(getClass().getResource("/assets/navDeco.png").toExternalForm());
        ImageView navbarDecoView = new ImageView(navbarDeco);
        VBox barAndDeco = new VBox();
        barAndDeco.getChildren().addAll(navbar, navbarDecoView);

        bp.setTop(barAndDeco);

        productButton.setCursor(Cursor.HAND);
        productButton.setOnMouseClicked(event -> {
            mainApp.showAdminProductsPage();
        });

        categoryButton.setCursor(Cursor.HAND);
        categoryButton.setOnMouseClicked(event -> {
            mainApp.showAdminCategoriesPage();
        });

        usersButton.setCursor(Cursor.HAND);
        usersButton.setOnMouseClicked(event -> {
            mainApp.showAdminUsersPage();
        });

        ordersButton.setCursor(Cursor.HAND);
        ordersButton.setOnMouseClicked(event -> {
            mainApp.showAdminOrdersPage();
        });

        chatButton.setCursor(Cursor.HAND);
        chatButton.setOnMouseClicked(event -> {
            mainApp.showChatListPage();
        });

        logoView.setCursor(Cursor.HAND);
        logoView.setOnMouseClicked(event -> {
            mainApp.getAuth().Logout();
            mainApp.showLoginPage();
        });

        // content
        TableView<Customer> tableView = new TableView<>();

        TableColumn<Customer, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));

        TableColumn<Customer, String> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getBalance())));

        TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

        TableColumn<Customer, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender().toString()));

        tableView.getColumns().addAll(usernameColumn, balanceColumn, addressColumn, genderColumn);

        for (Customer customer : mainApp.getCustomerService().getAll()) {
            tableView.getItems().add(customer);
        }

        // Delete button
        Button deleteButton = new Button("Delete Selected User");
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        deleteButton.setCursor(Cursor.HAND);
        deleteButton.setOnAction(event -> {
            Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                mainApp.getCustomerService().delete(selectedCustomer.getUsername());
                tableView.getItems().remove(selectedCustomer);
            }
        });

        // Edit Address button
        Button editAddressButton = new Button("Edit Address");
        editAddressButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        editAddressButton.setCursor(Cursor.HAND);
        editAddressButton.setOnAction(event -> {
            Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                showEditAddressDialog(selectedCustomer, tableView);
            }
        });

        // Edit Password button
        Button editPasswordButton = new Button("Edit Password");
        editPasswordButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        editPasswordButton.setCursor(Cursor.HAND);
        editPasswordButton.setOnAction(event -> {
            Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                showEditPasswordDialog(selectedCustomer, tableView);
            }
        });

        // Edit Balance button
        Button editBalanceButton = new Button("Edit Balance");
        editBalanceButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        editBalanceButton.setCursor(Cursor.HAND);
        editBalanceButton.setOnAction(event -> {
            Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                showEditBalanceDialog(selectedCustomer, tableView);
            }
        });

        // Layout for the table and buttons
        VBox contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(tableView, editAddressButton, editPasswordButton, editBalanceButton, deleteButton);

        bp.setCenter(contentBox);

        return new Scene(sp, 1366, 768);
    }

    private void showEditAddressDialog(Customer selectedCustomer, TableView<Customer> tableView) {
        // Create a dialog for editing the address
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Edit Address");

        // Label and text field for new address
        Label label = new Label("Enter new address:");
        TextField textField = new TextField(selectedCustomer.getAddress());
        
        // Button to save the new address
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        saveButton.setOnAction(saveEvent -> {
            String newAddress = textField.getText().trim();
            if (!newAddress.isEmpty() && !newAddress.equals(selectedCustomer.getAddress())) {
                selectedCustomer.setAddress(newAddress);
                tableView.refresh();
                mainApp.getCustomerService().update(selectedCustomer.getUsername(), "address", newAddress);
                dialog.close();
            }
        });

        // Layout for the dialog
        VBox dialogLayout = new VBox(10);
        dialogLayout.setAlignment(Pos.CENTER);
        dialogLayout.getChildren().addAll(label, textField, saveButton);

        Scene dialogScene = new Scene(dialogLayout, 300, 150);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void showEditPasswordDialog(Customer selectedCustomer, TableView<Customer> tableView) {
        // Create a dialog for editing the password
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Edit Password");

        // Label and text field for new password
        Label label = new Label("Enter new password:");
        PasswordField passwordField = new PasswordField();
        
        // Button to save the new password
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        saveButton.setOnAction(saveEvent -> {
            String newPassword = passwordField.getText().trim();
            if (!newPassword.isEmpty()) {
                selectedCustomer.setPassword(newPassword);
                tableView.refresh();
                mainApp.getCustomerService().update(selectedCustomer.getUsername(), "password", newPassword);
                dialog.close();
            }
        });

        // Layout for the dialog
        VBox dialogLayout = new VBox(10);
        dialogLayout.setAlignment(Pos.CENTER);
        dialogLayout.getChildren().addAll(label, passwordField, saveButton);

        Scene dialogScene = new Scene(dialogLayout, 300, 150);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void showEditBalanceDialog(Customer selectedCustomer, TableView<Customer> tableView) {
        // Create a dialog for editing the balance
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Edit Balance");

        // Label and text field for new balance
        Label label = new Label("Enter new balance:");
        TextField textField = new TextField(String.valueOf(selectedCustomer.getBalance()));
        
        // Button to save the new balance
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        saveButton.setOnAction(saveEvent -> {
            String newBalanceStr = textField.getText().trim();
            try {
                double newBalance = Double.parseDouble(newBalanceStr);
                selectedCustomer.setBalance(newBalance);
                tableView.refresh();
                mainApp.getCustomerService().update(selectedCustomer.getUsername(), "balance", newBalance);
                dialog.close();
            } catch (NumberFormatException e) {
                // Show error if the balance is not a valid number
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid balance.");
                alert.showAndWait();
            }
        });

        // Layout for the dialog
        VBox dialogLayout = new VBox(10);
        dialogLayout.setAlignment(Pos.CENTER);
        dialogLayout.getChildren().addAll(label, textField, saveButton);

        Scene dialogScene = new Scene(dialogLayout, 300, 150);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
