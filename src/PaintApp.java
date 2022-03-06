import javafx.application.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.input.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.*;


public class PaintApp extends Application {
    private Pane drawingPane;
    private Button clearButton;
    private int brushSize;

    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private static final int CANVAS_HEIGHT = 700;

    @Override
    public void start(Stage primaryStage) throws Exception {
        brushSize = 5;
        VBox primaryBox = new VBox();
        primaryBox.setStyle("-fx-background-color: null;");
        primaryBox.setSpacing(20);
        primaryBox.setAlignment(Pos.CENTER);

        drawingPane = new Pane();
        drawingPane.setOnMouseDragged(this::handleMouseDrag);
        primaryBox.getChildren().add(drawingPane);

        Rectangle canvas = new Rectangle(SCREEN_WIDTH, CANVAS_HEIGHT);
        canvas.setFill(Color.WHITE);
        drawingPane.getChildren().add(canvas);

        clearButton = new Button("Clear Drawing");
        clearButton.setOnAction(event -> {
            drawingPane.getChildren().clear();
            drawingPane.getChildren().add(canvas);
        });
        primaryBox.getChildren().addAll(clearButton);

        Scene scene = new Scene(primaryBox, SCREEN_WIDTH, SCREEN_HEIGHT, Color.BISQUE);
        primaryStage.setTitle("Paint App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMouseDrag(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        if (y + brushSize < CANVAS_HEIGHT && y - brushSize > 0) { // Prevent out of bounds
            Circle newCircle = new Circle(x, y, brushSize);
            drawingPane.getChildren().add(newCircle); 
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
