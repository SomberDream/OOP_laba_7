package ru.somber.laba_7.figurefactory;

import ru.somber.laba_7.figure.RowRectangle;
import ru.somber.laba_7.figure.IFigure;

public class RowRectangleFactory implements IFigureFactory {

    public IFigure createFigure() {
        return new RowRectangle();
    }

}
