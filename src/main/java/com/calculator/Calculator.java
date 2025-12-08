package com.calculator;

public class Calculator {
    private double firstNumber;
    private double secondNumber;
    private String operation;
    private boolean startNewNumber;

    public Calculator() {
        clear();
    }

    public void clear() {
        firstNumber = 0;
        secondNumber = 0;
        operation = "";
        startNewNumber = true;
    }

    public void appendNumber(String number) {
        if (startNewNumber) {
            firstNumber = Double.parseDouble(number);
            startNewNumber = false;
        } else {
            firstNumber = Double.parseDouble(firstNumber + number);
        }
    }

    public void setOperation(String op) {
        if (!operation.isEmpty()) {
            calculate();
        }
        secondNumber = firstNumber;
        operation = op;
        startNewNumber = true;
    }

    public double calculate() {
        if (operation.isEmpty()) {
            return firstNumber;
        }

        switch (operation) {
            case "+":
                firstNumber = secondNumber + firstNumber;
                break;
            case "-":
                firstNumber = secondNumber - firstNumber;
                break;
            case "*":
                firstNumber = secondNumber * firstNumber;
                break;
            case "/":
                if (firstNumber == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                firstNumber = secondNumber / firstNumber;
                break;
        }

        operation = "";
        startNewNumber = true;
        return firstNumber;
    }

    public double getResult() {
        return firstNumber;
    }

    public String getOperation() {
        return operation;
    }
}