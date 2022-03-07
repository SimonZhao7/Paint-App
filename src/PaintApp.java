import javafx.application.*;
import javafx.collections.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.input.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.text.*;
import java.util.*;


public class PaintApp extends Application {
    // Control
    private VBox primaryBox;
    private Pane drawingPane;
    private Button clearButton, changeBrushSizeButton, changeBrushColorButton;
    private TextField brushSizeInput, brushColorInput;

    // Settings
    private int brushSize;
    private Color brushColor;

    private List<Node> tempStrokes = new ArrayList<>();

    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private static final int CANVAS_HEIGHT = 700;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize Settings
        brushSize = 5;
        brushColor = Color.BLACK;

        primaryBox = new VBox();
        primaryBox.setStyle("-fx-background-color: null;");
        primaryBox.setSpacing(20);

        setPaintScreen();

        Scene scene = new Scene(primaryBox, SCREEN_WIDTH, SCREEN_HEIGHT, Color.BISQUE);
        primaryStage.setTitle("Paint App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handlePaintSelect(ActionEvent event) {
        ObservableList<Node> currentStrokes = drawingPane.getChildren();
        tempStrokes.clear();
        primaryBox.getChildren().clear();
        currentStrokes.remove(0);
        for (Node node : currentStrokes) {
            tempStrokes.add(node);
        }
        currentStrokes.clear();
        setPaintScreen();
    }

    private void handleSettingsSelect(ActionEvent event) {
        primaryBox.getChildren().clear();
        setSettingsScreen();
    }

    private void handleMouseDrag(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        if (y + brushSize < CANVAS_HEIGHT && y - brushSize > 0) { // Prevent out of bounds
            Circle newCircle = new Circle(x, y, brushSize, brushColor);
            drawingPane.getChildren().add(newCircle); 
        }
    }

    private void handleBrushSizeSubmit(ActionEvent event) {
        String inputText = brushSizeInput.getText();
        String errorMessage = "";
        try {
            if (inputText.isEmpty()) {
                errorMessage = "Brush size may not be empty";
                throw new IllegalArgumentException();
            }

            try {
                int brushSizeInt = Integer.parseInt(inputText);
                if (brushSizeInt < 1 || brushSizeInt > 100) {
                    errorMessage = "Brush size must be at least 1px and no greater than 100px";
                    throw new IllegalArgumentException();
                }
                brushSize = brushSizeInt;
                raiseAlert(AlertType.INFORMATION, "You have successfully changed the brush size.");
                brushSizeInput.clear();
            } catch (NumberFormatException e) {
                errorMessage = "Brush size must be a number";
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            raiseAlert(AlertType.ERROR, errorMessage);
        }
    }

    private void handleBrushColorSubmit(ActionEvent event) {
        String inputText = brushColorInput.getText();
        String errorMessage = "Invalid hex code (ex: #000000)";
        try {
            if (inputText.isEmpty()) {
                errorMessage = "Brush color may not be empty";
                throw new IllegalArgumentException();
            }

            Color newColor = Color.web(inputText);
            brushColor = newColor;
            raiseAlert(AlertType.INFORMATION, "You have successfully changed the brush color.");
            brushColorInput.clear();
        } catch (IllegalArgumentException e) {
            raiseAlert(AlertType.ERROR, errorMessage);
        }
    }

    private void setPaintScreen() {
        setupMenuBar();
        drawingPane = new Pane();
        drawingPane.setOnMouseDragged(this::handleMouseDrag);
        primaryBox.getChildren().add(drawingPane);

        Rectangle canvas = new Rectangle(SCREEN_WIDTH, CANVAS_HEIGHT);
        canvas.setFill(Color.WHITE);
        drawingPane.getChildren().add(canvas);
        drawingPane.getChildren().addAll(tempStrokes);

        clearButton = new Button("Clear Drawing");
        clearButton.setOnAction(event -> {
            drawingPane.getChildren().clear();
            drawingPane.getChildren().add(canvas);
        });
        HBox buttonContainer = new HBox(clearButton);
        buttonContainer.setAlignment(Pos.CENTER);
        primaryBox.getChildren().addAll(buttonContainer);
    }

    private void setSettingsScreen() {
        setupMenuBar();
        brushSizeInput = new TextField();
        brushSizeInput.setOnAction(this::handleBrushSizeSubmit);
        Text brushSizeLabel = new Text("Brush Size: ");
        VBox brushSizeBox = new VBox(brushSizeLabel, brushSizeInput);
        brushSizeBox.setSpacing(5);
        primaryBox.getChildren().add(brushSizeBox);

        changeBrushSizeButton = new Button("Change Brush Size");
        changeBrushSizeButton.setOnAction(this::handleBrushSizeSubmit);
        HBox brushButtonBox = new HBox(changeBrushSizeButton);
        primaryBox.getChildren().add(brushButtonBox);

        brushColorInput = new TextField();
        brushColorInput.setOnAction(this::handleBrushColorSubmit);
        Text brushColorLabel = new Text("Brush Color: ");
        VBox brushColorBox = new VBox(brushColorLabel, brushColorInput);
        brushColorBox.setSpacing(5);
        primaryBox.getChildren().add(brushColorBox);

        changeBrushColorButton = new Button("Change Brush Color");
        changeBrushColorButton.setOnAction(this::handleBrushColorSubmit);
        HBox brushColorButtonBox = new HBox(changeBrushColorButton);
        primaryBox.getChildren().add(brushColorButtonBox);
    }

    private void setupMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Paint");
        MenuItem paint = new MenuItem("Home");
        paint.setOnAction(this::handlePaintSelect);
        MenuItem settings = new MenuItem("Settings");
        settings.setOnAction(this::handleSettingsSelect);
        menu.getItems().add(paint);
        menu.getItems().add(settings);
        menuBar.getMenus().add(menu);
        primaryBox.getChildren().add(menuBar);
    }

    private void raiseAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
