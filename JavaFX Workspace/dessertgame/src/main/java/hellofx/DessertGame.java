package hellofx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.geometry.Pos;
import java.util.Random;

public class DessertGame extends Application {

    private int score;

    @Override
    public void start(final Stage stage) {
        Random random = new Random();

        // Step 3 & 4
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 640, 480);
        stage.setTitle("Dessert in the Desert JavaFX Game");

        // Step 5
        Label scoreLabel = new Label("Score: 0");
        borderPane.setTop(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.TOP_LEFT);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            Platform.exit();
        });
        borderPane.setBottom(exitButton);
        BorderPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);

        // Step 6
        Pane pane = new Pane();
        borderPane.setCenter(pane);
        BorderPane.setAlignment(pane, Pos.CENTER);

        // Step 7 & 8: Button creation
        Button[] buttonArray = new Button[8];
        for (int i = 0; i < buttonArray.length; i++) {
            buttonArray[i] = new Button(i == 7 ? "Dessert" : "Desert");
            pane.getChildren().add(buttonArray[i]);
        }
        randomizeButtonPosition(random, buttonArray);
        exitButton.requestFocus();

        stage.setScene(scene);
        stage.show();

        // 9: Score and button position randomization
        for (Button button : buttonArray) {
            button.setOnAction(event -> {
                score = button.getText().equals("Dessert") ? score + 1 : score - 1;
                randomizeButtonPosition(random, buttonArray);
                exitButton.requestFocus();
                scoreLabel.setText("Score: " + score);
            });
        }
    }

    /**
     * Randomize position of X [0, 600], inclusive and Y [0, 400], inclusive
     * 
     * @param random Random object
     * @param array  Button array
     */
    private void randomizeButtonPosition(Random random, Button[] array) {
        for (Button button : array) {
            button.setLayoutX(random.nextInt(601));
            button.setLayoutY(random.nextInt(401));
        }
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
