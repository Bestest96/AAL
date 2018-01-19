package model;

public class Vector {
    private double x;
    private double y;

    public Vector(Point b, Point e) {
        this.x = e.getX() - b.getX();
        this.y = e.getY() - b.getY();
    }

    public double squaredModulus() {
        return x * x + y * y;
    }

    public double xAxisAngle() {
        return Math.atan2(y, x);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
