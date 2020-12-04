package ru.somber.laba_7.gui.figurefactory;

import ru.somber.laba_7.gui.figure.GUIQuad;
import ru.somber.laba_7.gui.figure.IFigure;

public class QuadFactory implements IFigureFactory {

    public IFigure createFigure() {
        return new GUIQuad();
    }

}
