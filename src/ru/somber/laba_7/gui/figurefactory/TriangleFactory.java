package ru.somber.laba_7.gui.figurefactory;

import ru.somber.laba_7.gui.figure.GUITriangle;
import ru.somber.laba_7.gui.figure.IFigure;

public class TriangleFactory implements IFigureFactory {

    public IFigure createFigure() {
        return new GUITriangle();
    }

}
