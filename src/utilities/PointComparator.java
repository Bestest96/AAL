package utilities;

import model.Point;

import java.util.Comparator;

public class PointComparator implements Comparator<Point> {

    private Point O;

    private class Vector {
        private int x;
        private int y;

        Vector(Point b, Point e) {
            this.x = e.getX() - b.getX();
            this.y = e.getY() - b.getY();
        }

        int squaredModulus() {
            return x * x + y * y;
        }

        double xAxisAngle() {
            double theta = Math.atan2(y, x);
            return theta;
        }
    }

    public PointComparator(Point O) {
        this.O = O;
    }

    public double getAngle(Point p) {
        Vector v = new Vector(O, p);
        return v.xAxisAngle();
    }

    public int compare(Point p1, Point p2) {
        Vector v1 = new Vector(O, p1);
        Vector v2 = new Vector(O, p2);
        double angle1 = v1.xAxisAngle();
        double angle2 = v2.xAxisAngle();
        int compare = Double.compare(angle1, angle2);
        if (compare != 0)
            return compare;
        else {
            int squaredDistance1 = v1.squaredModulus();
            int squaredDistance2 = v2.squaredModulus();
            return Integer.compare(squaredDistance1, squaredDistance2);
        }
    }
}
