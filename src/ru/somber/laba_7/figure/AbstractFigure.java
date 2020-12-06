package ru.somber.laba_7.figure;

import javafx.scene.paint.Color;

import java.util.StringTokenizer;

public abstract class AbstractFigure implements IFigure {

    /** Координаты центра фигуры. */
    private double x, y;
    /** Размер фигуры. */
    private float size;
    /** Цвет фигуры. */
    private Color color;


    public AbstractFigure() {
        this(0, 0, 0);
    }

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
    public String save() {
        return String.join(" ", "{",
                           getDescriptorForSave(),
                           Integer.toString((int) x),
                           Integer.toString((int) y),
                           Integer.toString((int) size),
                           Float.toString((float) color.getRed()),
                           Float.toString((float) color.getGreen()),
                           Float.toString((float) color.getBlue()),
                           "}");
    }

    @Override
    public void load(String record) {
        StringTokenizer tokenizer = new StringTokenizer(record, " ");
        tokenizer.nextToken();
        tokenizer.nextToken();

        int x = Integer.parseInt(tokenizer.nextToken());
        int y = Integer.parseInt(tokenizer.nextToken());
        int size = Integer.parseInt(tokenizer.nextToken());

        float r = Float.parseFloat(tokenizer.nextToken());
        float g = Float.parseFloat(tokenizer.nextToken());
        float b = Float.parseFloat(tokenizer.nextToken());

        this.x = x;
        this.y = y;
        this.size = size;
        this.color = Color.color(r, g, b);
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


    protected abstract String getDescriptorForSave();

}
