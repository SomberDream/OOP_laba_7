 package ru.somber.laba_7.serialize;

import ru.somber.laba_7.figure.IFigure;
import ru.somber.laba_7.figurefactory.IFigureFactory;
import ru.somber.laba_7.list.IIterator;
import ru.somber.laba_7.list.IList;
import ru.somber.laba_7.list.LinkedList;

import java.io.*;
import java.util.Scanner;

/**
 * Сериализатор фигур.
 * Может загружать/сохранять списки объектов по указанному адресу файла с данными фигур.
 * Для создания фигур при загрузке используется переданная фабрика фигур.
 */
public class FigureSerializer {

    private final String pathToFile;
    private final File file;
    private final IFigureFactory figureFactory;


    /**
     * @param pathToFile путь до файла с данными фигур.
     * @param figureFactory фабрика фигур, с помощью которой будут создаваться фигуры при их загрузке с файла.
     */
    public FigureSerializer(String pathToFile, IFigureFactory figureFactory) {
        this.pathToFile = pathToFile;
        this.file = new File(pathToFile);
        this.figureFactory = figureFactory;
    }


    /**
     * Загружает фигуры из файла с данными фигур и возвращает список загруженных фигур.
     * Фигуры создаются с помощью переданной при создании сериализатора фабрики фигур.
     */
    public IList<IFigure> load() throws IOException {
        IList<IFigure> loadedFigureList = new LinkedList<>();

        try (Scanner reader = new Scanner(new FileReader(file))) {
            while (reader.hasNextLine()) {
                String record = reader.nextLine();
                IFigure figure = loadFigureFromRecord(record, figureFactory);
                if (figure != null) {
                    loadedFigureList.addLast(figure);
                }
            }
        }

        return loadedFigureList;
    }

    /**
     * Сохраняет переданный список фигур в файл с данными фигур.
     */
    public void save(IList<IFigure> figureList) {
        StringBuilder resultStringBuilder = new StringBuilder();

        if (! figureList.isEmpty()) {
            IIterator<IFigure> iterator = figureList.getIterator();

            do {
                IFigure figure = iterator.currentElement();
                String figureData = figure.save();
                resultStringBuilder.append(figureData).append("\n\n");
            } while (iterator.next());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(resultStringBuilder.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "FigureSerializer{" +
                "pathToFile='" + pathToFile + '\'' +
                '}';
    }


    /**
     * Создает фигуру и заполняет ее данными из переданной записи с данными фигуры.
     */
    public static IFigure loadFigureFromRecord(String record, IFigureFactory figureFactory) {
        if (record.contains("{")) {
            int indexFirstSpace = record.indexOf(" ");
            int indexSecondSpace = record.indexOf(" ", indexFirstSpace + 1);
            String nameOfFigures = record.substring(indexFirstSpace + 1, indexSecondSpace);

            IFigure figure = figureFactory.createFigure(nameOfFigures);
            figure.load(record);

            return figure;
        }

        return null;
    }

}
