package ru.somber.laba_7.gui.figure;

import javafx.scene.canvas.GraphicsContext;
import ru.somber.laba_7.util.Util;
import ru.somber.laba_7.util.Vector2F;

/**
 * Размер круга это его радуис.
 */
public class GUICircle extends AbstractFigure {

    public GUICircle() {
        super();
    }


    @Override
    public void move(double xOffset, double yOffset) {
        //для сдвига фигуры нужно проверить можно ли вообще двигать фигуру.
        //для этого находит крайние точки круга и находит смещение за границы канваса.

        Vector2F leftBottomPoint = new Vector2F((float) (getX() + xOffset - getSize()), (float) (getY() + yOffset - getSize()));
        Vector2F rightTopPoint = new Vector2F((float) (getX() + xOffset + getSize()), (float) (getY() + yOffset + getSize()));

        Vector2F leftBottomOffset = Util.checkOutOfBorderCanvas(leftBottomPoint);
        Vector2F rightTopOffset = Util.checkOutOfBorderCanvas(rightTopPoint);

        //проверяем возможные смещения за пределы канваса через возвращенные смещения относительно границ канваса.
        double resultBorderOffsetX = leftBottomOffset.getX() + rightTopOffset.getX();
        double resultBorderOffsetY = leftBottomOffset.getY() + rightTopOffset.getY();

        //само смещение фигуры с учетом выхода за границы канваса, если таковое есть.
        setX(getX() + xOffset + resultBorderOffsetX);
        setY(getY() + yOffset + resultBorderOffsetY);
    }

    @Override
    public void render(GraphicsContext gc, boolean isFill) {
        //нужно произвести смещение на радиус, т.к. x, y - кординаты центра круга,
        //а рисуется круг из левого верхнего угла.
        //Т.е. координаты центра нужно сместить в верхний левый угол для корректной отрисовки.
        double xOffset = getX() - getSize();
        double yOffset = getY() - getSize();

        if (isFill) {
            gc.setFill(getColor());
            gc.fillOval(xOffset, yOffset, getSize() * 2, getSize() * 2);
        } else {
            gc.setStroke(getColor());
            gc.strokeOval(xOffset, yOffset, getSize() * 2, getSize() * 2);
        }
    }

    @Override
    public boolean containsPoint(double x, double y) {
        //проверка принадлежности точки кругу происходит через вычисление расстояния от
        //центра круга до точки и сравнения с радиусом круга.

        double deltaX = this.getX() - x;
        double deltaY = this.getY() - y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        //size у круга - радиус
        return distance <= getSize();
    }

    @Override
    public String toString() {
        return "GUICircle{ " + super.toString() + "}";
    }

}
