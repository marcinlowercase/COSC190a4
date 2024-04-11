package org.example.cosc190a4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Operations extends Application {



    StringBuilder logString = new StringBuilder();
    TextArea logArea = new TextArea();
    HBox logBox = new HBox(logArea);
    Button showLog = new Button("Show log");
    Button clearLog = new Button("Clear log");
    Button exit = new Button("Exit");
    HBox buttonHBox = new HBox(showLog, clearLog, exit);

    Boolean isWindowClosing = false;


    // Element to work with database
    static String DatabasePath = "data_files/Assignment-4-2024.accdb" ;
    static int messageIDCount = 1;
    static Connection connection;

    static {
        try {
            connection = DBHelper.connect(DBHelper.DB_TYPE.ACCESS, DatabasePath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void start(Stage stage) throws Exception {



        // Three buttons
        logBox.setAlignment(Pos.CENTER);
        logBox.setPadding(new Insets(10, 10, 10, 10));
        logBox.setSpacing(10);




        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setBottom(buttonHBox);
        borderPane.setTop(logBox);


        Scene scene = new Scene(borderPane, 800, 400);



        // Exit Button
        exit.setOnAction(event -> turnOffServer());



        stage.setScene(scene);
        stage.setTitle("Operations");
        stage.show();
        stage.setResizable(false);

        stage.setOnCloseRequest(windowEvent -> {
            turnOffServer();
            if (!isWindowClosing) windowEvent.consume();
        });

        logArea.setPrefWidth(stage.getWidth() * 0.7);
        logArea.setPrefHeight(stage.getHeight() * 0.7);



        // Button bar size
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(20);


        //Start server

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

        if (result.get() == ButtonType.OK) {
            Platform.exit();
            isWindowClosing = true;
        }
        else {
            exitAlert.close();
            isWindowClosing = false;
        }
    }

    private void startServer(){
        try (ServerSocket serverSocket = new ServerSocket(9999)){
            System.out.println("Server start in socket: " + 9999);

            while(true){
                Socket socket = serverSocket.accept();

//                System.out.println("Accepted connection from: " + socket.getInetAddress().getHostAddress());

//                DataInputStream inputStreamFromClient = new DataInputStream(socket.getInputStream());

                ObjectInputStream inputStreamFromClient = new ObjectInputStream(socket.getInputStream());
                MessageEntry newMessageEntry = (MessageEntry) inputStreamFromClient.readObject();


                writeNewMessageEntryToDataBase(newMessageEntry);



//                logString.append(inputStreamFromClient.readUTF());
//                logString.append("\n");
//                logField.setText(logString.toString());
                logArea.appendText(inputStreamFromClient.readUTF() + "\n");

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeNewMessageEntryToDataBase(MessageEntry messageEntry) throws SQLException, ClassNotFoundException {



        String insertSQL = "INSERT INTO ChatLog (ID, ReceivedTimeStamp, ReceviedFromUserHandle, ReceviedFromUserEmail, ReceivedChatMessage, ReceivedFromUserIP, ReceivedFromUserPort, ReceivedFromUserPreferredColor) "
                +"VALUES (?, ?, ?, ?, ?)";




    }

    private void getMessageIDCount() throws SQLException {
        String countRowQuery = "SELECT COUNT(*) FROM LinkedAccessTable;";
        ResultSet countIDMessageRS = DBHelper.execute(connection, countRowQuery);
        if (countIDMessageRS.next()){
            messageIDCount = countIDMessageRS.getInt(1);
        }
        countIDMessageRS.close();
    }


}
