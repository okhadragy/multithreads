package gui;

import java.io.ObjectInputFilter;

import Entity.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AdminOrdersPage {

    private final Central mainApp;

    public AdminOrdersPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage){

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

        // FOR ANY SCROLLABLE PAGE, USE THESE LINES:
        ScrollPane sp = new ScrollPane(bp);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // remove horizontal bar
        Platform.runLater(() -> sp.lookup(".viewport").setStyle("-fx-background-color: transparent;"));
        // when the viewport loads in set its style to transparent so it doesn't affect scrollpane styling
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
           TableView<Order> tableView = new TableView<>();

        
        TableColumn<Order, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData ->new SimpleStringProperty(cellData.getValue().getId()));
        
        // Create columns for the table
        TableColumn<Order, String> customerColumn = new TableColumn<>("Customer");
        customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer()));


        TableColumn<Order, String> paymentMethodColumn = new TableColumn<>("Payment Method");
        paymentMethodColumn.setCellValueFactory(cellData -> cellData.getValue().getPaymentMethod()==null? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getPaymentMethod().toString()));

        TableColumn<Order, String> productsColumn = new TableColumn<>("Products");
        productsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getProducts().size())) );

        TableColumn<Order, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        TableColumn<Order, String> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotal())));

        // Extra column for Cancel button
        TableColumn<Order, Void> changeStatusColumn = new TableColumn<>("Change Status");
        changeStatusColumn.setCellFactory(col -> new TableCell<>() {
            private final ComboBox<Status> statusDropdown = new ComboBox<>();
            {
                // Populate the dropdown menu with the possible statuses
                statusDropdown.getItems().setAll(Status.values());
                statusDropdown.setOnAction(event -> {
                    Order selectedOrder = getTableView().getItems().get(getIndex());
                    Status status = statusDropdown.getValue();
                    if (selectedOrder != null && status != null) {
                        if(status.equals(Status.draft)){
                            if(selectedOrder.getStatus().equals(Status.processing)){
                            mainApp.getOrderService().setStatus(selectedOrder.getId(),status);
                        }
                        }
                        if(status.equals(Status.processing)){
                            mainApp.getOrderService().setStatus(selectedOrder.getId(),status);
                        }
                        if(status.equals(Status.shipping)){
                            mainApp.getOrderService().pay(selectedOrder.getId(), selectedOrder.getPaymentMethod());
                        }
                        if(status.equals(Status.delivered)){
                            mainApp.getOrderService().deliver(selectedOrder.getId());
                        }
                        if(status.equals(Status.closed)){
                            mainApp.getOrderService().close(selectedOrder.getId());
                        }
                        if(status.equals(Status.cancelled)){
                            mainApp.getOrderService().cancel(selectedOrder.getId());
                        }
                       
                        //selectedOrder.setStatus(status); // Update the status in the local object
                        tableView.refresh(); // Refresh the table view to reflect changes
                    }
                });
            }
        
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().get(getIndex()) == null) {
                    setGraphic(null);
                } else {
                    Order currentOrder = getTableView().getItems().get(getIndex());
                    statusDropdown.setValue(currentOrder.getStatus()); // Set the current status in the dropdown
                    setGraphic(statusDropdown); // Display the dropdown in the cell
                }
            }
        });
        


        for (Order order : mainApp.getOrderService().getAll()){
            tableView.getItems().add(order);
        }


         tableView.getColumns().addAll( idColumn , customerColumn, paymentMethodColumn, productsColumn, statusColumn, totalColumn, changeStatusColumn);

         bp.setCenter(tableView);
     

        return new Scene(sp, 1366, 768);

    }
}