package view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.Point;

import java.util.List;
import java.util.Stack;

public class SpiralView {

    private List<Point> points;
    private Scene scene;
    private Group group;

    public SpiralView(List<Point> points) {
        this.points = points;
        group = new Group();
        scene = new Scene(group, View.getWIDTH(), View.getHEIGHT());
    }

    public void drawSpiral() {
        for (int i = 0; i < points.size() - 1; ++i) {
            int startX = points.get(i).getX();
            int startY = points.get(i).getY();
            int endX = points.get(i + 1).getX();
            int endY = points.get(i + 1).getY();
            System.out.println(startX + " " + startY);
            System.out.println(endX + " " + endY);
            System.out.println("");
            Line line = new Line(startX, startY, endX, endY);
            line.setStroke(Color.RED);
            group.getChildren().add(line);
        }
        View.setScene(scene);
        View.show();
    }

    public void drawConvexHulls(Stack<Stack<Point>> points) {
        if (points.peek().size() == 2) {
            Stack<Point> xd = points.pop();
            Point p1 = xd.pop();
            Point p2 = xd.pop();
            Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            group.getChildren().add(line);
        }
        while (points.size() != 0) {
            Stack<Point> ch = points.pop();
            Point topElement = ch.peek();
            while (ch.size() != 1) {
                Point p1 = ch.pop();
                Point p2 = ch.peek();
                Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                group.getChildren().add(line);
            }
            Point p1 = ch.pop();
            Line line = new Line(p1.getX(), p1.getY(), topElement.getX(), topElement.getY());
            group.getChildren().add(line);
        }
        View.setScene(scene);
        View.show();
    }
}
