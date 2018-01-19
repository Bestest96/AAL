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
        normalizePoints();

    }

    private void normalizePoints() {
        double maxX = Double.NEGATIVE_INFINITY;
        double minX = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        for (Point p : points) {
            maxX = Math.max(maxX, p.getX());
            minX = Math.min(minX, p.getX());
            maxY = Math.max(maxY, p.getY());
            minY = Math.min(minY, p.getY());
        }
        if (minX >= 0 && maxX <= View.getWIDTH() && minY >= 0 && maxY <= View.getHEIGHT())
            return;
        double ax = (View.getWIDTH() - 1) / (maxX - minX);
        double bx = -ax * minX;
        double ay = (View.getHEIGHT() - 1) / (maxY - minY);
        double by = -ay * minY;
        for (Point p : points) {
            p.setX(ax * p.getX() + bx);
            p.setY(ay * p.getY() + by);
        }
    }

    public void drawSpiral() {
        for (int i = 0; i < points.size() - 1; ++i) {
            double startX = points.get(i).getX();
            double startY = points.get(i).getY();
            double endX = points.get(i + 1).getX();
            double endY = points.get(i + 1).getY();
            Line line = new Line(startX, startY, endX, endY);
            line.setStroke(Color.RED);
            group.getChildren().add(line);
        }
        View.setScene(scene);
        View.show();
    }
}
