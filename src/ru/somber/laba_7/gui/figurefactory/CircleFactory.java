package ru.somber.laba_7.gui.figurefactory;

import ru.somber.laba_7.gui.figure.GUICircle;
import ru.somber.laba_7.gui.figure.IFigure;

public class CircleFactory implements IFigureFactory {

    public IFigure createFigure() {
        return new GUICircle();
    }

}
