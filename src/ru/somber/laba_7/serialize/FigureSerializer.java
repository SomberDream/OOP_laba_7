package ru.somber.laba_7.serialize;

import ru.somber.laba_7.figure.IFigure;
import ru.somber.laba_7.figurefactory.IFigureFactory;
import ru.somber.laba_7.list.IIterator;
import ru.somber.laba_7.list.IList;
import ru.somber.laba_7.list.LinkedList;

import java.io.*;
import java.util.Scanner;

public class FigureSerializer {

    private String pathToFile;
    private File file;
    private IFigureFactory figureFactory;


    public FigureSerializer(String pathToFile, IFigureFactory figureFactory) {
        this.pathToFile = pathToFile;
        this.file = new File(pathToFile);
        this.figureFactory = figureFactory;
    }


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
