package algorithms;

import model.Point;
import model.Vector;
import utilities.AngleComparator;

import java.text.DecimalFormat;
import java.util.*;

public class ConvexHullAlgorithm implements SpiralAlgorithm {

    private List<Point> spiralPoints;

    public ConvexHullAlgorithm() {
        spiralPoints = new ArrayList<>();
    }

    public List<Point> getSpiralPoints() {
        return spiralPoints;
    }

    // needed for finding convex hull, checks p3 placement with respect to two former points
    private double counterClockWiseCheck(Point p1, Point p2, Point p3) {
        return (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) - (p2.getX() - p1.getX()) * (p3.getY() - p2.getY());
    }

    // returns second element on stack
    private Point getNextToTopElement(Stack<Point> S) {
        Point toPush = S.pop();
        Point toRet = S.peek();
        S.push(toPush);
        return toRet;

    }

    // finds point with minimum y coordinate in a list of points
    private Point findMinPoint(List<Point> points) {
        Point minPoint = points.get(0);
        for (int i = 1; i < points.size(); ++i) {
            Point p = points.get(i);
            if (p.getY() < minPoint.getY())
                minPoint = p;
            else if (p.getY() == minPoint.getY() && p.getX() < minPoint.getX())
                minPoint = p;
        }
        return minPoint;
    }

    // calculates convex hull for a list of points, complexity O(n*log(n))
    private Stack<Point> calculateConvexHull(List<Point> points) {
        Point minPoint = findMinPoint(points);
        points.remove(minPoint);
        points.sort(new AngleComparator(minPoint)); //reason for O(n*log(n))
        Stack<Point> convexHull = new Stack<>();
        convexHull.push(minPoint);
        convexHull.push(points.get(0));
        convexHull.push(points.get(1));
        for (int i = 2; i < points.size(); ++i) {
            while (counterClockWiseCheck(getNextToTopElement(convexHull), convexHull.peek(), points.get(i)) > 0)
                convexHull.pop();
            convexHull.push(points.get(i));
        }
        return convexHull;
    }

    // return all convex hulls in a list of points, worst-case complexity is O(n^2)
    public Stack<Stack<Point>> getConvexHulls(List<Point> points) {
        Stack<Stack<Point>> convexHulls = new Stack<>();
        while (points.size() >= 3) {
            Stack<Point> convexHull = calculateConvexHull(points);
            convexHulls.push(convexHull);
            for (Point p : convexHull)
                points.remove(p);
        }
        if (points.size() != 0) {
            Stack<Point> lastPoints = new Stack<>();
            Point lastMinPoint = findMinPoint(points);
            points.remove(lastMinPoint);
            points.sort(new AngleComparator(lastMinPoint));
            lastPoints.push(lastMinPoint);
            for (Point p : points)
                lastPoints.push(p);
            convexHulls.push(lastPoints);
        }
        return convexHulls;
    }

    public List<Point> findSpiral(List<Point> points) {
        spiralPoints.clear();
        Stack<Stack<Point>> convexHulls = getConvexHulls(points); // O(n^2)
        while (!convexHulls.isEmpty()) {
            Stack<Point> actual = convexHulls.pop();
            Queue<Point> actualRest = new ArrayDeque<>();
            if (spiralPoints.size() > 1) { // finds the next point to connect to in order to not create an angle greater than 180 degrees
                Point lastPoint = spiralPoints.get(spiralPoints.size() - 1);
                Point stlPoint = spiralPoints.get(spiralPoints.size() - 2);
                if (lastPoint.getX() == stlPoint.getX()) { //we cannot create a linear equation, special case
                    double x = lastPoint.getX();
                    while (!actual.isEmpty()) {
                        Point top = actual.peek();
                        Point nextToTop = (actual.size() > 1) ? getNextToTopElement(actual) : actualRest.element();
                        double a = (nextToTop.getY() - top.getY()) / (nextToTop.getX() - top.getX());
                        double b = nextToTop.getY() - a * nextToTop.getX();
                        double y = a * x + b;
                        y = Double.parseDouble(new DecimalFormat("#.##").format(y).replace(',', '.')); //to eliminate numerical errors
                        double y1 = top.getY();
                        double y2 = nextToTop.getY();
                        if (Math.signum(lastPoint.getY() - stlPoint.getY()) == Math.signum(y - lastPoint.getY()) && ((y <= y1 && y >= y2) || (y >= y1 && y <= y2))) {
                            spiralPoints.add(new Point(x, y)); // point found
                            break;
                        }
                        actualRest.offer(actual.pop());
                    }
                }
                else {
                    double a1 = (lastPoint.getY() - stlPoint.getY()) / (lastPoint.getX() - stlPoint.getX());
                    double b1 = lastPoint.getY() - a1 * lastPoint.getX();
                    while (!actual.isEmpty()) {
                        Point top = actual.peek();
                        Point nextToTop = (actual.size() > 1) ? getNextToTopElement(actual) : actualRest.element();
                        if (top.getX() == nextToTop.getX()) { // cannot create a linear equation from convex points
                            double crossY = a1 * top.getX() + b1;
                            crossY = Double.parseDouble(new DecimalFormat("#.##").format(crossY).replace(',', '.')); //to eliminate numerical errors
                            double y1 = top.getY();
                            double y2 = nextToTop.getY();
                            if (Math.signum(lastPoint.getX() - stlPoint.getX()) == Math.signum(top.getX() - lastPoint.getX()) && ((crossY >= y1 && crossY <= y2) || (crossY <= y1 && crossY >= y2))) {
                                spiralPoints.add(new Point(top.getX(), crossY)); // point found
                                break;
                            }
                        }
                        else {
                            double a2 = (nextToTop.getY() - top.getY()) / (nextToTop.getX() - top.getX());
                            double b2 = nextToTop.getY() - a2 * nextToTop.getX();
                            if (a1 != a2) { // eliminates parallel lines
                                double crossX = (b1 - b2) / (a2 - a1);
                                crossX = Double.parseDouble(new DecimalFormat("#.##").format(crossX).replace(',', '.')); //to eliminate numerical errors
                                double x1 = top.getX();
                                double x2 = nextToTop.getX();
                                if (Math.signum(lastPoint.getX() - stlPoint.getX()) == Math.signum(crossX - lastPoint.getX()) && ((crossX >= x1 && crossX <= x2) || (crossX <= x1 && crossX >= x2))) {
                                    double crossY = a2 * crossX + b2;
                                    spiralPoints.add(new Point(crossX, crossY)); // point found
                                    break;
                                }
                            }
                        }
                        actualRest.offer(actual.pop());
                    }
                }
            }
            if (!actual.isEmpty())
                actualRest.offer(actual.pop());
            while (!actual.isEmpty()) // add rest of the points from convex to the spiral
                spiralPoints.add(actual.pop());
            while (!actualRest.isEmpty())
                spiralPoints.add(actualRest.poll());
        }
        return spiralPoints;
    }
}
