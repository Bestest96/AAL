/*
    Lukasz Lepak, 277324
    AAL 17Z, projekt
    Tytuł projektu: Generacja spirali ze zbioru punktów
    prowadzący: dr inż. Tomasz Gambin
 */
package utilities;

import model.Point;
import model.Vector;

import java.util.Comparator;

public class ModulusComparator implements Comparator<Point> {

    private Point O;

    public ModulusComparator(Point O) {
        this.O = O;
    }

    @Override
    public int compare(Point p1, Point p2) {
        Vector v1 = new Vector(O, p1);
        Vector v2 = new Vector(O, p2);
        double squaredDistance1 = v1.squaredModulus();
        double squaredDistance2 = v2.squaredModulus();
        int compare = Double.compare(squaredDistance1, squaredDistance2);
        if (compare != 0)
            return compare;
        else {
            double angle1 = v1.xAxisAngle();
            double angle2 = v2.xAxisAngle();
            return Double.compare(angle1, angle2);
        }
    }
}
