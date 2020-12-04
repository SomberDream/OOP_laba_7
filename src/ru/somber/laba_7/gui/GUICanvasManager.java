package ru.somber.laba_7.gui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ru.somber.laba_7.gui.figure.GUIGroupFigure;
import ru.somber.laba_7.gui.figure.IFigure;
import ru.somber.laba_7.gui.figurefactory.IFigureFactory;
import ru.somber.laba_7.util.Vector2F;
import ru.somber.laba_7.list.IIterator;
import ru.somber.laba_7.list.IList;
import ru.somber.laba_7.list.LinkedList;

public class GUICanvasManager {
    //ширина и высота канваса.
    public static final int WIDTH_CANVAS = 800;
    public static final int HEIGHT_CANVAS = 600;

    //вектора с координатами сдвига в заданном направлении.
    private static final Vector2F UP_OFFSET = new Vector2F(0, -5);
    private static final Vector2F DOWN_OFFSET = new Vector2F(0, 5);
    private static final Vector2F RIGHT_OFFSET = new Vector2F(5, 0);
    private static final Vector2F LEFT_OFFSET = new Vector2F(-5, 0);

    /** Холст, но котором будут рисоваться объекты. */
    private final Canvas canvas;
    /** Лист объектов, которые рисуются в виде границы. */
    private final IList<IFigure> strokeFigureList;
    /** Лист объектов, которые рисуются заполненными. */
    private final IList<IFigure> fillFigureList;
    /** Список фигур типа сгруппированных фигур. */
    private final IList<IFigure> groupFigureList;

    /** Флаг нажате ли клавиша CTRL. */
    private boolean isKeyCTRLPressed;

    /** Фабрика фигур. С помощью этой фабрики будут создаваться фигуры. */
    private IFigureFactory figureFactory;
    /** Размер фигур. */
    private int figureSize = 50;
    /** Цвет фигур. */
    private Color figureColor;


