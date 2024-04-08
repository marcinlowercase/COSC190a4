package org.example.cosc190a4.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Run extends Application {
    @Override
    public void start(Stage stage) throws Exception {


        Circle circle = new Circle(100);
        circle.setFill(Color.DARKGREY);
        circle.setStroke(Color.RED);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(circle);

        Scene scene = new Scene(borderPane, 400, 400);

        stage.setScene(scene);
        stage.show();

    }
}
