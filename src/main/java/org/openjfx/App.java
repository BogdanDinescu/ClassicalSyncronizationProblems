package org.openjfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.openjfx.philosophersproblem.PhilosophersLogic;
import org.openjfx.philosophersproblem.PhilosophersPane;
import org.openjfx.producersandconsumersproblem.ProducerConsumerLogic;
import org.openjfx.producersandconsumersproblem.ProducerConsumerPane;
import org.openjfx.readersandwritersproblem.ReadersWritersLogic;
import org.openjfx.readersandwritersproblem.ReadersWritersPane;

public class App extends Application {
    private PhilosophersLogic philosophersLogic;
    private PhilosophersPane philosophersPane;
    private ProducerConsumerPane producerConsumerPane;
    private ProducerConsumerLogic producerConsumerLogic;
    private ReadersWritersPane readersWritersPane;
    private ReadersWritersLogic readersWritersLogic;

    private BorderPane stageRootNode;

    private static int numOfPhilosophers = 5;
    private static int bufferSize = 4;
    private static int numOfReaders = 3;
    private static int numOfWriters = 3;

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final Background GENERIC_BACKGROUND = new Background(new BackgroundFill(Color.web("#B4B4B4"), CornerRadii.EMPTY, Insets.EMPTY));
    private static final String DEFAULT_TEXT_FILL_COLOR = "-fx-text-fill: rgb(220, 220, 220);";
    private static final String BUTTON_STATIC_STYLE = "-fx-background-radius: 0;" +
            "-fx-background-color: rgb(55, 55, 56);" +
            "-fx-text-fill: rgb(220, 220, 220);";
    private static final String BUTTON_HOVER_STYLE = "-fx-background-radius: 0;" +
            "-fx-background-color: rgb(85, 85, 85);" +
            "-fx-text-fill: rgb(220, 220, 220);";
    private static final String BUTTON_CLICKED_STYLE = "-fx-background-radius: 0;" +
            "-fx-background-color: rgb(45, 45, 45);" +
            "-fx-text-fill: rgb(220, 220, 220);";
    private static final String HORIZONTAL_BOX_DEFAULT_BACKGROUND_COLOR = "-fx-background-color: rgb(55, 55, 55);";

