package ru.somber.laba_7.figurefactory;

import ru.somber.laba_7.figure.Quad;
import ru.somber.laba_7.figure.IFigure;

public class QuadFactory implements IFigureFactory {

    public IFigure createFigure() {
        return new Quad();
    }

}
