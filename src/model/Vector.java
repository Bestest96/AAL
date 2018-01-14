package model;

public class Vector {
    private int x;
    private int y;

    public Vector(Point b, Point e) {
        this.x = e.getX() - b.getX();
        this.y = e.getY() - b.getY();
    }

    public int squaredModulus() {
        return x * x + y * y;
    }

    public double xAxisAngle() {
        return Math.atan2(y, x);
    }
}
