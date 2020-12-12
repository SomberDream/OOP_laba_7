package ru.somber.laba_7.figure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.somber.laba_7.figurefactory.IFigureFactory;
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
    /** Фабрика фигур, с помощью которой будут создаваться загружаемые с помощью load() фигуры. */
    private IFigureFactory figureFactory;


    public GroupFigure() {
        this(null);
    }

    public GroupFigure(IFigureFactory figureFactory) {
        this.figureList = new LinkedList<>();
        this.figureFactory = figureFactory;
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


    /**
     * Добавляет переданную фигуру в группу.
     */
    public void addToGroup(IFigure figure) {
        if (! figureList.contains(figure)) {
            figureList.addLast(figure);
        }
    }

    /**
     * Дабавляет переданный список фигур в объект группы фигур.
     */
    public void addToGroup(IList<IFigure> figures) {
        if (figures.isEmpty()) {
            return;
        }

        IIterator<IFigure> iterator = figures.getIterator();

        do {
            IFigure figure = iterator.currentElement();
            addToGroup(figure);
        } while(iterator.next());
    }

    /**
     * Возвращает список фигур, которые хранятся в группе.
     */
    public IList<IFigure> getFigureList() {
        return figureList;
    }


    /**
     * Двигает внутренние фигуры на переданные смещения.
     */
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

    /**
     * Устанавливает переданный размер для внутренних фигур.
     */
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

    /**
     * Устанавливает переданный цвет для внутренних фигур.
     */
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

    /**
     * Производит отрисовку внутренних фигур для перданного графического констекста.
     */
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

    /**
     * Производит поиск переданной точка на попадание среди внутренних фигур.
     */
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

    @Override
    public String save() {
        String data = "{ " + getDescriptor() + " ";

        if (! figureList.isEmpty()) {
            IIterator<IFigure> iterator = figureList.getIterator();
            do {
                IFigure figure = iterator.currentElement();
                String figureData = figure.save();

                data += figureData + " ";
            } while (iterator.next());
        }

        data += "}";
        return data;
    }

    @Override
    public void load(String record) {

        IList<String> records = new LinkedList<>();

        int countOpenBrace = 0;
        int firstIndexOfRecord = -1;
        for (int i = record.indexOf("{", 1); i < record.length(); i++) {
            char character = record.charAt(i);

            if (character == '{') {
                countOpenBrace++;
                if (countOpenBrace == 1) {
                    firstIndexOfRecord = i;
                }
            } else if (character == '}') {
                countOpenBrace--;
                if (countOpenBrace == 0) {
                    String subRecord = record.substring(firstIndexOfRecord, i + 1);
                    records.addLast(subRecord);
                }
            }
        }

        if (! records.isEmpty()) {
            IIterator<String> iterator = records.getIterator();
            do {
                String subRecord = iterator.currentElement();

                int startIndex = subRecord.indexOf(" ");
                int endIndex = subRecord.indexOf(" ", startIndex + 1);
                String nameOfFigure = subRecord.substring(startIndex + 1, endIndex);

                IFigure figure = figureFactory.createFigure(nameOfFigure);
                figure.load(subRecord);

                figureList.addLast(figure);
            } while (iterator.next());
        }

    }


    public static String getDescriptor() {
        return "group_figure";
    }

}
