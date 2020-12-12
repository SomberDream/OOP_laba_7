package ru.somber.laba_7.figurefactory;

import ru.somber.laba_7.figure.IFigure;

public interface IFigureFactory {

    /**
     * Создает объекты фигуры по переданному названию фигуры.
     * Если передать неизвестное для фабрики название, то выпадает исключение времени выполнения.
     */
    IFigure createFigure(String figureName);

}