    @Override
    public void start(Stage stage) {
        philosophersPane = new PhilosophersPane(numOfPhilosophers, WINDOW_WIDTH, WINDOW_HEIGHT);
        philosophersPane.setBackground(GENERIC_BACKGROUND);
        philosophersLogic = new PhilosophersLogic(numOfPhilosophers, philosophersPane);

        producerConsumerPane = new ProducerConsumerPane(bufferSize, WINDOW_WIDTH, WINDOW_HEIGHT);
        producerConsumerPane.setBackground(GENERIC_BACKGROUND);
        producerConsumerLogic = new ProducerConsumerLogic(bufferSize, producerConsumerPane);

        readersWritersPane = new ReadersWritersPane(numOfReaders, numOfWriters, WINDOW_WIDTH, WINDOW_HEIGHT);
        readersWritersPane.setBackground(GENERIC_BACKGROUND);
        readersWritersLogic = new ReadersWritersLogic(numOfReaders, numOfWriters, readersWritersPane);

        HBox buttonsBox = getButtonsForSynchronizationProblems();
        HBox settingsBox = getSettingsForSynchronizationProblems();
        VBox headerBox = new VBox();
        headerBox.getChildren().addAll(buttonsBox, settingsBox);

        Pane emptyPane = new Pane();
        emptyPane.setBackground(GENERIC_BACKGROUND);
        stageRootNode = new BorderPane();
        stageRootNode.setTop(headerBox);
        stageRootNode.setCenter(emptyPane);

        Scene scene = new Scene(stageRootNode, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("CSP");
        stage.setResizable(false);
        stage.show();
    }

    private HBox getButtonsForSynchronizationProblems() {
        Button startDeadlockExecButton = withGivenLabelCreateButtonAfterSetStyleAndAction("Philosophers-DeadLock");

        Button startCorrectExecutionButton = withGivenLabelCreateButtonAfterSetStyleAndAction("Philosophers-Sync");

        Button startProducerConsumerButton = withGivenLabelCreateButtonAfterSetStyleAndAction("Producers-Consumers");

        Button startReadersWritersButton = withGivenLabelCreateButtonAfterSetStyleAndAction("Readers-Writers");

        Button stopExecButton = withGivenLabelCreateButtonAfterSetStyleAndAction("Finish");

        HBox horizontalBox = new HBox();
        horizontalBox.setStyle(HORIZONTAL_BOX_DEFAULT_BACKGROUND_COLOR);
        horizontalBox.setFillHeight(true);
        horizontalBox.getChildren()
                .addAll(startDeadlockExecButton,
                        startCorrectExecutionButton,
                        startProducerConsumerButton,
                        startReadersWritersButton,
                        stopExecButton);
        return horizontalBox;
    }

    private HBox getSettingsForSynchronizationProblems() {
        VBox philosopherBox = new VBox();
        VBox producersConsumersBox = new VBox();
        VBox readersWritersBox = new VBox();

        Label philosopherLabel = new Label("Number of philosophers");
        Label producersConsumersLabel = new Label("Buffer size");
        Label readersWritersLabel = new Label("Number of people");
        philosopherLabel.setStyle(DEFAULT_TEXT_FILL_COLOR);
        producersConsumersLabel.setStyle(DEFAULT_TEXT_FILL_COLOR);
        readersWritersLabel.setStyle(DEFAULT_TEXT_FILL_COLOR);

        Slider philosopherSlider = createPhilosopherSlider();
        Slider producersConsumersSlider = createProducersConsumersSlider();
        Slider readersWritersSlider = createReadersWritersSlider();

        philosopherBox.getChildren().addAll(philosopherLabel, philosopherSlider);
        producersConsumersBox.getChildren().addAll(producersConsumersLabel, producersConsumersSlider);
        readersWritersBox.getChildren().addAll(readersWritersLabel, readersWritersSlider);

        HBox horizontalBox = new HBox();
        horizontalBox.setStyle(HORIZONTAL_BOX_DEFAULT_BACKGROUND_COLOR);
        horizontalBox.setFillHeight(true);
        horizontalBox.getChildren().addAll(philosopherBox,
                producersConsumersBox,
                readersWritersBox);

        return horizontalBox;
    }

    private Slider createPhilosopherSlider() {
        Slider philosopherSlider = new Slider(4, 10, 1);//min,max,thick amount
        setSliderProps(philosopherSlider);
        philosopherSlider.setValue(numOfPhilosophers);

        philosopherSlider.valueProperty()
                .addListener((obs, oldVal, newVal) -> numOfPhilosophers = newVal.intValue());

        return philosopherSlider;
    }

    private Slider createProducersConsumersSlider() {
        Slider producersConsumersSlider = new Slider(4, 6, 1);
        setSliderProps(producersConsumersSlider);
        producersConsumersSlider.setValue(bufferSize);

        producersConsumersSlider.valueProperty()
                .addListener((obs, oldVal, newVal) -> bufferSize = newVal.intValue());

        return producersConsumersSlider;
    }

    private Slider createReadersWritersSlider() {
        Slider readersWritersSlider = new Slider(3, 6, 1);
        setSliderProps(readersWritersSlider);
        readersWritersSlider.setValue(numOfWriters);

        readersWritersSlider.valueProperty().addListener((obs, oldVal, newVal) ->
        {
            numOfReaders = newVal.intValue();
            numOfWriters = newVal.intValue();
        });

        return readersWritersSlider;
    }

    private void setSliderProps(Slider slider) {
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.setMinorTickCount(0);
        slider.setMajorTickUnit(1);
        slider.setSnapToTicks(true);
    }

    private Button withGivenLabelCreateButtonAfterSetStyleAndAction(String label) {
        Button newButton = new Button(label);

        newButton.setPrefHeight(38);

        newButton.setStyle(BUTTON_STATIC_STYLE);
        newButton.setOnMouseEntered(e -> newButton.setStyle(BUTTON_HOVER_STYLE));
        newButton.setOnMousePressed(e -> newButton.setStyle(BUTTON_CLICKED_STYLE));
        newButton.setOnMouseReleased(e -> newButton.setStyle(BUTTON_HOVER_STYLE));
        newButton.setOnMouseMoved(e -> newButton.setStyle(BUTTON_HOVER_STYLE));
        newButton.setOnMouseExited(e -> newButton.setStyle(BUTTON_STATIC_STYLE));

        switch (label){
            case "Philosophers-DeadLock":
                newButton.setOnAction(this::startDeadlockPossibilityButton);
                break;
            case "Philosophers-Sync":
                newButton.setOnAction(this::startCorrectSynchronizationButton);
                break;
            case "Producers-Consumers":
                newButton.setOnAction(this::startProducerConsumer);
                break;
            case "Readers-Writers":
                newButton.setOnAction(this::startReadersWriters);
                break;
            case "Finish":
                newButton.setOnAction(this::stopButton);
                break;
            default:
                newButton.setOnAction(actionEvent -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "The program malfunction. Please close the application and start again.", ButtonType.OK);
                    alert.showAndWait();
                });
                break;
        }

