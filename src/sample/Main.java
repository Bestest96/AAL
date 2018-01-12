package sample;

import algorithms.ConvexHullAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Point;
import view.SpiralView;
import view.View;

import java.util.*;
import java.util.stream.Collectors;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        View.setStage(primaryStage);
        List<Point> points = new ArrayList<>();
        Random random = new Random();
        while (points.size() != 100) {
            points.add(new Point(random.nextInt(View.getWIDTH()), random.nextInt(View.getHEIGHT())));
        }
        List<Point> points2 = points.stream().distinct().collect(Collectors.toList());
        System.out.println(points2.size());
        ConvexHullAlgorithm cha = new ConvexHullAlgorithm(points2);
        SpiralView sv = new SpiralView(cha.findSpiral());
        sv.drawConvexHulls(cha.getConvexHulls());
        sv.drawSpiral();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
