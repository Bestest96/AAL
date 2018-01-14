package algorithms;

import model.Point;
import model.Vector;
import utilities.ModulusComparator;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class SortAlgorithm {

    private List<Point> originalPoints;
    private List<Point> spiralPoints;
    private Point O;

    public SortAlgorithm(List<Point> originalPoints) {
        this.originalPoints = originalPoints;
        spiralPoints = new ArrayList<>();
        this.O = new Point(View.getWIDTH() / 2, View.getHEIGHT() / 2);
    }

    public List<Point> findSpiral() {
        List<Point> points = new ArrayList<>(originalPoints);
        points.sort(new ModulusComparator(O));
        spiralPoints.add(points.get(0));
        double actualAngle = new Vector(O, points.get(0)).xAxisAngle();
        points.remove(0);
        while (points.size() > 0) {
            int i = 0;
            while (i < points.size()) {
                double angle = new Vector(O, points.get(i)).xAxisAngle();
                if (angle >= 0)
                    angle -= 2 * Math.PI;
                if (actualAngle > angle)
                    ++i;
                else
                    break;
            }
            if (i == points.size()) {
                actualAngle -= 2 * Math.PI;
                continue;
            }
            spiralPoints.add(points.get(i));
            actualAngle = new Vector(O, points.get(i)).xAxisAngle();
            points.remove(i);
        }
        return spiralPoints;
    }

}
