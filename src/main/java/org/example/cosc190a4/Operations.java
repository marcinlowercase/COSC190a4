package org.example.cosc190a4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    static String DatabasePath = "data_files/Assignment-4-2024.accdb";
    static int messageIDCount;
    static Connection connection;

    // Element to work with Thread
    Lock lock = new ReentrantLock();

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


        // Clear log button

        clearLog.setOnAction(actionEvent -> {
            clearEverythingFromDB();
            System.out.println("Cleared");
        });


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
        Thread startServerThread = new Thread(() -> startServer());
        startServerThread.setDaemon(true);
        startServerThread.start();


    }

    private void clearEverythingFromDB() {

        String clearQuery = "DELETE FROM ChatLog";

        try {
            PreparedStatement prepareToClear = connection.prepareStatement(clearQuery);
            Statement clearStatement = connection.createStatement();
            clearStatement.execute(clearQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
        } else {
            exitAlert.close();
            isWindowClosing = false;
        }
    }

    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
//            System.out.println("Server start in socket: " + 9999);

            Platform.runLater(() -> System.out.println("Server started. Waiting for connections on port " + 9999 + "\n"));
            while (true) {
                Socket clientSocket = serverSocket.accept();

//                System.out.println("Accepted connection from: " + socket.getInetAddress().getHostAddress());

//                DataInputStream inputStreamFromClient = new DataInputStream(socket.getInputStream());


                Thread receiveMessageFromClientThread = new Thread(() -> receiveMessageEntryFromClient(clientSocket));
                receiveMessageFromClientThread.setDaemon(true);
                receiveMessageFromClientThread.start();

//                ObjectInputStream inputStreamFromClient = new ObjectInputStream(clientSocket.getInputStream());


//                logString.append(inputStreamFromClient.readUTF());
//                logString.append("\n");
//                logField.setText(logString.toString());
//                logArea.appendText(inputStreamFromClient.readUTF() + "\n");

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void receiveMessageEntryFromClient(Socket socket) {
        try (ObjectInputStream inputStreamFromClient = new ObjectInputStream(socket.getInputStream());) {
            while (true) {
                MessageEntry newMessageEntry = (MessageEntry) inputStreamFromClient.readObject();

                System.out.println(newMessageEntry);

                writeNewMessageEntryToDataBase(newMessageEntry, socket);


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeNewMessageEntryToDataBase(MessageEntry messageEntry, Socket socket) throws SQLException, ClassNotFoundException {
        lock.lock();

        getMessageIDCount();
        System.out.println(messageIDCount);

        String addQuery = "INSERT INTO ChatLog (ID, ReceivedTimeStamp, ReceviedFromUserHandle, ReceviedFromUserEmail, ReceivedChatMessage, ReceivedFromUserIP, ReceivedFromUserPort, ReceivedFromUserPreferredColor) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(addQuery);

        preparedStatement.setInt(1, messageIDCount + 1);
        preparedStatement.setDate(2, new java.sql.Date(messageEntry.getTimeStamp().getTime()));
        preparedStatement.setString(3, messageEntry.getUserHandle());
        preparedStatement.setString(4, messageEntry.getUserEmail());
        preparedStatement.setString(5, messageEntry.getChatMessage());
        preparedStatement.setString(6, String.valueOf(socket.getInetAddress()));
        messageEntry.setUserPort(socket.getPort());
        preparedStatement.setInt(7, messageEntry.getUserPort());
        preparedStatement.setString(8, Color.rgb(messageEntry.getUserPreferredColorR(), messageEntry.getUserPreferredColorG(), messageEntry.getUserPreferredColorB()).toString());

        preparedStatement.execute();


        System.out.println("Hello " + messageEntry);

        lock.unlock();
    }

    private void getMessageIDCount() throws SQLException {
        String countRowQuery = "SELECT COUNT(*) FROM ChatLog;";
        ResultSet countIDMessageRS = DBHelper.execute(connection, countRowQuery);
        if (countIDMessageRS.next()) {
            messageIDCount = countIDMessageRS.getInt(1);
        }
        countIDMessageRS.close();
    }

    private void writeToDB() {

    }


}
