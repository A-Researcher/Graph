package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    private String localUrl;
    private ImageView imageView;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(20));
        File file = new File("F:/Models/Consensus(3)/images/graph.jpg");
        localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);
        imageView = new ImageView(image);
        root.getChildren().addAll(imageView);
        WatchDirectory watchdirectory = new WatchDirectory(this);
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(watchdirectory::register);
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    watchdirectory.register();
//                } catch (InterruptedException ex) {
//                    System.out.println("a");
//                }
//            }
//        });
        primaryStage.setTitle("Consensus graph");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    public void notifyNeedUpdate() {
        Platform.runLater(() -> {
            Image image = new Image(localUrl);
            imageView.setImage(image);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
