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






public class Client extends Application {

    TextArea chatArea = new TextArea();
    HBox chatAreaHBox = new HBox(chatArea);
    TextField chatBox;


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
            changeToChatScene(stage);
        } else {
            changeToSetupScene(stage);
        }
        stage.show();



//        // Main Scene /////////////////////////////////////////////////////
//
//        BorderPane borderPane = new BorderPane();
//        chatBox = new TextField();
//
//        Label lblMessage = new Label("Message: ");
////        chatAreaHBox.getChildren().add(lblMessage);
//
//        chatArea.setEditable(false); // Makes the chat box un-editable TreyO
//
//        HBox chatBoxHBox = new HBox();
//        chatBoxHBox.getChildren().addAll(lblMessage, chatBox);
//        chatBoxHBox.setSpacing(10);
//        chatBoxHBox.setPadding(new Insets(10, 10, 0, 10));
//        chatBoxHBox.setAlignment(Pos.CENTER);
//
//
//
//        chatAreaHBox.setPadding(new Insets(10, 10, 10, 10)); // bob
//        chatAreaHBox.setSpacing(10);
//
//
//        chatBox.setOnAction(actionEvent -> {
//            try {
//                sendToServer(serverAddress, serverPort);
//                chatBox.setText("");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//
//
//        chatAreaHBox.setAlignment(Pos.CENTER);
//
//
//        borderPane.setCenter(chatAreaHBox);
//        borderPane.setBottom(chatBoxHBox);
//        borderPane.setPadding(new Insets(20, 20, 20, 20));
//
//        Scene scene = new Scene(borderPane, 800, 400);
//        stage.setScene(scene);
//        stage.setTitle("Chat Program");
//        stage.setResizable(false);
//        stage.show();
//
//        chatArea.setPrefWidth(stage.getWidth() * 0.7);
//        chatArea.setPrefHeight(stage.getHeight() * 0.7);
//        chatBoxHBox.setPrefWidth(stage.getWidth() * chatBox.getWidth());
//        chatBox.setPrefWidth(stage.getWidth()*0.7 - lblMessage.getWidth() - 10);

    }

    private boolean alreadyHaveUserInfo() {
        return new File(JSONPath).exists();
    }

    private void sendToServer(String serverAddress, int serverPort) throws IOException {

        Socket connectedServer = new Socket(serverAddress, serverPort);

        String messageToServer = chatBox.getText();



        DataOutputStream outputStream = new DataOutputStream(connectedServer.getOutputStream());

        outputStream.writeUTF(messageToServer);


    }


    private void changeToSetupScene(Stage stage){

//        GridPane gridPane

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

        HBox nameHBox = new HBox(nameLabel, nameTF);
        HBox handleHBox = new HBox(handleLabel, handleTF);
        HBox colorHBox = new HBox(colorLabel, colorRedTF, colorGreenTF, colorBlueTF);
        HBox emailHBox = new HBox(emailLabel, emailTF);
        HBox buttonHBox = new HBox(createUserButton);

        nameHBox.setAlignment(Pos.CENTER_LEFT);
        handleHBox.setAlignment(Pos.CENTER_LEFT);
        colorHBox.setAlignment(Pos.CENTER_LEFT);
        emailHBox.setAlignment(Pos.CENTER_LEFT);
        buttonHBox.setAlignment(Pos.CENTER);



        VBox setupVbox = new VBox(nameHBox, handleHBox, colorHBox, emailHBox);
        setupVbox.setAlignment(Pos.CENTER);




        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(setupVbox);
        borderPane.setBottom(buttonHBox);

        Scene setUpScene = new Scene(borderPane, 800, 400);


        stage.setScene(setUpScene);
        stage.setTitle("Setup");
    }

    private void changeToChatScene(Stage stage){
        // Main Scene /////////////////////////////////////////////////////

        BorderPane borderPane = new BorderPane();
        chatBox = new TextField();

        Label lblMessage = new Label("Message: ");
//        chatAreaHBox.getChildren().add(lblMessage);

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
