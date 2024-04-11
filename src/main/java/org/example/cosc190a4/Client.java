package org.example.cosc190a4;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;


public class Client extends Application {

    TextArea chatArea = new TextArea();
    HBox chatAreaHBox = new HBox(chatArea);
    TextField chatBox;



    static User userOnDevices;
    static String serverAddress;
    static int serverPort;




    /// element of setup screen

    static TextField nameTF = new TextField();
    static TextField handleTF = new TextField();
    static TextField colorRedTF = new TextField();
    static TextField colorGreenTF = new TextField();
    static TextField colorBlueTF = new TextField();
    static TextField emailTF = new TextField();



    // Element to work with JSON
    static String JSONPath = "data_files/info.json";
    static ObjectMapper mapper = new ObjectMapper();
    File infoFile = new File(JSONPath);


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
            System.out.println(userOnDevices);
        } else {
            changeToSetupScene(stage);

        }
        stage.show();

    }

    private User loadUserFromInfoJson() {
        try (BufferedReader reader = new BufferedReader(new FileReader(JSONPath))) {

            return mapper.readValue(reader.readLine(), new TypeReference<User>() {});
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean alreadyHaveUserInfo() {
        return infoFile.exists();
    }

    private void sendToServer(String serverAddress, int serverPort) throws IOException {

        Socket connectedServer = new Socket(serverAddress, serverPort);

//        MessageEntry messageEntry = new MessageEntry(1, new Date(), userOnDevices.getHandle(), userOnDevices.getEmail(), chatBox.getText(), "userIP", connectedServer.getPort(), userOnDevices.returnColor().toString());

        String messageToServer = chatBox.getText();


        DataOutputStream outputStream = new DataOutputStream(connectedServer.getOutputStream());

        outputStream.writeUTF(messageToServer);


    }


    private void changeToSetupScene(Stage stage){


        Label nameLabel = new Label("Name: ");
        Label handleLabel = new Label("Handle: ");
        Label colorLabel = new Label("Color: ");
        Label emailLabel = new Label("Email: ");


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

        colorRedTF.setPromptText("R");
        colorGreenTF.setPromptText("G");
        colorBlueTF.setPromptText("B");

        colorRedTF.setAlignment(Pos.CENTER);
        colorGreenTF.setAlignment(Pos.CENTER);
        colorBlueTF.setAlignment(Pos.CENTER);


        // Number filter for color input
        colorRedTF.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!keyEvent.getCharacter().matches("\\d|\\b")) {
                    keyEvent.consume();
                }
            }
        });
        colorGreenTF.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!keyEvent.getCharacter().matches("\\d|\\b")) {
                    keyEvent.consume();
                }
            }
        });
        colorBlueTF.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!keyEvent.getCharacter().matches("\\d|\\b")) {
                    keyEvent.consume();
                }
            }
        });


        // set preview color

        colorRedTF.setOnKeyTyped(keyEvent -> {
            changeOutlineColorBaseOnEnterValue();
        });

        colorGreenTF.setOnKeyTyped(keyEvent -> {
            changeOutlineColorBaseOnEnterValue();
        });

        colorBlueTF.setOnKeyTyped(keyEvent -> {
            changeOutlineColorBaseOnEnterValue();
        });




        // Empty when click on text field
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
        // Add new user using input///////////////////////////////////////////////////////////////////////////////
        createUserButton.setOnAction(actionEvent -> {
            userOnDevices = new User(nameTF.getText(), handleTF.getText(), colorRedTF.getText(), colorGreenTF.getText(), colorBlueTF.getText(), emailTF.getText());
            System.out.println(userOnDevices);
            try {
                storeUserToInfoJson(userOnDevices);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            changeToChatScene(stage);
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////






        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(gridPane);
        borderPane.setBottom(buttonHBox);

        Scene setUpScene = new Scene(borderPane, 800, 400);


        stage.setScene(setUpScene);
        stage.setResizable(false);
        stage.setTitle("Setup");
    }



//   function to preview color
    private void changeOutlineColorBaseOnEnterValue() {
        nameTF.setStyle("-fx-border-color: rgb("+ getValue(colorRedTF.getText()) +","+ getValue(colorGreenTF.getText()) +","+ getValue(colorBlueTF.getText())+"); ");
        handleTF.setStyle("-fx-border-color: rgb("+ getValue(colorRedTF.getText()) +","+ getValue(colorGreenTF.getText()) +","+ getValue(colorBlueTF.getText())+"); ");
        colorRedTF.setStyle("-fx-border-color: rgb("+ getValue(colorRedTF.getText()) +","+ getValue(colorGreenTF.getText()) +","+ getValue(colorBlueTF.getText())+"); ");
        colorGreenTF.setStyle("-fx-border-color: rgb("+ getValue(colorRedTF.getText()) +","+ getValue(colorGreenTF.getText()) +","+ getValue(colorBlueTF.getText())+"); ");
        colorBlueTF.setStyle("-fx-border-color: rgb("+ getValue(colorRedTF.getText()) +","+ getValue(colorGreenTF.getText()) +","+ getValue(colorBlueTF.getText())+"); ");
        emailTF.setStyle("-fx-border-color: rgb("+ getValue(colorRedTF.getText()) +","+ getValue(colorGreenTF.getText()) +","+ getValue(colorBlueTF.getText())+"); ");
//
    }


//    return value of the color text field
    private int getValue(String string){
        if (string.matches("[0-9]+")){
            int numberValue = Integer.parseInt(string);
            if (numberValue > 255){
                return 255;
            } else if (numberValue < 0){
                return 0;
            } else {
                return numberValue;
            }
        } else {
            return 0;
        }
    }

    private void storeUserToInfoJson(User user) throws IOException {
        mapper.writeValue(infoFile, user);
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
