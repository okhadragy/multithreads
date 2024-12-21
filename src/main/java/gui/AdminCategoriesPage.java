package gui;

import java.util.Stack;
import Entity.*;
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

public class AdminCategoriesPage {

    private final Central mainApp;

    public AdminCategoriesPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage) {

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

        // FOR ANY SCROLLABLE PAGE, USE THESE LINES:
        ScrollPane sp = new ScrollPane(bp);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // remove horizontal bar
        Platform.runLater(() -> sp.lookup(".viewport").setStyle("-fx-background-color: transparent;"));
        // when the viewport loads in set its style to transparent so it doesn't affect
        // scrollpane styling
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
        TableView<Category> tableView = new TableView<>();

        TableColumn<Category, String> nameColumn = new TableColumn<>("Category Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Category, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        TableColumn<Category, String> IDColumn = new TableColumn<>("ID");
        IDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));

        Button deleteButton = new Button("Remove Category");
        Button editButton = new Button("Edit Category");

        deleteButton.setOnAction(event -> {
            Category selectedCat = tableView.getSelectionModel().getSelectedItem();
            if (selectedCat != null) {
                String catId = selectedCat.getId();
                mainApp.getCategoryService().delete(catId);
                tableView.getItems().remove(selectedCat);
            }
        });

        editButton.setOnAction(event -> {
            Category selectedCat = tableView.getSelectionModel().getSelectedItem();
            if (selectedCat != null) {
                String catId = selectedCat.getId();
            }
        });

        tableView.getColumns().addAll(nameColumn, descriptionColumn, IDColumn);

        for (Category category : mainApp.getCategoryService().getAll()) {
            tableView.getItems().add(category);
        }
        // NEW CODE

        VBox vertical = new VBox(20);
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        HBox hbox = new HBox(15);
        hbox.getChildren().addAll(deleteButton,editButton);
        vertical.getChildren().addAll(tableView, hbox, gridPane);
        VBox.setMargin(hbox, new Insets(0, 0, 0, 15));

        Text descriptionLabel = new Text("Description:");
        descriptionLabel.setFill(Color.WHITE);
        TextField descriptionField = new TextField();

        Text nameLabel = new Text("Name:");
        nameLabel.setFill(Color.WHITE);
        TextField nameField = new TextField();

        gridPane.add(descriptionLabel, 0, 0);
        gridPane.add(descriptionField, 1, 0);
        descriptionField.setPrefWidth(300);
        descriptionField.setPrefHeight(60);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameField, 1, 1);
        Text warning = new Text();
        gridPane.add(warning, 1, 3);
        warning.setFill(Color.RED);

        Button submitButton = new Button("Add Category");
        gridPane.add(submitButton, 1, 2);

        submitButton.setOnAction(event -> {
            String description = descriptionField.getText();
            String name = nameField.getText();

            if (description.isEmpty()) {
                warning.setText("Description can't be empty");
                return;
            }
            if (name.isEmpty()) {
                warning.setText("Name can't be empty");
                return;
            }
            mainApp.getCategoryService().create(name, description);
            // e3ml hena el operation ely htzwd el category gowa el database

            stage.setScene(getScene(stage)); // refresh page
        });

        gridPane.setAlignment(Pos.CENTER);
        bp.setCenter(vertical);
        // NEW CODE
        // line el setCenter tl3 fo2

        // bp.setCenter(tableView);

        return new Scene(sp, 1366, 768);

    }
}