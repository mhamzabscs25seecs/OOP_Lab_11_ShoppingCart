package Lab_11;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class ShoppingCartGUI extends Application {
    private static final String ROOT_STYLE = "-fx-background-color: linear-gradient(to bottom right, #f6f7fb, #e9f2ef);"
            + "-fx-font-family: 'Segoe UI', Arial, sans-serif;";
    private static final String HEADER_STYLE = "-fx-padding: 22 26;"
            + "-fx-background-color: #12211d;"
            + "-fx-border-color: #2f6b5f;"
            + "-fx-border-width: 0 0 4 0;";
    private static final String PANEL_STYLE = "-fx-background-color: white;"
            + "-fx-background-radius: 8;"
            + "-fx-border-color: #d6dedb;"
            + "-fx-border-radius: 8;"
            + "-fx-padding: 16;"
            + "-fx-effect: dropshadow(gaussian, rgba(20, 38, 34, 0.12), 18, 0.18, 0, 6);";
    private static final String SUMMARY_CARD_STYLE = "-fx-background-color: #f7fbfa;"
            + "-fx-background-radius: 8;"
            + "-fx-padding: 10 16;"
            + "-fx-min-width: 132;"
            + "-fx-border-color: #b7d4cc;"
            + "-fx-border-radius: 8;";
    private static final String TEXT_INPUT_STYLE = "-fx-background-radius: 8;"
            + "-fx-border-radius: 8;"
            + "-fx-border-color: #b9c8c3;"
            + "-fx-background-color: #fbfdfc;";
    private static final String PRIMARY_BUTTON_STYLE = "-fx-background-radius: 8;"
            + "-fx-border-radius: 8;"
            + "-fx-font-weight: 800;"
            + "-fx-cursor: hand;"
            + "-fx-padding: 9 15;"
            + "-fx-background-color: #1f7a64;"
            + "-fx-text-fill: white;";
    private static final String SECONDARY_BUTTON_STYLE = "-fx-background-radius: 8;"
            + "-fx-border-radius: 8;"
            + "-fx-font-weight: 800;"
            + "-fx-cursor: hand;"
            + "-fx-padding: 9 15;"
            + "-fx-background-color: #e2eee9;"
            + "-fx-text-fill: #16352e;";
    private static final String DANGER_BUTTON_STYLE = "-fx-background-radius: 8;"
            + "-fx-border-radius: 8;"
            + "-fx-font-weight: 800;"
            + "-fx-cursor: hand;"
            + "-fx-padding: 9 15;"
            + "-fx-background-color: #b84a4a;"
            + "-fx-text-fill: white;";
    private static final String TABLE_STYLE = "-fx-background-radius: 8;"
            + "-fx-border-radius: 8;"
            + "-fx-border-color: #cad7d3;"
            + "-fx-background-color: white;";
    private static final String CHECKOUT_BOX_STYLE = "-fx-padding: 12;"
            + "-fx-background-color: #f2f7f5;"
            + "-fx-background-radius: 8;"
            + "-fx-border-color: #d1dfda;"
            + "-fx-border-radius: 8;";
    private static final String STATUS_BAR_STYLE = "-fx-padding: 12 18;"
            + "-fx-background-color: white;"
            + "-fx-border-color: #d8e2df;"
            + "-fx-border-width: 1 0 0 0;";
    private static final String STATUS_BASE_STYLE = "-fx-font-weight: 700;";
    private static final String ERROR_POPUP_STYLE = "-fx-background-color: #b84a4a;"
            + "-fx-background-radius: 18;"
            + "-fx-border-color: #7f2929;"
            + "-fx-border-radius: 18;"
            + "-fx-border-width: 2;"
            + "-fx-effect: dropshadow(gaussian, rgba(80, 20, 20, 0.35), 18, 0.25, 0, 6);"
            + "-fx-font-size: 15px;"
            + "-fx-font-weight: 800;"
            + "-fx-padding: 14 22;"
            + "-fx-text-fill: white;";

    private static final String[] PAYMENT_GATEWAYS = {
            "Cash-On-Delivery",
            "Credit Card",
            "EasyPaisa",
            "JazzCash"
    };

    private final ShoppingCart cart = new ShoppingCart();
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<CartItem> cartRows = FXCollections.observableArrayList();

    private final TableView<Product> productTable = new TableView<>();
    private final TableView<CartItem> cartTable = new TableView<>();

    private final TextField productIdField = new TextField();
    private final TextField productNameField = new TextField();
    private final TextField productPriceField = new TextField();
    private final TextField productStockField = new TextField();
    private final TextField quantityField = new TextField("1");

    private final ComboBox<String> paymentBox = new ComboBox<>();
    private final TextArea activityArea = new TextArea();
    private final Label statusLabel = new Label("Ready to shop.");
    private final Label totalLabel = new Label("PKR 0");
    private final Label itemCountLabel = new Label("0 items");

    @Override
    public void start(Stage stage) {
        seedProducts();
        setupTables();
        setupControls();

        BorderPane root = new BorderPane();
        root.setStyle(ROOT_STYLE);
        root.setTop(createHeader());
        root.setCenter(createMainContent());
        root.setBottom(createStatusBar());

        refreshCart();

        Scene scene = new Scene(root, 1050, 680);
        stage.setTitle("Lab 11 - Shopping Cart Management");
        stage.setMinWidth(950);
        stage.setMinHeight(620);
        stage.setScene(scene);
        stage.show();
    }

    private HBox createHeader() {
        Label title = new Label("Shopping Cart");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: 800; -fx-text-fill: white;");

        Label subtitle = new Label("Products, cart updates, exception handling, and payment gateway checkout");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #c8d8d3;");

        VBox titleBox = new VBox(3, title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox totalCard = new VBox(4, new Label("Cart Total"), totalLabel);
        totalCard.setStyle(SUMMARY_CARD_STYLE);
        totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 800; -fx-text-fill: #12211d;");

        VBox itemCard = new VBox(4, new Label("Cart Size"), itemCountLabel);
        itemCard.setStyle(SUMMARY_CARD_STYLE);
        itemCountLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 800; -fx-text-fill: #12211d;");

        HBox header = new HBox(18, titleBox, spacer, itemCard, totalCard);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle(HEADER_STYLE);
        return header;
    }

    private HBox createMainContent() {
        VBox leftPanel = createProductPanel();
        VBox rightPanel = createCartPanel();

        HBox content = new HBox(18, leftPanel, rightPanel);
        content.setPadding(new Insets(18));
        HBox.setHgrow(leftPanel, Priority.ALWAYS);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);
        return content;
    }

    private VBox createProductPanel() {
        Label sectionTitle = new Label("Product Catalog");
        sectionTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 800; -fx-text-fill: #152b25;");

        GridPane productForm = new GridPane();
        productForm.setStyle("-fx-padding: 8 0 0 0;");
        productForm.setHgap(10);
        productForm.setVgap(10);

        configureTextField(productIdField, "ID");
        configureTextField(productNameField, "Product name");
        configureTextField(productPriceField, "Price");
        configureTextField(productStockField, "Stock");

        productForm.add(new Label("Product ID"), 0, 0);
        productForm.add(productIdField, 1, 0);
        productForm.add(new Label("Name"), 0, 1);
        productForm.add(productNameField, 1, 1);
        productForm.add(new Label("Price"), 0, 2);
        productForm.add(productPriceField, 1, 2);
        productForm.add(new Label("Stock"), 0, 3);
        productForm.add(productStockField, 1, 3);

        Button createButton = new Button("Create Product");
        createButton.setStyle(PRIMARY_BUTTON_STYLE);
        createButton.setMaxWidth(Double.MAX_VALUE);
        createButton.setOnAction(event -> createProduct());

        VBox panel = new VBox(12, sectionTitle, productTable, productForm, createButton);
        panel.setStyle(PANEL_STYLE);
        VBox.setVgrow(productTable, Priority.ALWAYS);
        return panel;
    }

    private VBox createCartPanel() {
        Label sectionTitle = new Label("Cart Workspace");
        sectionTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 800; -fx-text-fill: #152b25;");

        configureTextField(quantityField, "Quantity");
        quantityField.setMaxWidth(130);

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button removeButton = new Button("Remove");

        addButton.setStyle(PRIMARY_BUTTON_STYLE);
        updateButton.setStyle(SECONDARY_BUTTON_STYLE);
        removeButton.setStyle(DANGER_BUTTON_STYLE);

        addButton.setOnAction(event -> addProductToCart());
        updateButton.setOnAction(event -> updateQuantity());
        removeButton.setOnAction(event -> removeFromCart());

        HBox cartActions = new HBox(10,
                new Label("Quantity"),
                quantityField,
                addButton,
                updateButton,
                removeButton);
        cartActions.setAlignment(Pos.CENTER_LEFT);

        paymentBox.setMaxWidth(Double.MAX_VALUE);
        paymentBox.setStyle(TEXT_INPUT_STYLE);
        Button checkoutButton = new Button("Checkout");
        checkoutButton.setStyle(PRIMARY_BUTTON_STYLE);
        checkoutButton.setMaxWidth(Double.MAX_VALUE);
        checkoutButton.setOnAction(event -> checkout());

        VBox checkoutBox = new VBox(9,
                new Label("Payment Gateway"),
                paymentBox,
                checkoutButton);
        checkoutBox.setStyle(CHECKOUT_BOX_STYLE);

        activityArea.setEditable(false);
        activityArea.setWrapText(true);
        activityArea.setPrefRowCount(5);
        activityArea.setMinHeight(115);
        activityArea.setPrefHeight(135);
        activityArea.setStyle(TEXT_INPUT_STYLE);

        VBox panel = new VBox(12,
                sectionTitle,
                cartTable,
                cartActions,
                checkoutBox,
                new Label("Activity Log"),
                activityArea);
        panel.setStyle(PANEL_STYLE);
        VBox.setVgrow(cartTable, Priority.ALWAYS);
        return panel;
    }

    private HBox createStatusBar() {
        statusLabel.setStyle(STATUS_BASE_STYLE);
        HBox bar = new HBox(statusLabel);
        bar.setStyle(STATUS_BAR_STYLE);
        bar.setAlignment(Pos.CENTER_LEFT);
        return bar;
    }

    private void setupTables() {
        productTable.setItems(products);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productTable.setPlaceholder(new Label("Create a product to begin."));
        productTable.setStyle(TABLE_STYLE);

        TableColumn<Product, Number> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> new ReadOnlyIntegerWrapper(data.getValue().getProductId()));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product");
        nameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));

        TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(formatMoney(data.getValue().getPrice())));

        TableColumn<Product, Number> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(data -> new ReadOnlyIntegerWrapper(data.getValue().getStock()));

        productTable.getColumns().setAll(idColumn, nameColumn, priceColumn, stockColumn);

        cartTable.setItems(cartRows);
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cartTable.setPlaceholder(new Label("Your cart is empty."));
        cartTable.setStyle(TABLE_STYLE);

        TableColumn<CartItem, Number> cartIdColumn = new TableColumn<>("ID");
        cartIdColumn.setCellValueFactory(data -> new ReadOnlyIntegerWrapper(data.getValue().getProduct().getProductId()));

        TableColumn<CartItem, String> cartProductColumn = new TableColumn<>("Product");
        cartProductColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProduct().getName()));

        TableColumn<CartItem, Number> quantityColumn = new TableColumn<>("Qty");
        quantityColumn.setCellValueFactory(data -> new ReadOnlyIntegerWrapper(data.getValue().getQuantity()));

        TableColumn<CartItem, String> subtotalColumn = new TableColumn<>("Subtotal");
        subtotalColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(formatMoney(data.getValue().getSubtotal())));

        cartTable.getColumns().setAll(cartIdColumn, cartProductColumn, quantityColumn, subtotalColumn);

        productTable.setOnMouseClicked(event -> cartTable.getSelectionModel().clearSelection());

        cartTable.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, selectedItem) -> {
            if (selectedItem != null) {
                productTable.getSelectionModel().select(selectedItem.getProduct());
                quantityField.setText(String.valueOf(selectedItem.getQuantity()));
            }
        });
    }

    private void setupControls() {
        productTable.getSelectionModel().selectFirst();
        paymentBox.getItems().setAll(PAYMENT_GATEWAYS);
        paymentBox.getSelectionModel().selectFirst();
        activityArea.setText("Select a product, enter quantity, and add it to the cart.");
    }

    private void seedProducts() {
        products.addAll(
                new Product(101, "Laptop", 175000, 4),
                new Product(102, "Headphones", 7500, 15),
                new Product(103, "Keyboard", 4000, 10),
                new Product(104, "Mouse", 2500, 20),
                new Product(105, "USB-C Cable", 1200, 25));
    }

    private void createProduct() {
        try {
            int id = parsePositiveInt(productIdField, "Product ID");
            String name = productNameField.getText().trim();
            int price = parsePositiveInt(productPriceField, "Price");
            int stock = parsePositiveInt(productStockField, "Stock");

            if (name.isEmpty()) {
                throw new IllegalArgumentException("Product name cannot be empty.");
            }
            if (findProduct(id) != null) {
                throw new IllegalArgumentException("Product ID " + id + " already exists.");
            }

            Product product = new Product(id, name, price, stock);
            products.add(product);
            productTable.getSelectionModel().select(product);
            clearProductForm();
            showSuccess("Created product: " + name);
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void addProductToCart() {
        Product selected = getSelectedProduct();
        if (selected == null) {
            showError("Please select a product first.");
            return;
        }

        try {
            int quantity = parseQuantity();
            cart.addProduct(selected, quantity);
            refreshCart();
            showSuccess("Added " + quantity + " x " + selected.getName() + " to cart.");
        } catch (InvalidQuantityException | InsufficientStockException | NumberFormatException ex) {
            showError(ex.getMessage());
        }
    }

    private void removeFromCart() {
        CartItem selectedItem = cartTable.getSelectionModel().getSelectedItem();
        Product selectedProduct = getSelectedProduct();
        int productId = selectedItem == null ? selectedProduct == null ? -1 : selectedProduct.getProductId()
                : selectedItem.getProduct().getProductId();

        if (productId == -1) {
            showError("Select a cart item or product to remove.");
            return;
        }

        try {
            cart.removeProduct(productId);
            refreshCart();
            showSuccess("Removed product ID " + productId + " from cart.");
        } catch (ProductNotFoundException ex) {
            showError(ex.getMessage());
        }
    }

    private void updateQuantity() {
        CartItem selectedItem = cartTable.getSelectionModel().getSelectedItem();
        Product selectedProduct = getSelectedProduct();
        int productId = selectedItem == null ? selectedProduct == null ? -1 : selectedProduct.getProductId()
                : selectedItem.getProduct().getProductId();

        if (productId == -1) {
            showError("Select a cart item or product to update.");
            return;
        }

        try {
            int quantity = parseQuantity();
            cart.updateQuantity(productId, quantity);
            refreshCart();
            showSuccess("Updated product ID " + productId + " to quantity " + quantity + ".");
        } catch (ProductNotFoundException | InvalidQuantityException | InsufficientStockException
                | NumberFormatException ex) {
            showError(ex.getMessage());
        }
    }

    private void checkout() {
        try {
            String confirmation = cart.checkout(paymentBox.getValue());
            refreshCart();
            productTable.refresh();
            showSuccess(confirmation);
        } catch (EmptyCartException | InvalidPaymentException | InsufficientStockException ex) {
            showError(ex.getMessage());
        }
    }

    private Product getSelectedProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        CartItem selectedItem = cartTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            return selectedItem.getProduct();
        }
        return selected;
    }

    private Product findProduct(int id) {
        for (Product product : products) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }

    private int parseQuantity() {
        return parsePositiveInt(quantityField, "Quantity");
    }

    private int parsePositiveInt(TextField field, String fieldName) {
        String raw = field.getText().trim();
        if (raw.isEmpty()) {
            throw new NumberFormatException(fieldName + " is required.");
        }

        try {
            int value = Integer.parseInt(raw);
            if (value <= 0) {
                throw new NumberFormatException(fieldName + " must be greater than 0.");
            }
            return value;
        } catch (NumberFormatException ex) {
            if (ex.getMessage() != null && ex.getMessage().contains("greater than 0")) {
                throw ex;
            }
            throw new NumberFormatException(fieldName + " must be a whole number.");
        }
    }

    private void refreshCart() {
        cartRows.setAll(cart.getCartItems());
        cartTable.refresh();
        productTable.refresh();

        int itemCount = 0;
        for (CartItem item : cart.getCartItems()) {
            itemCount += item.getQuantity();
        }

        totalLabel.setText(formatMoney(cart.calculateTotal()));
        itemCountLabel.setText(itemCount + (itemCount == 1 ? " item" : " items"));
    }

    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle(STATUS_BASE_STYLE + "-fx-text-fill: #1f7a64;");
        activityArea.appendText("\nSUCCESS: " + message);
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle(STATUS_BASE_STYLE + "-fx-text-fill: #b84a4a;");
        activityArea.appendText("\nERROR: " + message);
        showErrorPopup(message);
    }

    private void showErrorPopup(String message) {
        if (statusLabel.getScene() == null || statusLabel.getScene().getWindow() == null) {
            return;
        }

        Label popupMessage = new Label("ERROR: " + message);
        popupMessage.setStyle(ERROR_POPUP_STYLE);
        popupMessage.setWrapText(true);
        popupMessage.setMaxWidth(420);

        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.getContent().add(popupMessage);

        Window window = statusLabel.getScene().getWindow();
        popup.show(window);
        popupMessage.applyCss();
        popupMessage.autosize();

        double x = window.getX() + (window.getWidth() - popupMessage.getWidth()) / 2;
        double y = window.getY() + 96;
        popup.setX(x);
        popup.setY(y);

        popupMessage.setOnMouseClicked(event -> popup.hide());

        PauseTransition delay = new PauseTransition(Duration.seconds(2.8));
        delay.setOnFinished(event -> popup.hide());
        delay.play();
    }

    private void clearProductForm() {
        productIdField.clear();
        productNameField.clear();
        productPriceField.clear();
        productStockField.clear();
    }

    private void configureTextField(TextField field, String prompt) {
        field.setPromptText(prompt);
        field.setMaxWidth(Double.MAX_VALUE);
        field.setStyle(TEXT_INPUT_STYLE);
    }

    private String formatMoney(int amount) {
        return String.format("PKR %,d", amount);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
