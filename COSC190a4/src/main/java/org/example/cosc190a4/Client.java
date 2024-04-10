package org.example.cosc190a4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class Client extends Application {

    TextField chatBox;

    Button sendButton;

    static String serverAddress;
    static int serverPort;

    public static void main(String[] args) {
          serverAddress = "localhost";
          serverPort = 9999;

          Application.launch();

    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        chatBox = new TextField();
        sendButton = new Button("Send");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(chatBox, sendButton);
        hBox.setSpacing(10);
//        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setAlignment(Pos.CENTER);

        sendButton.setOnAction(actionEvent -> {
            try {
                sendToServer(serverAddress, serverPort);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        borderPane.setBottom(hBox);
        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Chat Program");
        stage.show();
    }

    private void sendToServer(String serverAddress, int serverPort) throws IOException {

        Socket connectedServer = new Socket(serverAddress, serverPort);

        String messageToServer = chatBox.getText();

        DataOutputStream outputStream = new DataOutputStream(connectedServer.getOutputStream());

        outputStream.writeUTF(messageToServer);


    }
}
