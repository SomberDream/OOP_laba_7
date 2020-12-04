package ru.somber.laba_7.gui.figure;

import javafx.scene.canvas.GraphicsContext;
import ru.somber.laba_7.util.Util;
import ru.somber.laba_7.util.Vector2F;

/**
 * Треугольник задается предустановленными вершинами. Их координаты можно увидеть в templateVertex*.
 * Размер треугольника - масштабирование этих предустановленных вершин.
 */
public class GUITriangle extends AbstractFigure {
    private static final Vector2F templateVertexA = new Vector2F(0, -1);
    private static final Vector2F templateVertexB = new Vector2F(1, 1);
    private static final Vector2F templateVertexC = new Vector2F(-1, 1);


    public GUITriangle(double x, double y, float size) {
        super(size, x, y);
    }

    @Override
    public void move(double xOffset, double yOffset) {
        //пытаемся подвинуть фигуру.
        //для начала нужно проверить не выходит ли фигура за пределы холста.
        //для этого вычисляем смещенные координаты крайних вершин и проверяем

        Vector2F newA = getVertexA();
        Vector2F newB = getVertexB();
        Vector2F newC = getVertexC();

        newA.setX(newA.getX() + (float) xOffset);
        newA.setY(newA.getY() + (float) yOffset);

        newB.setX(newB.getX() + (float) xOffset);
        newB.setY(newB.getY() + (float) yOffset);

        newC.setX(newC.getX() + (float) xOffset);
        newC.setY(newC.getY() + (float) yOffset);

        Vector2F borderOffsetA = Util.checkOutOfBorderCanvas(newA);
        Vector2F borderOffsetB = Util.checkOutOfBorderCanvas(newB);
        Vector2F borderOffsetC = Util.checkOutOfBorderCanvas(newC);

        //для Х достаточно вершин B и C (они наиболее удалены друг от друга по оси Х)
        double resultBorderOffsetX = borderOffsetB.getX() + borderOffsetC.getX();
        //для Y достаточно вершин A и B (или C, не важно) (они наиболее удалены друг от друга по оси У)
        double resultBorderOffsetY = borderOffsetA.getY() + borderOffsetB.getY();

        //само смещение фигуры с учетом выхода за границы канваса, если таковое есть.
        setX(getX() + xOffset + resultBorderOffsetX);
        setY(getY() + yOffset + resultBorderOffsetY);
    }

    @Override
    public void render(GraphicsContext gc, boolean isFill) {
        Vector2F vertexA = getVertexA();
        Vector2F vertexB = getVertexB();
        Vector2F vertexC = getVertexC();

        double[] xCoords = new double[]{
                vertexA.getX(),
                vertexB.getX(),
                vertexC.getX() };

        double[] yCoords = new double[]{
                vertexA.getY(),
                vertexB.getY(),
                vertexC.getY() };

        if (isFill) {
            gc.setFill(getColor());
            gc.fillPolygon(xCoords, yCoords, 3);
        } else {
            gc.setStroke(getColor());
            gc.strokePolygon(xCoords, yCoords, 3);
        }
    }

    @Override
    public boolean containsPoint(double x, double y) {
        Vector2F vertexA = getVertexA();
        Vector2F vertexB = getVertexB();
        Vector2F vertexC = getVertexC();
        Vector2F point = new Vector2F((float) x, (float) y);

        boolean checkLineAB = Util.checkPointOfLine(vertexA, vertexB, point, false);
        boolean checkLineBC = Util.checkPointOfLine(vertexB, vertexC, point, false);
        boolean checkLineCA = Util.checkPointOfLine(vertexC, vertexA, point, false);

        return checkLineAB && checkLineBC && checkLineCA;
    }


    public Vector2F getVertexA() {
        return new Vector2F(templateVertexA.getX() * getSize() + (float) getX(), templateVertexA.getY() * getSize() + (float) getY());
    }

    public Vector2F getVertexB() {
        return new Vector2F(templateVertexB.getX() * getSize() + (float) getX(), templateVertexB.getY() * getSize() + (float) getY());
    }

    public Vector2F getVertexC() {
        return new Vector2F(templateVertexC.getX() * getSize() + (float) getX(), templateVertexC.getY() * getSize() + (float) getY());
    }

    @Override
    public String toString() {
        return "GUITriangle{ " + super.toString() + "}";
    }

}
