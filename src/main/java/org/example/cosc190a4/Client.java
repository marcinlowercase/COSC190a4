package org.example.cosc190a4;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Date;


public class Client extends Application {

    TextArea chatArea = new TextArea();
    HBox chatAreaHBox = new HBox(chatArea);
    TextField chatBox;



    static User userOnDevices;
    static String serverAddress;
    static int serverPort;

    static String JSONPath = "data_files/info.json";





    public static void main(String[] args) throws IOException {
          // JSON handler

//        handleUserInfo();



          serverAddress = "localhost";
          serverPort = 9999;

          Application.launch();

    }




    private static void handleUserInfo() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        File infoJson = new File(JSONPath);

        if (infoJson.exists()){

            BufferedReader reader = new BufferedReader(new FileReader(JSONPath));
            String inputJson = reader.readLine();
            reader.close();

            User user1 = mapper.readValue(inputJson, new TypeReference<User>() {});

            System.out.println(user1);

        } else {

            //            User user1 = new User("theo", "TT", "Color.RED", "theodore@gmail.com");
//            mapper.writeValue(new File(JSONPath), user1);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {


        if (alreadyHaveUserInfo()){
            userOnDevices = loadUserFromInfoJson();
            changeToChatScene(stage);
        } else {
            changeToSetupScene(stage);
        }
        stage.show();

    }

    private User loadUserFromInfoJson() {
        return new User();
    }

    private boolean alreadyHaveUserInfo() {
        return new File(JSONPath).exists();
    }

    private void sendToServer(String serverAddress, int serverPort) throws IOException {

        Socket connectedServer = new Socket(serverAddress, serverPort);

        //// need to fix
        MessageEntry messageEntry = new MessageEntry(1, new Date(), userOnDevices.getHandle(), userOnDevices.getEmail(), chatBox.getText(), "userIP", connectedServer.getPort(), userOnDevices.getTextColor().toString());

        String messageToServer = chatBox.getText();


        DataOutputStream outputStream = new DataOutputStream(connectedServer.getOutputStream());

        outputStream.writeUTF(messageToServer);


    }


    private void changeToSetupScene(Stage stage){


        Label nameLabel = new Label("Name: ");
        Label handleLabel = new Label("Handle: ");
        Label colorLabel = new Label("Color: ");
        Label emailLabel = new Label("Email: ");

        TextField nameTF = new TextField();
        TextField handleTF = new TextField();
        TextField colorRedTF = new TextField();
        TextField colorGreenTF = new TextField();
        TextField colorBlueTF = new TextField();
        TextField emailTF = new TextField();

        Button createUserButton = new Button("Create A User");

        HBox colorTF = new HBox(colorRedTF, colorGreenTF, colorBlueTF);
        colorTF.setAlignment(Pos.CENTER);
        colorTF.setSpacing(10);


        HBox buttonHBox = new HBox(createUserButton);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setPadding(new Insets(10, 10, 10, 10));


        //        GridPane gridPane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTF, 1, 0);
        gridPane.add(handleLabel, 0, 1);
        gridPane.add(handleTF, 1, 1);
        gridPane.add(colorLabel, 0, 2);
        gridPane.add(colorTF, 1, 2);
        gridPane.add(emailLabel, 0, 3);
        gridPane.add(emailTF, 1, 3);


        // set place holder for RGB

        colorRedTF.setText("R");
        colorGreenTF.setText("G");
        colorBlueTF.setText("B");

        colorRedTF.setAlignment(Pos.CENTER);
        colorGreenTF.setAlignment(Pos.CENTER);
        colorBlueTF.setAlignment(Pos.CENTER);

        colorRedTF.setOnMouseClicked(actionEvent -> {
            colorRedTF.setText("");
        });

        colorGreenTF.setOnMouseClicked(actionEvent -> {
            colorGreenTF.setText("");
        });
        colorBlueTF.setOnMouseClicked(actionEvent -> {
            colorBlueTF.setText("");
        });

        ///////////////
        // Add new user using input
        createUserButton.setOnAction(actionEvent -> {
            userOnDevices = new User(nameTF.getText(), handleTF.getText(), colorRedTF.getText(), colorGreenTF.getText(), colorBlueTF.getText(), emailTF.getText());
            System.out.println(userOnDevices);
            storeToInfoJson();
            changeToChatScene(stage);
        });

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(gridPane);
        borderPane.setBottom(buttonHBox);

        Scene setUpScene = new Scene(borderPane, 800, 400);


        stage.setScene(setUpScene);
        stage.setResizable(false);
        stage.setTitle("Setup");
    }

    private void storeToInfoJson() {
        //do nothing right now
    }

    private void changeToChatScene(Stage stage){

        // Main Scene /////////////////////////////////////////////////////

        BorderPane borderPane = new BorderPane();
        chatBox = new TextField();

        Label lblMessage = new Label("Message: ");

        chatArea.setEditable(false); // Makes the chat box un-editable TreyO

        HBox chatBoxHBox = new HBox();
        chatBoxHBox.getChildren().addAll(lblMessage, chatBox);
        chatBoxHBox.setSpacing(10);
        chatBoxHBox.setPadding(new Insets(10, 10, 0, 10));
        chatBoxHBox.setAlignment(Pos.CENTER);

        chatAreaHBox.setPadding(new Insets(10, 10, 10, 10)); // bob
        chatAreaHBox.setSpacing(10);


        chatBox.setOnAction(actionEvent -> {
            try {
                sendToServer(serverAddress, serverPort);
                chatBox.setText("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });



        chatAreaHBox.setAlignment(Pos.CENTER);


        borderPane.setCenter(chatAreaHBox);
        borderPane.setBottom(chatBoxHBox);
        borderPane.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene(borderPane, 800, 400);
        stage.setScene(scene);
        stage.setTitle("Chat Program");
        stage.setResizable(false);
        stage.show();

        chatArea.setPrefWidth(stage.getWidth() * 0.7);
        chatArea.setPrefHeight(stage.getHeight() * 0.7);
        chatBoxHBox.setPrefWidth(stage.getWidth() * chatBox.getWidth());
        chatBox.setPrefWidth(stage.getWidth()*0.7 - lblMessage.getWidth() - 10);
    }

}
