package ru.somber.laba_7.figurefactory;

import ru.somber.laba_7.figure.Circle;
import ru.somber.laba_7.figure.IFigure;

public class CircleFactory implements IFigureFactory {

    public IFigure createFigure() {
        return new Circle();
    }

}
