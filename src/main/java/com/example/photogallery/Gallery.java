package com.example.photogallery;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Gallery extends Application {

    private List<Image> images = Arrays.asList(
            new Image(Objects.requireNonNull(getClass().getResource("/com/example/photogallery/ape.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/com/example/photogallery/bike.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/com/example/photogallery/bike2.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/com/example/photogallery/khalifa.png")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/com/example/photogallery/map.jpg")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/com/example/photogallery/monster.png")).toExternalForm())
    );

    private int currentIndex = -1;
    private ImageView fullSizeView = new ImageView();
    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        GridPane gridPane = thumbNailGridpane();

        StackPane root = new StackPane(gridPane);
        root.getStyleClass().add("grid-root");

        Scene scene = new Scene(root, 500, 400);
        String cssPath = "/com/example/photogallery/style.css";
        String cssExternalForm = Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm();
        scene.getStylesheets().add(cssExternalForm);

        primaryStage.setTitle("Image Gallery");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane thumbNailGridpane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        int colCount = 0;
        int rowCount = 0;
        for (Image image : images) {
            ImageView thumbnailImageView = thumbNailImageView(image, images.indexOf(image));
            gridPane.add(thumbnailImageView, colCount, rowCount);
            colCount++;
            if (colCount == 3) {
                colCount = 0;
                rowCount++;
            }
        }

        return gridPane;
    }

    private ImageView thumbNailImageView(Image image, int index) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.getStyleClass().add("thumbnail");
        imageView.setOnMouseClicked(e -> fullSizeImage(index));
        return imageView;
    }

    private void fullSizeImage(int index) {
        if (index >= 0 && index < images.size()) {
            currentIndex = index;
            fullSizeView.setImage(images.get(currentIndex));
            VBox fullSizeView = imageFullSizeView();
            StackPane root = new StackPane(this.fullSizeView, fullSizeView);
            stage.getScene().setRoot(root);
        }
    }

    private VBox imageFullSizeView() {
        double maxWidth = stage.getWidth() * 0.8;
        double maxHeight = stage.getHeight() * 0.8;

        fullSizeView.setPreserveRatio(true);
        fullSizeView.setFitWidth(maxWidth);
        fullSizeView.setFitHeight(maxHeight);

        Button previous = new Button("Previous");
        previous.setOnAction(e -> {
            if (currentIndex > 0) {
                fullSizeImage(currentIndex - 1);
            }
        });

        Button next = new Button("Next");
        next.setOnAction(e -> {
            if (currentIndex < images.size() - 1) {
                fullSizeImage(currentIndex + 1);
            }
        });

        Button back = new Button("Back");
        back.setOnAction(e -> {
            stage.getScene().setRoot(thumbNailGridpane());
        });

        VBox navigation = new VBox(10, previous, next, back);
        navigation.setPadding(new Insets(20));
        return navigation;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
