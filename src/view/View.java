package view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class View {

    private static Stage stage;

    private static final int WIDTH = 1440;
    private static final int HEIGHT = 810;

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
        stage.setResizable(false);
        stage.setTitle("AAL");
        stage.setAlwaysOnTop(true);
        stage.setX(0);
        stage.setY(0);
        stage.show();
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

}
