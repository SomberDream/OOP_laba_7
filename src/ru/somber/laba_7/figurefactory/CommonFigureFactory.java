package ru.somber.laba_7.figurefactory;

import ru.somber.laba_7.figure.*;

public class CommonFigureFactory implements IFigureFactory {

    @Override
    public IFigure createFigure(String figureName) {

        if (Circle.getDescriptor().equals(figureName)) {
            return new Circle();
        } else if (Quad.getDescriptor().equals(figureName)) {
            return new Quad();
        } else if (Triangle.getDescriptor().equals(figureName)) {
            return new Triangle();
        } else if (ColumnRectangle.getDescriptor().equals(figureName)) {
            return new ColumnRectangle();
        } else if (RowRectangle.getDescriptor().equals(figureName)) {
            return new RowRectangle();
        } else if (GroupFigure.getDescriptor().equals(figureName)) {
            return new GroupFigure(this);
        }

        throw new RuntimeException("Фабрика фигур: неизвестное название фигуры: " + figureName);
    }
}
