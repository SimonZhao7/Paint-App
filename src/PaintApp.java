import javafx.application.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.*;


public class PaintApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox primaryBox = new VBox();

        Scene scene = new Scene(primaryBox, 1920, 1080, Color.BISQUE);
        primaryStage.setTitle("Paint App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
