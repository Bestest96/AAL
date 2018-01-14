package view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class View {

    private static Stage stage;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        View.stage = stage;
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public static void show() {
        stage.show();
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }
}
