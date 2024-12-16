package gui;

import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;

import java.util.ArrayList;
import java.util.List;

import Entity.Product; // Import the Product class

public class ProductPage {

    private final Central mainApp;
    private List<VBox> allProducts = new ArrayList<>(); // Store all product boxes

    public ProductPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage) {
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

        ScrollPane sp = new ScrollPane(bp);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // This line might cause NullPointerException if ".viewport" doesn't exist yet
        Platform.runLater(() -> {
            try {
                sp.lookup(".viewport").setStyle("-fx-background-color: transparent;");
            } catch (NullPointerException e) {
                System.err.println("Error setting viewport style: " + e.getMessage());
            }
        });
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: black; -fx-border-color: transparent");

        // nav
        Image logo = new Image(getClass().getResource("/assets/multithreadsLogo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(85);
        logoView.setPreserveRatio(true);

        Text cartButton = new Text("CART");
        cartButton.setFill(Color.WHITE);
        cartButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Text productButton = new Text("PRODUCTS");
        productButton.setFill(Color.WHITE);
        productButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Text categoryButton = new Text("CATEGORIES");
        categoryButton.setFill(Color.WHITE);
        categoryButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Text ordersButton = new Text("ORDERS");
        ordersButton.setFill(Color.WHITE);
        ordersButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox controlBox = new HBox(40);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.getChildren().addAll(productButton, categoryButton, cartButton, ordersButton);
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
            mainApp.showProductPage();
        });
        categoryButton.setCursor(Cursor.HAND);
        categoryButton.setOnMouseClicked(event -> {
            mainApp.showCategoryPage();
        });
        cartButton.setCursor(Cursor.HAND);
        cartButton.setOnMouseClicked(event -> {
            mainApp.showCartPage();
        });
        ordersButton.setCursor(Cursor.HAND);
        ordersButton.setOnMouseClicked(event -> {
            mainApp.showOrdersPage();
        });
        logoView.setCursor(Cursor.HAND);
        logoView.setOnMouseClicked(event -> {
            mainApp.getAuth().Logout();
            mainApp.showLoginPage();
        });

        // content
        VBox mainLayout = new VBox(50);
        mainLayout.setAlignment(Pos.TOP_CENTER);

        HBox controlBar = new HBox(25);
        controlBar.setPadding(new Insets(30, 15, 0, 70));

        // Create the search bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search for products...");
        searchBar.setPrefWidth(250);

        // Create category filter dropdown
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("All", "Hoodies", "T-Shirts", "Trousers", "Shoes");
        categoryComboBox.setValue("All");

        // Create sort by dropdown
        ComboBox<String> sortByComboBox = new ComboBox<>();
        sortByComboBox.getItems().addAll("Price (Low to High)", "Price (High to Low)", "Name (A-Z)", "Name (Z-A)");
        sortByComboBox.setValue("Price (Low to High)");

        FlowPane productGrid = new FlowPane();
        productGrid.setHgap(50);
        productGrid.setAlignment(Pos.CENTER);

        // Get products from ProductService
        try {
            List<Product> products = mainApp.getProductService().getAll(); // Retrieve all products
            System.out.println("Retrieved products: " + products.size()); // Debug output
            for (Product product : products) {

                addProductToGrid(productGrid, product); // Add each product to grid
            }
        } catch (RuntimeException e) {
            System.err.println("Error getting products: " + e.getMessage());
            // Optionally, display a message in the UI for the user
            Label errorLabel = new Label("Failed to load products.");
            errorLabel.setTextFill(Color.RED);
            productGrid.getChildren().add(errorLabel);
        }


        controlBar.getChildren().addAll(searchBar, categoryComboBox, sortByComboBox);
        mainLayout.getChildren().addAll(controlBar, productGrid);
        bp.setCenter(mainLayout);

        // Search functionality
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            productGrid.getChildren().clear();
            for (VBox productBox : allProducts) {
                if (productBox.getChildren().get(1) instanceof Label) {
                    Label nameLabel = (Label) productBox.getChildren().get(1);
                    if (nameLabel.getText().toLowerCase().contains(newValue.toLowerCase())) {
                        productGrid.getChildren().add(productBox);
                    }
                }
            }
        });

        // Sort functionality
        sortByComboBox.setOnAction(event -> {
            String selectedSort = sortByComboBox.getValue();
            List<VBox> sortedProducts = new ArrayList<>(allProducts);

            switch (selectedSort) {
                case "Price (Low to High)":
                    sortedProducts.sort((p1, p2) -> {
                        Label priceLabel1 = (Label) p1.getChildren().get(2);
                        Label priceLabel2 = (Label) p2.getChildren().get(2);
                        double price1 = Double.parseDouble(priceLabel1.getText().replace("$", "").replace("EGP", ""));
                        double price2 = Double.parseDouble(priceLabel2.getText().replace("$", "").replace("EGP", ""));
                        return Double.compare(price1, price2);
                    });
                    break;
                case "Price (High to Low)":
                    sortedProducts.sort((p1, p2) -> {
                        Label priceLabel1 = (Label) p1.getChildren().get(2);
                        Label priceLabel2 = (Label) p2.getChildren().get(2);
                        double price1 = Double.parseDouble(priceLabel1.getText().replace("$", "").replace("EGP", ""));
                        double price2 = Double.parseDouble(priceLabel2.getText().replace("$", "").replace("EGP", ""));
                        return Double.compare(price2, price1);
                    });
                    break;
                case "Name (A-Z)":
                    sortedProducts.sort((p1, p2) -> {
                        Label nameLabel1 = (Label) p1.getChildren().get(1);
                        Label nameLabel2 = (Label) p2.getChildren().get(1);
                        return nameLabel1.getText().compareTo(nameLabel2.getText());
                    });
                    break;
                case "Name (Z-A)":
                    sortedProducts.sort((p1, p2) -> {
                        Label nameLabel1 = (Label) p1.getChildren().get(1);
                        Label nameLabel2 = (Label) p2.getChildren().get(1);
                        return nameLabel2.getText().compareTo(nameLabel1.getText());
                    });
                    break;
            }

            productGrid.getChildren().clear();
            productGrid.getChildren().addAll(sortedProducts);
        });

        // Filter functionality
        categoryComboBox.setOnAction(event -> {
            String selectedCategory = categoryComboBox.getValue();
            productGrid.getChildren().clear();

            if (selectedCategory.equals("All")) {
                productGrid.getChildren().addAll(allProducts);
            } else {
                for (VBox productBox : allProducts) {
                    if (productBox.getChildren().size() > 4 && productBox.getChildren().get(4) instanceof Label) {
                        Label categoryLabel = (Label) productBox.getChildren().get(4);
                        if (categoryLabel.getText().equals(selectedCategory)) {
                            productGrid.getChildren().add(productBox);
                        }
                    }
                }
            }
        });

        return new Scene(sp, 1366, 768);
    }

    private void addProductToGrid(FlowPane grid, Product product) {
        // Load product image
        Image productImage;
        try {
            productImage = new Image(getClass().getResourceAsStream(product.getImage()));
        } catch (Exception e) {
            System.err.println("Error loading image: " + product.getImage());
            productImage = new Image(getClass().getResourceAsStream("/assets/m.png")); // Fallback image
        }
        ImageView productImageView = new ImageView(productImage);
        productImageView.setFitWidth(200);
        productImageView.setFitHeight(200);

        // Create labels
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.WHITE);

        Label priceLabel = new Label("$" + product.getPrice() + " EGP");
        priceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        priceLabel.setTextFill(Color.LIGHTGRAY);

        // Button to view the product details
        Button showProductButton = new Button("Show Product");
        showProductButton.setCursor(Cursor.HAND);
        showProductButton.setOnMouseClicked(e -> {
            mainApp.showSelectProductPage(false);
        });
        showProductButton.setStyle("-fx-background-color: #006fff; -fx-text-fill: white; -fx-border-radius: 10px; -fx-padding: 10px; -fx-font-size: 14px;");

        Label categoryLabel = new Label(product.getCategoryId());
        categoryLabel.setVisible(false);

        VBox productBox = new VBox(10, productImageView, nameLabel, priceLabel, showProductButton, categoryLabel);
        productBox.setAlignment(Pos.CENTER);
        grid.getChildren().add(productBox);
        FlowPane.setMargin(productBox, new Insets(0, 0, 50, 0));

        allProducts.add(productBox);
    }
}