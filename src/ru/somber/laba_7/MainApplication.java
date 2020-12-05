package ru.somber.laba_7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.somber.laba_7.figurefactory.*;

public class MainApplication extends Application {

    private Scene rootScene;
    private CanvasManager canvasManager;


    public MainApplication() {
        super();
    }


    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        prepareMainStage(primaryStage);

        primaryStage.setTitle("Дорофеев Никита ПРО-223 лабораторная №7");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }


    private void prepareMainStage(Stage mainStage) {
        HBox rootLayout = new HBox();
        rootScene = new Scene(rootLayout);
        mainStage.setScene(rootScene);
        mainStage.setResizable(false);


        Pane canvasLayout = createCanvasLayout();
        Pane controlLayout = createControlLayout();


        rootLayout.getChildren().addAll(canvasLayout,  controlLayout);
    }

    private Pane createCanvasLayout() {
        VBox layout = new VBox();

        Canvas canvas = new Canvas(CanvasManager.WIDTH_CANVAS, CanvasManager.HEIGHT_CANVAS);

        layout.getChildren().add(canvas);
        VBox.setVgrow(canvas, Priority.ALWAYS);

        canvasManager = new CanvasManager(rootScene, canvas);

        return layout;
    }

    private Pane createControlLayout() {
        VBox layout = new VBox();

        VBox chooseFigureLayout = new VBox();
        VBox controlFigureLayout = new VBox();
        VBox controlGroupFigureLayout = new VBox();

        prepareChooseFigureLayout(chooseFigureLayout);
        prepareControlFigureLayout(controlFigureLayout);
        prepareControlGroupFigureLayout(controlGroupFigureLayout);

        TitledPane chooseFigurePane = new TitledPane("Choose figure type", chooseFigureLayout);
        TitledPane controlFigurePane = new TitledPane("Change figure property", controlFigureLayout);
        TitledPane controlGroupFigurePane = new TitledPane("Group/Ungroup figure", controlGroupFigureLayout);

        layout.getChildren().addAll(chooseFigurePane, controlFigurePane, controlGroupFigurePane);
        return layout;
    }

    private void prepareChooseFigureLayout(VBox layout) {
        RadioButton buttonCircle = new RadioButton("Circle");
        RadioButton buttonTriangle = new RadioButton("Triangle");
        RadioButton buttonQuad = new RadioButton("Quad");
        RadioButton buttonRowRectangle = new RadioButton("Row Rectangle");
        RadioButton buttonColumnRectangle = new RadioButton("Column Rectangle");

        buttonCircle.setOnAction((event) -> {
            canvasManager.setFigureFactory(new CircleFactory());
        });

        buttonTriangle.setOnAction((event) -> {
            canvasManager.setFigureFactory(new TriangleFactory());
        });

        buttonQuad.setOnAction((event) -> {
            canvasManager.setFigureFactory(new QuadFactory());
        });

        buttonRowRectangle.setOnAction((event) -> {
            canvasManager.setFigureFactory(new RowRectangleFactory());
        });

        buttonColumnRectangle.setOnAction((event) -> {
            canvasManager.setFigureFactory(new ColumnRectangleFactory());
        });

        ToggleGroup toggleGroup = new ToggleGroup();

        buttonCircle.setToggleGroup(toggleGroup);
        buttonTriangle.setToggleGroup(toggleGroup);
        buttonQuad.setToggleGroup(toggleGroup);
        buttonRowRectangle.setToggleGroup(toggleGroup);
        buttonColumnRectangle.setToggleGroup(toggleGroup);


        buttonCircle.setSelected(true);
        canvasManager.setFigureFactory(new CircleFactory());
        layout.getChildren().addAll(buttonCircle, buttonTriangle, buttonQuad, buttonRowRectangle, buttonColumnRectangle);
    }

    private void prepareControlFigureLayout(VBox layout) {
        canvasManager.setFigureSize(45);

        Slider figureSizeSlider = new Slider(10, 80, 45);
        figureSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            canvasManager.setFigureSize((int) newValue.doubleValue());
        });
        figureSizeSlider.setShowTickMarks(true);
        figureSizeSlider.setShowTickLabels(true);

        canvasManager.setFigureColor(Color.color(0.5, 0.5, 0.5));

        Slider figureRedSlider = new Slider(0, 1, 0.5);
        Slider figureGreenSlider = new Slider(0, 1, 0.5);
        Slider figureBlueSlider = new Slider(0, 1, 0.5);

        figureRedSlider.setShowTickMarks(true);
        figureRedSlider.setShowTickLabels(true);
        figureGreenSlider.setShowTickMarks(true);
        figureGreenSlider.setShowTickLabels(true);
        figureBlueSlider.setShowTickMarks(true);
        figureBlueSlider.setShowTickLabels(true);

        figureRedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            canvasManager.setFigureColor(Color.color(newValue.doubleValue(), figureGreenSlider.getValue(), figureBlueSlider.getValue()));
        });
        figureGreenSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            canvasManager.setFigureColor(Color.color(figureRedSlider.getValue(), newValue.doubleValue(), figureBlueSlider.getValue()));
        });
        figureBlueSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            canvasManager.setFigureColor(Color.color(figureRedSlider.getValue(), figureGreenSlider.getValue(), newValue.doubleValue()));
        });


        Label figureSizeLabel = new Label("Figure size:");
        Label figureColorLabel = new Label("Figure color:");
        Label figureRedLabel = new Label("Color red component:");
        Label figureGreenLabel = new Label("Color green component:");
        Label figureBlueLabel = new Label("Color blue component:");

        layout.getChildren().addAll(figureSizeLabel, figureSizeSlider,
                                    figureColorLabel,
                                    figureRedLabel, figureRedSlider,
                                    figureGreenLabel, figureGreenSlider,
                                    figureBlueLabel, figureBlueSlider);
    }

    private void prepareControlGroupFigureLayout(VBox layout) {
        Button createGroupButton = new Button("Create group");
        Button deleteGroupButton = new Button("Delete group");

        createGroupButton.setOnAction((event) -> {
            canvasManager.groupFillFigures();
        });

        deleteGroupButton.setOnAction((event) -> {
            canvasManager.ungroupFillFigures();
        });

        layout.getChildren().addAll(createGroupButton, deleteGroupButton);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
