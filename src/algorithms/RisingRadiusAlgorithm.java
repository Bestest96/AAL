/*
    Łukasz Lepak, 277324
    AAL 17Z, projekt
    Tytuł projektu: Generacja spirali ze zbioru punktów
    prowadzący: dr inż. Tomasz Gambin
 */
package algorithms;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.Point;
import model.Vector;
import utilities.AngleComparator;
import utilities.ModulusComparator;
import view.View;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RisingRadiusAlgorithm implements SpiralAlgorithm {

    private List<Point> spiralPoints;
    private Point O; // middle point of the spiral (may not be included in it)
    private int slices; // amount of slices needed to create one circle

    public RisingRadiusAlgorithm(int slices) {
        spiralPoints = new ArrayList<>();
        this.O = new Point(View.getWIDTH() / 2, View.getHEIGHT() / 2);
        this.slices = slices;
    }

    @Override
    public List<Point> getSpiralPoints() {
        return spiralPoints;
    }

    public int getSlices() {
        return slices;
    }

    public List<Point> findSpiral(List<Point> points) {
        spiralPoints.clear();
        double pointAngleChange = 2 * Math.PI / slices; // an angle required to rotate for one slice
        points.sort(new ModulusComparator(O)); // O(n*log(n))
        Point begin = points.get(0);
        points.remove(0);
        spiralPoints.add(begin);
        while (!points.isEmpty()) { //O(1,5 * slices * n)
            Point actual = points.get(0);
            points.remove(0);
            Vector beginVector = new Vector(O, begin);
            Vector actualVector = new Vector(O, actual);
            double beginAngle = beginVector.xAxisAngle();
            double actualAngle = actualVector.xAxisAngle();
            if (beginAngle < 0) beginAngle += 2 * Math.PI; // scale angles to match 0-2 * PI range
            if (actualAngle < 0) actualAngle += 2 * Math.PI;
            if (actualAngle <= beginAngle) actualAngle += 2 * Math.PI; // when actual has less angle, we need to add 2 * PI
            double difference = actualAngle - beginAngle;
            double newPointsAmount = Math.floor(difference / pointAngleChange); // additional points we need to add to the spiral
            if (newPointsAmount < slices / 2)
                newPointsAmount += slices; // do one circle more in order to avoid angles greater than 180 degrees
            double beginModulus = Math.sqrt(beginVector.squaredModulus());
            double actualModulus = Math.sqrt(actualVector.squaredModulus());
            double stepDiff = (actualModulus - beginModulus) / (newPointsAmount + 1); // modulus change during one step
            for (int i = 0; i < newPointsAmount; ++i) { // point interpolation
                double x = O.getX() + (beginModulus + (i + 1) * stepDiff) * Math.cos(beginAngle + (i + 1) * pointAngleChange);
                double y = O.getY() + (beginModulus + (i + 1) * stepDiff) * Math.sin(beginAngle + (i + 1) * pointAngleChange);
                spiralPoints.add(new Point(x, y));
            }
            spiralPoints.add(actual); // add actual point to spiral and set is as begin
            begin = actual;
        }
        return spiralPoints;
    }
}

