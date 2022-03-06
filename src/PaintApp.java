import javafx.application.*;
import javafx.collections.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.input.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.*;
import java.util.*;


public class PaintApp extends Application {
    private VBox primaryBox;
    private Pane drawingPane;
    private Button clearButton;
    private int brushSize;

    private List<Node> tempStrokes = new ArrayList<>();

    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private static final int CANVAS_HEIGHT = 700;

    @Override
    public void start(Stage primaryStage) throws Exception {
        brushSize = 5;
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

    private void handleMouseDrag(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        if (y + brushSize < CANVAS_HEIGHT && y - brushSize > 0) { // Prevent out of bounds
            Circle newCircle = new Circle(x, y, brushSize);
            drawingPane.getChildren().add(newCircle); 
        }
    }

    private void setPaintScreen() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Paint");
        MenuItem paint = new MenuItem("Home");
        paint.setOnAction(this::handlePaintSelect);
        MenuItem settings = new MenuItem("Settings");
        menu.getItems().add(paint);
        menu.getItems().add(settings);
        menuBar.getMenus().add(menu);
        primaryBox.getChildren().add(menuBar);

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

    public static void main(String[] args) {
        launch(args);
    }
}
