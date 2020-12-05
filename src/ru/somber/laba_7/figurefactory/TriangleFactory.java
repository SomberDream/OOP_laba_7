package ru.somber.laba_7.figurefactory;

import ru.somber.laba_7.figure.Triangle;
import ru.somber.laba_7.figure.IFigure;

public class TriangleFactory implements IFigureFactory {

    public IFigure createFigure() {
        return new Triangle();
    }

}
