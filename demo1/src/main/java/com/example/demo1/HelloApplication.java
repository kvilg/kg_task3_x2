package com.example.demo1;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;

public class HelloApplication extends Application {
    private static double rPadding = 20;
    private static double rMargin = 8;
    private static double moveTime = 450;
    private static double pause = moveTime;
    private static double xW = 400;
    private static double yH = 400;

    @Override
    public void start(Stage primaryStage)  {
        Group root = new Group();
        primaryStage.setScene(new Scene(root, xW, yH, Color.BLACK));
        primaryStage.setResizable(true);

        Group circles = new Group();
        ParallelTransition animation = new ParallelTransition();


        int in_raw = (int) ceil(xW / rPadding) / 2;
        int in_column = (int) ceil(yH / sqrt(3) / rPadding) / 2;

        for (int i = -in_column; i <= in_column; i++) {
            for (int j = -in_raw; j <= in_raw; j++) {

                Circle circle = new Circle(getX(i, j), getY(i), rMargin, Color.WHITE);
                circles.getChildren().add(circle);
                SequentialTransition st = new SequentialTransition();
                st.getChildren().addAll(
                        new PathTransition(
                                Duration.millis(moveTime),
                                new Path(
                                        new MoveTo(getX(i, j), getY(i)),
                                        new LineTo(getX(i, j + (i % 2 == 0 ? 1 : -1)), getY(i))
                                ),
                                circle),
                        new PauseTransition(Duration.millis(pause)),

                        new PathTransition(
                                Duration.millis(moveTime),
                                new Path(
                                        new MoveTo(getX(i, j), getY(i)),
                                        new LineTo(getX(i + (j % 2 == 0 ? 1 : -1), j), getY(i + (j % 2 == 0 ? 1 : -1)))
                                ),
                                circle),
                        new PauseTransition(Duration.millis(pause)),

                        new PathTransition(
                                Duration.millis(moveTime),
                                new Path(
                                        new MoveTo(getX(i, j), getY(i)),
                                        new LineTo(getX(i - ((i + j) % 2 == 0 ? 1 : -1), j + ((i + j) % 2 == 0 ? 1 : -1)), getY(i - ((i + j) % 2 == 0 ? 1 : -1)))
                                ),
                                circle),
                        new PauseTransition(Duration.millis(pause))
                );
                st.setCycleCount(Animation.INDEFINITE);
                animation.getChildren().add(st);
            }
        }

        root.getChildren().add(circles);
        primaryStage.show();
        animation.play();
    }

    private double getX(double x, double y) {
        return xW/2 + x * rPadding + y * 2 * rPadding;
    }

    private double getY(double x) {
        return yH/2 + x * sqrt(3) * rPadding;
    }
}