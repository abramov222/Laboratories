package geometry2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected double x, y;
    protected Color color;

    public Shape(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public abstract void draw(GraphicsContext gc);
    public abstract boolean contains(double px, double py);

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public double getX() { return x; }
    public double getY() { return y; }
}