package gui.scene;

import game.GameController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// this will appear at the end of each level of game
public class GameEnd extends VBox {
    private Button startButton;
    private Button exitButton;
    private Text endingText;

    @FunctionalInterface
    public interface GameStarter {
        void startGame();
    }
    public GameEnd(GameStarter gameStarter, Stage stage) {
        initializeEndingText();
        initializeStartButton(gameStarter);
        initializeExitButton(stage);

        // change text context
        if (GameController.getInstance().getGameTimer() == 0 ) {
            // GameOver cause time up
            endingText.setText("Time Up!! The witch fail to create a potion");
            startButton.setText("Try Again");
        } else {
            // GameOver cause player win
            endingText.setText("Potion has been made! The witch gain her power back");
            startButton.setText("Next Potion");
        }

        // Create layout and add the start and exit button
        this.setBackground(new Background(
                new BackgroundFill(Color.web("#8F6F5C"), null, null)));
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(endingText);
        this.getChildren().add(startButton);
        this.getChildren().add(exitButton);
    }

    private void initializeEndingText() {
        endingText = new Text();
        endingText.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 20)); // Set font and size
        endingText.setFill(Color.WHITE);
    }

    private void initializeStartButton(GameStarter gameStarter) {
        startButton = new Button("Start Game");
        startButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
        startButton.setTextFill(Color.web("#695244"));
        startButton.setBackground(new Background(
                new BackgroundFill(Color.PINK, new CornerRadii(5), null)));
        startButton.setPrefWidth(180);
        startButton.setPrefHeight(50);
        startButton.setOnMouseEntered(e -> {
            startButton.setBackground(new Background(
                    new BackgroundFill(Color.LIGHTPINK, new CornerRadii(5), null)));
        });
        startButton.setOnMouseExited(e -> {
            startButton.setBackground(new Background(
                    new BackgroundFill(Color.PINK, new CornerRadii(5), null)));
        });
        startButton.setOnAction(e -> gameStarter.startGame());
    }

    private void initializeExitButton(Stage stage) {
        exitButton = new Button("Exit");
        exitButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
        exitButton.setTextFill(Color.web("#695244"));
        exitButton.setBackground(new Background(
                new BackgroundFill(Color.PINK, new CornerRadii(5), null)));
        exitButton.setPrefWidth(180);
        exitButton.setPrefHeight(50);
        exitButton.setOnMouseEntered(e -> {
            exitButton.setBackground(new Background(
                    new BackgroundFill(Color.LIGHTPINK, new CornerRadii(5), null)));
        });
        exitButton.setOnMouseExited(e -> {
            exitButton.setBackground(new Background(
                    new BackgroundFill(Color.PINK, new CornerRadii(5), null)));
        });
        exitButton.setOnAction(e -> {
            stage.close();
            System.exit(0);
        });
    }
}
