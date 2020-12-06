package ru.somber.laba_7.figure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.somber.laba_7.serialize.ISerializable;

public interface IFigure extends ISerializable {

    /**
     * Возвращает координату X фигуры.
     */
    double getX();

    /**
     * Возвращает координату Y фигуры.
     */
    double getY();

    /**
     * Устанавливает координату X для фигуры.
     */
    void setX(double x);

    /**
     * Устанавливает координату Y для фигуры.
     */
    void setY(double y);

    /**
     * Сдвигает координаты фигуры на переданный значения.
     */
    void move(double xOffset, double yOffset);

    /**
     * Возвращает размер фигуры.
     */
    float getSize();

    /**
     * Устанавливает размер фигуры.
     */
    void setSize(float size);

    /**
     * Возвращает цвет, которым рисуется фигура.
     */
    Color getColor();

    /**
     * Устанавливает новый цвет фигуры.
     */
    void setColor(Color color);

    /**
     * Отрисовывет фигуру для переданного графического контекста.
     * В зависимости от переданного флага isFill
     * фигура должна рисоваться закрашенной или только ее граница.
     */
    void render(GraphicsContext gc, boolean isFill);

    /**
     * Проверяет входит ли переданная точка в область фигуры.
     */
    boolean containsPoint(double x, double y);

}
