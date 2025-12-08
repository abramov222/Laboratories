package com.geometryapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class GeometryController {
    @FXML private Canvas canvas;
    @FXML private Button rectangleButton;
    @FXML private Button circleButton;

    private ShapeManager shapeManager;

    @FXML
    public void initialize() {
        shapeManager = new ShapeManager(canvas);

        // Обработчики для мыши
        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);
        canvas.setOnMouseReleased(this::handleMouseReleased);

        // Обработчики для кнопок
        rectangleButton.setOnAction(e -> shapeManager.addRectangle());
        circleButton.setOnAction(e -> shapeManager.addCircle());

        // Автомасштабирование канваса
        canvas.widthProperty().bind(
                ((javafx.scene.layout.Pane) canvas.getParent()).widthProperty()
        );
        canvas.heightProperty().bind(
                ((javafx.scene.layout.Pane) canvas.getParent()).heightProperty()
        );
    }

    private void handleMousePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            shapeManager.selectShape(event.getX(), event.getY());
        } else if (event.getButton() == MouseButton.SECONDARY) {
            shapeManager.changeColorAt(event.getX(), event.getY());
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            shapeManager.moveSelectedShape(event.getX(), event.getY());
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        shapeManager.deselectShape();
    }
}