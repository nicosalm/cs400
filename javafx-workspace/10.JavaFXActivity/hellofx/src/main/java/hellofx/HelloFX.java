package hellofx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.Group;
import javafx.stage.Stage;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {

        /**
         * String javaVersion = System.getProperty("java.version");
         * String javafxVersion = System.getProperty("javafx.version");
         * Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " +
         * javaVersion + ".");
         */

        stage.setTitle("CS400: The Key");

        Label l = new Label("    The key to making programs fast\n" +
                "    is to make them do practically nothing.\n" +
                "    -- Mike Haertel");

        Circle c = new Circle(160, 120, 30);
        Polygon t = new Polygon(160, 120, 200, 220, 120, 220);

        Group g = new Group(l, c, t);

        Scene scene = new Scene(g, 320, 240);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}