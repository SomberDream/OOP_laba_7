package ru.somber.laba_7.figure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.somber.laba_7.list.IIterator;
import ru.somber.laba_7.list.IList;
import ru.somber.laba_7.list.LinkedList;

public class GroupFigure implements IFigure {

    /** Относительные координаты группы фигур. */
    private double x, y;
    /**
     * Размер фигур в группе.
     * Может не отражать размер всех фигур в группе ввиду того,
     * что размер фигур изменяется только при явном изменении.
     */
    private float size;
    /**
     * Цвет фигур в группе.
     * Может не отражать цвет всех фигур в группе ввиду того,
     * что цвет фигур изменяется только при явном изменении.
     */
    private Color color;
    /** Список фигур в группе. */
    private IList<IFigure> figureList;


    public GroupFigure() {
        figureList = new LinkedList<>();
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
        double deltaX = x - this.x;
        moveInnerFigures(deltaX, 0);

        this.x = x;
    }

    @Override
    public void setY(double y) {
        double deltaY = y - this.y;
        moveInnerFigures(0, deltaY);

        this.y = y;
    }

    @Override
    public void move(double xOffset, double yOffset) {
        moveInnerFigures(xOffset, yOffset);

        x += xOffset;
        y += yOffset;
    }

    @Override
    public float getSize() {
        return size;
    }

    @Override
    public void setSize(float size) {
        setSizeInnerFigures(size);
        this.size = size;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        setColorInnerFigures(color);
        this.color = color;
    }

    @Override
    public void render(GraphicsContext gc, boolean isFill) {
        renderInnerFigures(gc, isFill);
    }

    @Override
    public boolean containsPoint(double x, double y) {
        return containsPointInInnerFigures(x, y);
    }


    public void addToGroup(IFigure figure) {
        if (! figureList.contains(figure)) {
            figureList.addLast(figure);
        }
    }

    public void addToGroup(IList<IFigure> figures) {
        if (figures.isEmpty()) {
            return;
        }

        IIterator<IFigure> iterator = figures.getIterator();

        do {
            IFigure figure = iterator.currentElement();
            figureList.addLast(figure);
        } while(iterator.next());
    }

    public IList<IFigure> getFigureList() {
        return figureList;
    }


    private void moveInnerFigures(double xOffset, double yOffset) {
        if (figureList.isEmpty()) {
            return;
        }

        IIterator<IFigure> iterator = figureList.getIterator();

        do {
            IFigure figure = iterator.currentElement();
            figure.move(xOffset, yOffset);
        } while(iterator.next());
    }

    private void setSizeInnerFigures(float size) {
        if (figureList.isEmpty()) {
            return;
        }

        IIterator<IFigure> iterator = figureList.getIterator();

        do {
            IFigure figure = iterator.currentElement();
            figure.setSize(size);
        } while(iterator.next());
    }

    private void setColorInnerFigures(Color color) {
        if (figureList.isEmpty()) {
            return;
        }

        IIterator<IFigure> iterator = figureList.getIterator();

        do {
            IFigure figure = iterator.currentElement();
            figure.setColor(color);
        } while(iterator.next());
    }

    private void renderInnerFigures(GraphicsContext gc, boolean isFill) {
        if (figureList.isEmpty()) {
            return;
        }

        IIterator<IFigure> iterator = figureList.getIterator();

        do {
            IFigure figure = iterator.currentElement();
            figure.render(gc, isFill);
        } while(iterator.next());
    }

    private boolean containsPointInInnerFigures(double x, double y) {
        if (figureList.isEmpty()) {
            return false;
        }

        IIterator<IFigure> iterator = figureList.getIterator();

        do {
            IFigure figure = iterator.currentElement();
            boolean flag = figure.containsPoint(x, y);

            if (flag) {
                return true;
            }
        } while(iterator.next());

        return false;
    }

}