    public GUICanvasManager(Scene scene, Canvas canvas) {
        this.canvas = canvas;

        this.strokeFigureList = new LinkedList<>();
        this.fillFigureList = new LinkedList<>();
        this.groupFigureList = new LinkedList<>();

        //события клавиш обрабатывать для сцены, т.к. канвас не реагирует на события.
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new KeyCTRLPressHandler<>());
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new KeyCTRLRealiseHandler<>());
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new KeyDeleteRealiseHandler<>());
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new KeysMovePressHandler<>());

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new MousePressEvent<>());
    }


    /**
     * Возвращает фигуру по координатам точки, если точка является частью фигуры.
     */
    public IFigure getFigure(double x, double y) {
        IFigure figure = getStrokeFigure(x, y);
        if (figure == null) {
            figure = getFillFigure(x, y);
        }

        return figure;
    }

    /**
     * Отрисовывает все фигуры.
     */
    public void renderFigures() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        renderStrokeFigure();
        renderFillFigure();
    }

    public void setFigureFactory(IFigureFactory figureFactory) {
        this.figureFactory = figureFactory;
    }

    public void setFigureSize(int figureSize) {
        this.figureSize = figureSize;

        applyFigureProperty();
    }

    public void setFigureColor(Color figureColor) {
        this.figureColor = figureColor;

        applyFigureProperty();
    }

    public void moveFigures(Vector2F offsets) {
        if (fillFigureList.isEmpty()) {
            return;
        }

        IIterator<IFigure> iterator = fillFigureList.getIterator();
        do {
            IFigure figure = iterator.currentElement();
            figure.move(offsets.getX(), offsets.getY());
        } while (iterator.next());

        renderFigures();
    }

    public void groupFillFigures() {
        if (fillFigureList.isEmpty()) {
            return;
        }

        GUIGroupFigure groupFigure = new GUIGroupFigure();
        groupFigure.addToGroup(fillFigureList);

        fillFigureList.clear();
        fillFigureList.addLast(groupFigure);

        groupFigureList.addLast(groupFigure);
    }

    public void ungroupFillFigures() {
        if (fillFigureList.isEmpty() || groupFigureList.isEmpty()) {
            return;
        }

        IList<IFigure> tempList = new LinkedList<>();

        IIterator<IFigure> iterator = fillFigureList.getIterator();
        do {
            IFigure figure = iterator.currentElement();

            if (groupFigureList.contains(figure)) {
                IList<IFigure> innerFigureList = ((GUIGroupFigure) figure).getFigureList();

                if (innerFigureList.isEmpty()) {
                    continue;
                }

                IIterator<IFigure> iteratorInnerFigures = innerFigureList.getIterator();

                do {
                    IFigure innerFigure = iteratorInnerFigures.currentElement();

                    tempList.addLast(innerFigure);
                } while(iteratorInnerFigures.next());

                fillFigureList.remove(figure);
                groupFigureList.remove(figure);
            }
        } while (iterator.next());

        iterator = tempList.getIterator();
        do {
            IFigure figure = iterator.currentElement();

            fillFigureList.addLast(figure);
        } while (iterator.next());
    }


    /**
     * Добавляет фигуру к списку фигур, которые рисуются в виде границы.
     */
    private void addStrokeFigure(IFigure figure) {
        strokeFigureList.addLast(figure);
    }

    /**
     * Добавляет фигуру к списку фигур, которые рисуются заполненными.
     */
    private void addFillFigure(IFigure figure) {
        fillFigureList.addLast(figure);
    }

    /**
     * Удаляет фигуру из списка фигур, которые рисуются в виде границы.
     */
    private void removeStrokeFigure(IFigure figure) {
        strokeFigureList.remove(figure);
    }

    /**
     * Удаляет фигуру из списка фигур, которые рисуются заполненными.
     */
    private void removeFillFigure(IFigure figure) {
        fillFigureList.remove(figure);
    }

    /**
     * Ищет фигуру среди списка фигур, рисующихся в виде границы.
     * В случае успеха возвращается true, иначе false.
     */
    private boolean containsStrokeFigure(IFigure figure) {
        return strokeFigureList.contains(figure);
    }

    /**
     * Ищет фигуру среди списка фигур, рисующихся заполненными.
     * В случае успеха возвращается true, иначе false.
     */
    private boolean containsFillFigure(IFigure figure) {
        return fillFigureList.contains(figure);
    }

    /**
     * Ищет фигуру среди фигур-границ по координатам точки,
     * которая может принадлежать этой фигуре.
     * Т.е. если точка попала в область фигуры, то эта фигура возвращается.
     */
    private IFigure getStrokeFigure(double x, double y) {
        if (strokeFigureList.isEmpty()) {
            return null;
        }

        IIterator<IFigure> iterator = strokeFigureList.getIterator();
        do {
            IFigure figure = iterator.currentElement();
            if (figure.containsPoint(x, y)) {
                return figure;
            }
        } while (iterator.next());

        return null;
    }

    /**
     * Ищет фигуру среди заполненных фигур по координатам точки,
     * которая может принадлежать этой фигуре.
     * Т.е. если точка попала в область фигуры, то эта фигура возвращается.
     */
    private IFigure getFillFigure(double x, double y) {
        if (fillFigureList.isEmpty()) {
            return null;
        }

        IIterator<IFigure> iterator = fillFigureList.getIterator();
        do {
            IFigure figure = iterator.currentElement();
            if (figure.containsPoint(x, y)) {
                return figure;
            }
        } while (iterator.next());

        return null;
    }

    /**
     * Рисует все фигуры, которые рисуются в виде границ.
     */
    private void renderStrokeFigure() {
        if (strokeFigureList.isEmpty()) {
            return;
        }

        IIterator<IFigure> iterator = strokeFigureList.getIterator();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        do {
            IFigure figure = iterator.currentElement();
            figure.render(gc, false);
        } while (iterator.next());
    }

    /**
     * Рисует все заполненные фигуры.
     */
    private void renderFillFigure() {
        if (fillFigureList.isEmpty()) {
            return;
        }

        IIterator<IFigure> iterator = fillFigureList.getIterator();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        do {
            IFigure figure = iterator.currentElement();
            figure.render(gc, true);
        } while (iterator.next());
    }

    /**
     * Удаляет фигуру из списка заполненных фигур и добавляет в список фигур-границ.
     */
    private void setFigureStroke(IFigure figure) {
        boolean containsStrokeList = strokeFigureList.contains(figure);

        fillFigureList.remove(figure);
        if (! containsStrokeList) {
            addStrokeFigure(figure);
        }
    }

    /**
     * Удаляет фигуру из списка фигур-границ и добавляет в список заполненных фигур.
     */
    private void setFigureFill(IFigure figure) {
        boolean containsFillList = fillFigureList.contains(figure);

        strokeFigureList.remove(figure);
        if (! containsFillList) {
            addFillFigure(figure);
        }
    }

    /**
     * Перемещает все фигуры из списка заполненных фигур в список фигур-границ.
     */
    private void allFillFigureToStrokeFigure() {
        if (fillFigureList.isEmpty()) {
            return;
        }

        IIterator<IFigure> iterator = fillFigureList.getIterator();
        do {
            IFigure figure = iterator.currentElement();
            addStrokeFigure(figure);
        } while (iterator.next());

        fillFigureList.clear();
    }

    /**
     * Применяет свойства к выделенным фигурам.
     * Под свойствами подразумевается цвет и размер.
     * После применения свойств перерисовывает кадр.
     */
    private void applyFigureProperty() {
        if (fillFigureList.isEmpty()) {
            return;
        }

        IIterator<IFigure> iterator = fillFigureList.getIterator();
        do {
            IFigure figure = iterator.currentElement();
            figure.setSize(figureSize);
            figure.setColor(figureColor);
        } while (iterator.next());

        renderFigures();
    }


    /**
     * Класс для обработки события нажатия на CTRL.
     */
    private class KeyCTRLPressHandler<T extends KeyEvent> implements EventHandler<T> {
        @Override
        public void handle(T event) {
            if (event.getCode() == KeyCode.CONTROL) {
                isKeyCTRLPressed = true;
            }
            renderFigures();
        }
    }

    /**
     * Класс для обработки события отжатия CTRL.
     */
    private class KeyCTRLRealiseHandler<T extends KeyEvent> implements EventHandler<T> {
        @Override
        public void handle(T event) {
            if (event.getCode() == KeyCode.CONTROL) {
                isKeyCTRLPressed = false;
            }
            renderFigures();
        }
    }

    /**
     * Класс для обработки события отжатия Delete.
     */
    private class KeyDeleteRealiseHandler<T extends KeyEvent> implements EventHandler<T> {
        @Override
        public void handle(T event) {
            if (event.getCode() == KeyCode.DELETE) {
                if (fillFigureList.isEmpty()) {
                    return;
                }

                IIterator<IFigure> iterator = fillFigureList.getIterator();
                do {
                    IFigure figure = iterator.currentElement();
                    groupFigureList.remove(figure);
                } while(iterator.next());

                fillFigureList.clear();
                renderFigures();
            }
        }
    }

    /**
     * Класс для обработки события нажатия на левую клавишу мыши.
     */
    private class MousePressEvent<T extends MouseEvent> implements EventHandler<T> {
        @Override
        public void handle(T event) {
            MouseButton pressButton = event.getButton();
            if (pressButton == MouseButton.PRIMARY) {
                double x = event.getX();
                double y = event.getY();

                IFigure figure = getFigure(x, y);

                if (! isKeyCTRLPressed) {
                    allFillFigureToStrokeFigure();
                }
                if (figure == null) {
                    figure = figureFactory.createFigure();

                    figure.setSize(figureSize);
                    figure.setColor(figureColor);
                    figure.move(x, y);

                    allFillFigureToStrokeFigure();
                    addFillFigure(figure);
                } else {
                    setFigureFill(figure);
                }
            }

            renderFigures();
        }

    }

    /**
     * Класс для обработки событий нажатия на клавиши сдвига фигур.
     */
    private class KeysMovePressHandler<T extends KeyEvent> implements EventHandler<T> {
        @Override
        public void handle(T event) {
            if (event.getCode() == KeyCode.W) {
                moveFigures(UP_OFFSET);
            }

            if (event.getCode() == KeyCode.S) {
                moveFigures(DOWN_OFFSET);
            }

            if (event.getCode() == KeyCode.D) {
                moveFigures(RIGHT_OFFSET);
            }

            if (event.getCode() == KeyCode.A) {
                moveFigures(LEFT_OFFSET);
            }
        }

    }
}
