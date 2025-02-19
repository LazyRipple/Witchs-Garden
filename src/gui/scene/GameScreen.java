package gui.scene;

import guiSharedObject.Entity;
import guiSharedObject.InputUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

import static guiSharedObject.RenderableHolder.*;

// this is the screen of map of game
public class GameScreen extends Canvas {

    public GameScreen(double width, double height) {
        super(width, height);
        this.setVisible(true);
        addListener();
    }

    public void addListener() {
        this.setOnKeyPressed((KeyEvent event) -> {
            InputUtility.setKeyPressed(event.getCode(), true);
        });

        this.setOnKeyReleased((KeyEvent event) -> {
            InputUtility.setKeyPressed(event.getCode(), false);
        });
    }

    public void paintComponent() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        for (Entity entity : getInstance().getEntities()) {
            if (entity.isVisible() && !entity.isDestroyed()) {
                entity.draw(gc);
            }
        }
    }

}