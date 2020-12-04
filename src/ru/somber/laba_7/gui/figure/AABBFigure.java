package ru.somber.laba_7.gui.figure;

import javafx.scene.canvas.GraphicsContext;
import ru.somber.laba_7.util.Util;
import ru.somber.laba_7.util.Vector2F;

/**
 * Класс для представления AABB фигуры.
 * В конструктор требуется передать 4 вектора с шаблонными вершинами фигурами.
 * На основе переданных вершин будут выводиться истинные вершины фигуры на основе ее размеров и позиции.
 */
public class AABBFigure extends AbstractFigure {
    //шаблонные вершины.
    private final Vector2F templateVertexA;
    private final Vector2F templateVertexB;
    private final Vector2F templateVertexC;
    private final Vector2F templateVertexD;


    public AABBFigure(Vector2F templateVertexA, Vector2F templateVertexB,
                      Vector2F templateVertexC, Vector2F templateVertexD) {

        super();

        this.templateVertexA = templateVertexA;
        this.templateVertexB = templateVertexB;
        this.templateVertexC = templateVertexC;
        this.templateVertexD = templateVertexD;
    }


    @Override
    public void move(double xOffset, double yOffset) {
        //пытаемся подвинуть фигуру.
        //для начала нужно проверить не выходит ли фигура за пределы холста.
        //для этого вычисляем смещенные координаты крайних вершин и проверяем

        Vector2F newA = getVertexA();
        Vector2F newC = getVertexC();

        newA.setX(newA.getX() + (float) xOffset);
        newA.setY(newA.getY() + (float) yOffset);

        newC.setX(newC.getX() + (float) xOffset);
        newC.setY(newC.getY() + (float) yOffset);

        Vector2F borderOffsetA = Util.checkOutOfBorderCanvas(newA);
        Vector2F borderOffsetC = Util.checkOutOfBorderCanvas(newC);

        //проверяем возможные смещения за пределы канваса через возвращенные смещения относительно границ канваса.
        double resultBorderOffsetX = borderOffsetA.getX() + borderOffsetC.getX();
        double resultBorderOffsetY = borderOffsetA.getY() + borderOffsetC.getY();

        //само смещение фигуры с учетом выхода за границы канваса, если таковое есть.
        setX(getX() + xOffset + resultBorderOffsetX);
        setY(getY() + yOffset + resultBorderOffsetY);
    }

    @Override
    public void render(GraphicsContext gc, boolean isFill) {
        Vector2F vertexA = getVertexA();
        Vector2F vertexB = getVertexB();
        Vector2F vertexC = getVertexC();
        Vector2F vertexD = getVertexD();

        //для отрисовки AABB достаточно собрать координаты вершин в два массива (координат X и координат Y)
        //и отправить на отрисовку как полигон.

        double[] xCoords = new double[]{
                vertexA.getX(),
                vertexB.getX(),
                vertexC.getX(),
                vertexD.getX()};

        double[] yCoords = new double[]{
                vertexA.getY(),
                vertexB.getY(),
                vertexC.getY(),
                vertexD.getY()};

        if (isFill) {
            gc.setFill(getColor());
            gc.fillPolygon(xCoords, yCoords, 4);
        } else {
            gc.setStroke(getColor());
            gc.strokePolygon(xCoords, yCoords, 4);
        }
    }

    @Override
    public boolean containsPoint(double x, double y) {
        //проверка принадлежности точки фигуре происходит через метод checkPointOfLine(),
        //который проверяет с какой стороны лежит точка относительно прямой с переданными координатами двух точек на ней.

        //вершины фигуры
        Vector2F vertexA = getVertexA();
        Vector2F vertexB = getVertexB();
        Vector2F vertexC = getVertexC();
        Vector2F vertexD = getVertexD();

        //проверяемая точка
        Vector2F point = new Vector2F((float) x, (float) y);

        //проверяем с каждой из прямых
        boolean checkLineAB = Util.checkPointOfLine(vertexA, vertexB, point, false);
        boolean checkLineBC = Util.checkPointOfLine(vertexB, vertexC, point, false);
        boolean checkLineCD = Util.checkPointOfLine(vertexC, vertexD, point, false);
        boolean checkLineDA = Util.checkPointOfLine(vertexD, vertexA, point, false);

        //если хотя бы одна проверка дала false, то точка лежит вне фигуры.
        //если все проверки дали true, то точка лежит внутри фигуры.
        return checkLineAB && checkLineBC && checkLineCD && checkLineDA;
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

    public Vector2F getVertexD() {
        return new Vector2F(templateVertexD.getX() * getSize() + (float) getX(), templateVertexD.getY() * getSize() + (float) getY());
    }


    @Override
    public String toString() {
        return "AABBFigure{ " + super.toString() + "}";
    }

}
