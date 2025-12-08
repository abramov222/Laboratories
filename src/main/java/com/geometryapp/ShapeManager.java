package com.geometryapp;

import geometry2d.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShapeManager {
    private List<Shape> shapes;
    private Canvas canvas;
    private Random random;
    private Shape selectedShape;
    private double offsetX, offsetY;

    public ShapeManager(Canvas canvas) {
        this.canvas = canvas;
        this.shapes = new ArrayList<>();
        this.random = new Random();
    }

    public void drawAll() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }

    public void addRectangle() {
        double x = random.nextDouble() * (canvas.getWidth() - 100);
        double y = random.nextDouble() * (canvas.getHeight() - 100);
        double width = 50 + random.nextDouble() * 100;
        double height = 50 + random.nextDouble() * 100;
        Color color = getRandomColor();

        shapes.add(new Rectangle(x, y, width, height, color));
        drawAll();
    }

    public void addCircle() {
        double x = random.nextDouble() * (canvas.getWidth() - 100);
        double y = random.nextDouble() * (canvas.getHeight() - 100);
        double radius = 25 + random.nextDouble() * 50;
        Color color = getRandomColor();

        shapes.add(new Circle(x, y, radius, color));
        drawAll();
    }

    public boolean selectShape(double x, double y) {
        // Ищем фигуру с конца (верхние фигуры)
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.contains(x, y)) {
                selectedShape = shape;
                offsetX = x - shape.getX();
                offsetY = y - shape.getY();

                // Перемещаем на верхний слой
                shapes.remove(i);
                shapes.add(shape);
                return true;
            }
        }
        return false;
    }

    public void moveSelectedShape(double x, double y) {
        if (selectedShape != null) {
            selectedShape.move(x - selectedShape.getX() - offsetX,
                    y - selectedShape.getY() - offsetY);
            drawAll();
        }
    }

    public void deselectShape() {
        selectedShape = null;
    }

    public void changeColorAt(double x, double y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.contains(x, y)) {
                shape.setColor(getRandomColor());
                drawAll();
                break;
            }
        }
    }

    private Color getRandomColor() {
        return Color.color(
                random.nextDouble(),
                random.nextDouble(),
                random.nextDouble()
        );
    }
}