        return newButton;
    }

    private boolean allThreadsFinished() {
        return philosophersLogic.allThreadsFinished() &&
                producerConsumerLogic.allThreadsFinished() &&
                readersWritersLogic.allThreadsFinished();
    }

    private void startDeadlockPossibilityButton(ActionEvent actionEvent) {
        if (allThreadsFinished()) {
            stageRootNode.setCenter(philosophersPane);
            philosophersPane.setTotalNumberOfPhilosophers(numOfPhilosophers);
            philosophersLogic.setNumberOfPhilosophers(numOfPhilosophers);
            philosophersPane.drawInitialFormation();
            philosophersLogic.createAndStartDeadlockProtocolThreads();
        }
    }

    private void startCorrectSynchronizationButton(ActionEvent actionEvent) {
        if (allThreadsFinished()) {
            stageRootNode.setCenter(philosophersPane);
            philosophersPane.setTotalNumberOfPhilosophers(numOfPhilosophers);
            philosophersLogic.setNumberOfPhilosophers(numOfPhilosophers);
            philosophersPane.drawInitialFormation();
            philosophersLogic.createAndStartSynchronizedProtocolThreads();
        }
    }

    private void startProducerConsumer(ActionEvent actionEvent) {
        if (allThreadsFinished()) {
            stageRootNode.setCenter(producerConsumerPane);
            producerConsumerPane.setBufferSize(bufferSize);
            producerConsumerLogic.setBufferSize(bufferSize);
            producerConsumerPane.drawInitialFormation();
            producerConsumerLogic.createAndStartConsumerProducer();
        }
    }

    private void startReadersWriters(ActionEvent actionEvent){
        if (allThreadsFinished()) {
            stageRootNode.setCenter(readersWritersPane);
            readersWritersPane.setNumberOfReaders(numOfReaders);
            readersWritersPane.setNumberOfWriters(numOfWriters);
            readersWritersLogic.setNumberOfReaders(numOfReaders);
            readersWritersLogic.setNumberOfWriters(numOfWriters);
            readersWritersPane.drawInitialFormation();
            readersWritersLogic.createAndStartReadersWriters();
        }
    }

    private void stopButton(ActionEvent actionEvent) {
        philosophersLogic.checkThreadsStateAndStopThem();
        producerConsumerLogic.stopConsumerAndProducer();
        readersWritersLogic.stopReadersWriters();
    }

    @Override
    public void stop() throws Exception {
        philosophersLogic.checkThreadsStateAndStopThem();
        producerConsumerLogic.stopConsumerAndProducer();
        readersWritersLogic.stopReadersWriters();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

}