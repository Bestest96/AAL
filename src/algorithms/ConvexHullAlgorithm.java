package algorithms;

import model.Point;
import utilities.AngleComparator;

import java.util.*;

public class ConvexHullAlgorithm {

    private List<Point> originalPoints;
    private List<Point> spiralPoints;

    public ConvexHullAlgorithm(List<Point> originalPoints) {
        this.originalPoints = originalPoints;
        spiralPoints = new ArrayList<>();
    }

    private int counterClockWiseCheck(Point p1, Point p2, Point p3) {
        return (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) - (p2.getX() - p1.getX()) * (p3.getY() - p2.getY());
    }

    private Point getNextToTopElement(Stack<Point> S) {
        Point toPush = S.pop();
        Point toRet = S.peek();
        S.push(toPush);
        return toRet;

    }

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

    private Stack<Point> calculateConvexHull(List<Point> points) {
        Point minPoint = findMinPoint(points);
        points.remove(minPoint);
        points.sort(new AngleComparator(minPoint));
        /*if (originalPoints.size() < 3)
            throw new Exception("Oops");*/
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

    private Stack<Stack<Point>> getConvexHulls() {
        List<Point> points = new ArrayList<>(originalPoints);
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

    public List<Point> findSpiral() {
        Stack<Stack<Point>> convexHulls = getConvexHulls();
        while (!convexHulls.isEmpty()) {
            Stack<Point> actual = convexHulls.pop();
            Queue<Point> actualRest = new ArrayDeque<>();
            if (spiralPoints.size() > 1) {
                Point lastPoint = spiralPoints.get(spiralPoints.size() - 1);
                Point stlPoint = spiralPoints.get(spiralPoints.size() - 2);
                while (!actual.isEmpty()) {
                    Point toCheck = actual.peek();
                    int check = (lastPoint.getX() - stlPoint.getX()) * (toCheck.getY() - stlPoint.getY()) - (toCheck.getX() - stlPoint.getX()) * (lastPoint.getY() - stlPoint.getY());
                    if (check <= 0)
                        break;
                    else
                        actualRest.add(actual.pop());
                }
            }
            while (!actual.isEmpty())
                spiralPoints.add(actual.pop());
            while (!actualRest.isEmpty())
                spiralPoints.add(actualRest.remove());
        }
        return spiralPoints;
    }
}
