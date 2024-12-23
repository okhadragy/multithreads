package gui;

import Entity.Product;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;

public class AdminProductsPage {

    private final Central mainApp;

    public AdminProductsPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage){

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

        ScrollPane sp = new ScrollPane(bp);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Platform.runLater(() -> sp.lookup(".viewport").setStyle("-fx-background-color: transparent;"));
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: black; -fx-border-color: transparent");

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

        TableView<Product> tableView = new TableView<>();

        TableColumn<Product, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));

        TableColumn<Product, String> categoryIdColumn = new TableColumn<>("Category ID");
        categoryIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoryId()));

        TableColumn<Product, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));

        for(Product products : mainApp.getProductService().getAll()){
            tableView.getItems().add(products);
        }

        tableView.getColumns().addAll(idColumn, nameColumn, descriptionColumn, priceColumn, categoryIdColumn, quantityColumn);

        Button changePriceButton = new Button("Change Price");
        changePriceButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        changePriceButton.setOnAction(event -> {
            Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                openPriceChangeDialog(selectedProduct);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a product to change the price.");
                alert.show();
            }
        });

        Button updateQuantityButton = new Button("Update Quantity");
        updateQuantityButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        updateQuantityButton.setOnAction(event -> {
            Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                openQuantityChangeDialog(selectedProduct);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a product to update the quantity.");
                alert.show();
            }
        });

        Button deleteProductButton = new Button("Delete Product");
        deleteProductButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteProductButton.setOnAction(event -> {
            Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, 
                        "Are you sure you want to delete " + selectedProduct.getName() + "?",
                        ButtonType.YES, ButtonType.NO);
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        mainApp.getProductService().delete(selectedProduct.getId());
                        tableView.getItems().remove(selectedProduct);
                    }
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a product to delete.");
                alert.show();
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(tableView, changePriceButton, updateQuantityButton, deleteProductButton);
        bp.setCenter(vbox);

        return new Scene(sp, 1366, 768);
    }

    private void openPriceChangeDialog(Product product) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Product Price");
        dialog.setHeaderText("Enter the new price for " + product.getName());
        dialog.setContentText("New price:");

        dialog.showAndWait().ifPresent(inputText -> {
            try {
                double newPrice = Double.parseDouble(inputText.trim());
                mainApp.getProductService().update(product.getId(), "price", newPrice);
                mainApp.showAdminProductsPage();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid price entered for product: " + product.getName());
                alert.show();
            }
        });
    }

    private void openQuantityChangeDialog(Product product) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Product Quantity");
        dialog.setHeaderText("Enter the new quantity for " + product.getName());
        dialog.setContentText("New quantity:");

        dialog.showAndWait().ifPresent(inputText -> {
            try {
                int newQuantity = Integer.parseInt(inputText.trim());
                mainApp.getProductService().update(product.getId(), "quantity", newQuantity);
                mainApp.showAdminProductsPage();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid quantity entered for product: " + product.getName());
                alert.show();
            }
        });
    }
}
