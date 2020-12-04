package ru.somber.laba_7.util;

import ru.somber.laba_7.gui.GUICanvasManager;

public class Util {


    /**
     * Проверяет лежит ли точка на прямой, справа или слева от прямой. Если лежит, то возвращается true, иначе false.
     *
     * Прямая задается координатами точек: (vertexA), (vertexB).
     * Точка задается координатами: (point).
     *
     * Направление прямой задается вектором AB (т.е. от точки A к точке B).
     *
     * checkRight - если true, то проверяет лежит ли справа от прямой, если false - лежит ле слево от прямой.
     */
    public static boolean checkPointOfLine(Vector2F vertexA,
                                            Vector2F vertexB,
                                            Vector2F point,
                                            boolean checkRight) {

        float xDirection = vertexB.getX() - vertexA.getX();
        float yDirection = vertexB.getY() - vertexA.getY();

        float xNormal = yDirection;
        float yNormal = -xDirection;

        float c = -(vertexA.getX() * xNormal + vertexA.getY() * yNormal);

        if (checkRight) {
            return (point.getX() * xNormal + point.getY() * yNormal + c) >= 0;
        } else {
            return (point.getX() * xNormal + point.getY() * yNormal + c) <= 0;
        }
    }


    /**
     * Проверяет выходит ли переданная точка за пределы канаваса и
     * возвращает вектор со смщеениями, чтобы вернуть точку в канвас.
     * Если она выходит за пределы, то в вектор записываются такие минимальные смещения,
     * чтобы точка оказалась в канвасе (т.е. на границе).
     */
    public static Vector2F checkOutOfBorderCanvas(Vector2F point) {
        int leftBorder = 0;
        int rightBorder = GUICanvasManager.WIDTH_CANVAS - 1;

        int topBorder = 0;
        int bottomBorder = GUICanvasManager.HEIGHT_CANVAS - 1;

        Vector2F resultOffset = new Vector2F();

        if (point.getX() < leftBorder) {
            resultOffset.setX(-point.getX());
        }

        if (point.getX() > rightBorder) {
            resultOffset.setX(rightBorder - point.getX());
        }

        if (point.getY() < topBorder) {
            resultOffset.setY(-point.getY());
        }

        if (point.getY() > bottomBorder) {
            resultOffset.setY(bottomBorder - point.getY());
        }

        return resultOffset;
    }

}
