package ru.somber.laba_7.figurefactory;

import ru.somber.laba_7.figure.ColumnRectangle;
import ru.somber.laba_7.figure.IFigure;

public class ColumnRectangleFactory implements IFigureFactory {

    public IFigure createFigure() {
        return new ColumnRectangle();
    }

}
