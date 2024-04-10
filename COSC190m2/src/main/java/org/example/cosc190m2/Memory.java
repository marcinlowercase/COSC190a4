package org.example.cosc190m2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Memory extends Application {
    private Image[] aImages;    //Array to hold Images
    private ArrayList<Image> lstRem = new ArrayList<>();
    private static final int TOTAL_IMAGES = 4;
    private BorderPane paneGameContainer;      //Major Game Container Pane
    private StackPane paneImageStack;   // Pane which will store ivMemory image
    private HBox hboxImageThumbNails;   //Pane actually stores the 4 buttons - preloaded by loadImageBar
    private HBox hboxStartButton;        //pane contains a simple start button
    private ImageView ivMemoryImage;  // The image to play with

    private int level = 3;

    private int[] memoryImage = new int[level];

    @Override
    public void start(Stage stage) throws Exception {


        // load the images into an image array
        loadImages();









        // load the image bar to show the image thumbnails
        hboxImageThumbNails = setupImageThumbnails();

        // load the start button
        hboxStartButton = setupStartButton();

        // load the pane to display images
        paneImageStack = setupImageDisplay(pickARandomImage());

        // set up the main game container
        paneGameContainer = new BorderPane();

        paneGameContainer.setBottom(hboxStartButton);
        paneGameContainer.setCenter(paneImageStack);




        stage.setScene(new Scene(paneGameContainer, 500, 300));
        stage.setTitle("Memory Game");
        stage.show();
    }

    public void loadImages() {
        aImages = new Image[TOTAL_IMAGES];
        aImages[0] = new Image("file:images/briefcase.png");
        aImages[1] = new Image("file:images/bullseye.png");
        aImages[2] = new Image("file:images/earth.png");
        aImages[3] = new Image("file:images/moneybag.png");
    }

    /**
     * This method will return a stack pane with the ivMemory imageView placed on it.
     * returns A stack pane that could be used to display images.
     *
     * @return
     */
    private StackPane setupImageDisplay(Image image) {
        ivMemoryImage = new ImageView(image);
        ivMemoryImage.setFitHeight(80);
        ivMemoryImage.setFitWidth(80);

        StackPane obPane = new StackPane();

        obPane.getChildren().clear();
        obPane.getChildren().add(ivMemoryImage);

        return obPane;
    }


    /**
     * This method will be responsible for loading the four Images at the bottom of the pane.
     *
     * @return
     */
    private HBox setupImageThumbnails() {

        HBox obBox = new HBox();
        obBox.setSpacing(15);
        obBox.setPadding(new Insets(20, 20, 20, 20));
        obBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < TOTAL_IMAGES; i++) {

            ImageView obCurrent = new ImageView(aImages[i]);
            obCurrent.setFitHeight(50);
            obCurrent.setFitWidth(50);

            Button obButton = new Button("", obCurrent);
            obBox.getChildren().add(obButton);
        }

        return obBox;
    }

    /**
     * This pane returns an HBox button with a start Button on it.
     *
     * @return
     */
    public HBox setupStartButton() {

        HBox obBox = new HBox();
        obBox.setSpacing(15);
        obBox.setPadding(new Insets(20, 20, 20, 20));
        obBox.setAlignment(Pos.CENTER);

        Button obStart = new Button("Start");
        obBox.getChildren().add(obStart);

        //Start button onclick

        obStart.setOnAction(actionEvent -> {
            try {
                game();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        return obBox;
    }

    private void game() throws InterruptedException {



        for (int i = 0; i < level; i++){
//            setupImageDisplay(pickARandomImage());
//            paneImageStack = setupImageDisplay(pickARandomImage());
//            paneGameContainer.setCenter(paneImageStack);

            showThePictures();
            showBlankScreen();



            System.out.println("Turn: " + i);
        }
        displayAnswerScreen();


//                paneImageStack = setupImageDisplay(pickARandomImage());
    }

    private void displayAnswerScreen() {


        Button briefCaseButton = new Button();
        ImageView briefCaseIV = new ImageView(aImages[0]);
        briefCaseIV.setPreserveRatio(true);
        briefCaseIV.setFitHeight(60);
        briefCaseButton.setGraphic(briefCaseIV);

        Button bullseyeButton = new Button();
        ImageView bullseyeIV = new ImageView(aImages[1]);
        bullseyeIV.setPreserveRatio(true);
        bullseyeIV.setFitHeight(60);
        bullseyeButton.setGraphic(bullseyeIV);

        Button earthButton = new Button();
        ImageView earthIV = new ImageView(aImages[2]);
        earthIV.setPreserveRatio(true);
        earthIV.setFitHeight(60);
        earthButton.setGraphic(earthIV);

        Button moneyBagButton = new Button();
        ImageView moneyIV = new ImageView(aImages[3]);
        moneyIV.setPreserveRatio(true);
        moneyIV.setFitHeight(60);
        moneyBagButton.setGraphic(moneyIV);




        HBox answerHbox = new HBox(briefCaseButton, bullseyeButton, earthButton, moneyBagButton);
        answerHbox.setAlignment(Pos.CENTER);
        answerHbox.setSpacing(10);
        answerHbox.setPadding(new Insets(10, 10, 10,10));

        paneGameContainer.setBottom(answerHbox);
    }

    private void showBlankScreen() {
        Label empty = new Label(" ");

        paneGameContainer.setCenter(empty);
        paneGameContainer.setCenter(empty);
        timeOf(500);
    }

    private void showThePictures() {

        paneImageStack = setupImageDisplay(pickARandomImage());
        paneGameContainer.setCenter(paneImageStack);
        timeOf(1000);
    }


    private Image pickARandomImage() {
        int randomNumber = (int) (Math.random() * 4);
//        memoryImage[0]
        System.out.println("randomenum: " +randomNumber);
        return aImages[randomNumber];
    }

    private void timeOf(long duration){
        long currentTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - currentTime < duration){
            // wait
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
