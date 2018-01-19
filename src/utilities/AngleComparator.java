package utilities;

import model.Point;
import model.Vector;

import java.util.Comparator;

public class AngleComparator implements Comparator<Point> {

    private Point O;

    public AngleComparator(Point O) {
        this.O = O;
    }

    @Override
    public int compare(Point p1, Point p2) {
        Vector v1 = new Vector(O, p1);
        Vector v2 = new Vector(O, p2);
        double angle1 = v1.xAxisAngle();
        double angle2 = v2.xAxisAngle();
        int compare = Double.compare(angle1, angle2);
        if (compare != 0)
            return compare;
        else {
            double squaredDistance1 = v1.squaredModulus();
            double squaredDistance2 = v2.squaredModulus();
            return Double.compare(squaredDistance1, squaredDistance2);
        }
    }
}
