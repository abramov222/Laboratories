package com.calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class CalculatorController {
    @FXML private Label display;
    @FXML private GridPane buttonGrid;
    @FXML private Pane rootPane;  // Добавим корневой контейнер

    private Calculator calculator;
    private boolean startNewNumber;

    @FXML
    public void initialize() {
        calculator = new Calculator();
        startNewNumber = true;

        // Назначаем обработчики для кнопок
        for (var node : buttonGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setOnAction(e -> handleButton(button.getText()));
            }
        }
    }


    @FXML
    private void setupKeyHandlers() {

        rootPane.setOnKeyPressed(this::handleKeyPress);


        rootPane.requestFocus();
    }

    private void handleButton(String text) {
        switch (text) {
            case "C":
                calculator.clear();
                display.setText("0");
                startNewNumber = true;
                break;
            case "=":
                try {
                    double result = calculator.calculate();
                    display.setText(formatNumber(result));
                } catch (ArithmeticException e) {
                    display.setText("Ошибка: " + e.getMessage());
                    calculator.clear();
                    startNewNumber = true;
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                calculator.setOperation(text);
                display.setText(formatNumber(calculator.getResult()) + " " + text);
                startNewNumber = true;
                break;
            case ".":
                if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
                break;
            default: // Цифры
                if (startNewNumber || display.getText().equals("0")) {
                    display.setText(text);
                    startNewNumber = false;
                } else {
                    display.setText(display.getText() + text);
                }
                calculator.appendNumber(display.getText());
        }
    }

    private void handleKeyPress(KeyEvent event) {
        System.out.println("Key pressed: " + event.getCode());

        if (event.getCode().isDigitKey()) {
            handleButton(event.getText());
        } else if (event.getCode() == KeyCode.PLUS || event.getText().equals("+")) {
            handleButton("+");
        } else if (event.getCode() == KeyCode.MINUS || event.getText().equals("-")) {
            handleButton("-");
        } else if (event.getCode() == KeyCode.MULTIPLY || event.getText().equals("*")) {
            handleButton("*");
        } else if (event.getCode() == KeyCode.DIVIDE || event.getText().equals("/")) {
            handleButton("/");
        } else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.EQUALS) {
            handleButton("=");
        } else if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.DELETE) {
            handleButton("C");
        } else if (event.getCode() == KeyCode.PERIOD || event.getCode() == KeyCode.DECIMAL) {
            handleButton(".");
        }


        rootPane.requestFocus();
    }

    private String formatNumber(double num) {
        if (num == (long) num) {
            return String.format("%d", (long) num);
        } else {
            return String.format("%s", num);
        }
    }
}