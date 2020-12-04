package ru.somber.laba_7.gui.figure;

import javafx.scene.paint.Color;

public abstract class AbstractFigure implements IFigure {

    /** Координаты центра фигуры. */
    private double x, y;
    /** Размер фигуры. */
    private float size;
    /** Цвет фигуры. */
    private Color color;


    public AbstractFigure(float size, double x, double y) {
        this.size = size;
        this.x = x;
        this.y = y;
    }


    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void move(double xOffset, double yOffset) {
        x += xOffset;
        y += yOffset;
    }

    @Override
    public float getSize() {
        return size;
    }

    @Override
    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "AbstractFigure{" +
                "x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", color=" + color +
                '}';
    }

}
