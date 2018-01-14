package sample;

import algorithms.ConvexHullAlgorithm;
import algorithms.SortAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Point;
import model.Vector;
import utilities.AngleComparator;
import view.SpiralView;
import view.View;

import java.util.*;
import java.util.stream.Collectors;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println(new Vector(new Point(400, 300), new Point(200, 300)).xAxisAngle());
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
        SortAlgorithm sa = new SortAlgorithm(points2);
        SpiralView sv = new SpiralView(cha.findSpiral());
        sv.drawSpiral();
        //sv.drawConvexHulls(cha.getConvexHulls());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
