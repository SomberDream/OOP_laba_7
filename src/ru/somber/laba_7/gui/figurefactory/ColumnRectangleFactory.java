package ru.somber.laba_7.gui.figurefactory;

import ru.somber.laba_7.gui.figure.GUIColumnRectangle;
import ru.somber.laba_7.gui.figure.IFigure;

public class ColumnRectangleFactory implements IFigureFactory {

    public IFigure createFigure() {
        return new GUIColumnRectangle(0, 0, 0);
    }

}
