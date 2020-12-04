package ru.somber.laba_7.gui.figurefactory;

import ru.somber.laba_7.gui.figure.GUIRowRectangle;
import ru.somber.laba_7.gui.figure.IFigure;

public class RowRectangleFactory implements IFigureFactory {

    public IFigure createFigure() {
        return new GUIRowRectangle(0, 0, 0);
    }

}
