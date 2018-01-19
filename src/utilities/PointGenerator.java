package utilities;

import model.Point;
import view.View;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PointGenerator {
    private Set<Point> generatedPoints;

    public PointGenerator() {
        generatedPoints = new HashSet<>();
    }

    public Set<Point> getGeneratedPoints() {
        return generatedPoints;
    }

    public List<Point> getGeneratedPointsAsList() {
        return new ArrayList<>(generatedPoints);
    }

    public List<Point> generatePoints(int amount) {
        int maxX = View.getWIDTH();
        int maxY = View.getHEIGHT();
        generatedPoints.clear();
        while (generatedPoints.size() < amount) {
            int x = ThreadLocalRandom.current().nextInt(maxX);
            int y = ThreadLocalRandom.current().nextInt(maxY);
            generatedPoints.add(new Point(x, y));
        }
        return new ArrayList<>(generatedPoints);
    }
}
