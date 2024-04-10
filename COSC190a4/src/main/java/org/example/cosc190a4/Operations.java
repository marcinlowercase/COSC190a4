package org.example.cosc190a4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Optional;

public class Operations extends Application {


    TextField log = new TextField();
    Button showLog = new Button("Show log");
    Button clearLog = new Button("Clear log");
    Button exit = new Button("Exit");

    @Override
    public void start(Stage stage) throws Exception {
        // Three buttons

        log.setPrefSize(stage.getWidth(), stage.getHeight()*0.8);

        HBox hBox = new HBox(showLog, clearLog, exit);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(0, 0, 20, 0));
        borderPane.setBottom(hBox);
        borderPane.setTop(log);


        Scene scene = new Scene(borderPane, 400, 400);



        // Exit Button
        exit.setOnAction(event -> turnOffServer());


        stage.setScene(scene);

        stage.show();

        Thread startServerThread = new Thread(this::startServer);
        startServerThread.start();


//        try {
//            ServerSocket serverSocket = new ServerSocket(9999);
//            System.out.println("Server started.Serving at port: " + 9999);
//
//            while (true){
//
//                Socket connectedClient = serverSocket.accept();
//
//                DataInputStream inputStreamFromClient = new DataInputStream((connectedClient.getInputStream()));
//
//                String messageFromClient = inputStreamFromClient.readUTF();
//
//                System.out.println(messageFromClient);
//
//
//
//                // Respond to client
//
//                DataOutputStream outputStreamToClient = new DataOutputStream(connectedClient.getOutputStream());
//
//                String messageToClient = "Hello" + new Date();
//                outputStreamToClient.writeUTF(messageToClient);
//
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    private void turnOffServer() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit Confirmation");
        exitAlert.setHeaderText("Exit Confirmation");
        exitAlert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = exitAlert.showAndWait();

        if (result.get() == ButtonType.OK) Platform.exit();
        else exitAlert.close();
    }

    private void startServer(){
        try (ServerSocket serverSocket = new ServerSocket(9999)){
            System.out.println("Server start in socket: " + 9999);

            while(true){
                Socket socket = serverSocket.accept();

//                System.out.println("Accepted connection from: " + socket.getInetAddress().getHostAddress());

                DataInputStream inputStreamFromClient = new DataInputStream(socket.getInputStream());

                String messageFromClient = inputStreamFromClient.readUTF();

                System.out.println(messageFromClient);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
