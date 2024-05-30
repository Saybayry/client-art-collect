package com.example.client;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.io.IOException;


public class ArtCardController {
    @FXML
    private ImageView contentVImage;

    @FXML
    private Label ArtNameLable;
    @FXML
    private Label ArtistLable;
    @FXML
    private Label TechniqueLable;
    @FXML
    private Label ArtMovementLable;

    String url_img = "http://localhost:8081/";


    @FXML
    public void initialize() {
    }



    public void setData(JSONObject jsonObject) {
        System.out.println(jsonObject);
        System.out.println(jsonObject.optString("imagePath", "goblin_girl.jpeg"));
        String image_name = jsonObject.optString("imagePath", "goblin_girl.jpeg");
        update_image(image_name);
        ArtNameLable.setText(jsonObject.optString("name", "No Title"));

        JSONObject jsonArtist = jsonObject.getJSONObject("artist");
        String artist_str = jsonArtist.optString("firstName")+" "+ jsonArtist.optString("lastName")+" "+jsonArtist.optString("otherName");
        ArtistLable.setText(artist_str);

//        JSONObject jsonTechnique = jsonObject.getJSONObject("technique").opt("name");
        TechniqueLable.setText(jsonObject.getJSONObject("technique").optString("name"));
        ArtMovementLable.setText(jsonObject.getJSONObject("artMovement").optString("name"));

        // Обновляем UI с использованием данных из jsonObject
//        titleLabel.setText(jsonObject.optString("title", "No Title"));
//        descriptionLabel.setText(jsonObject.optString("description", "No Description"));
    }



    public void update_image(String image_name){
        String img_server_url = "http://localhost:8081/";
        url_img = img_server_url +image_name;

        Task<Image> loadImageTask = new Task<>() {
            @Override
            protected Image call() throws Exception {
                String imageUrl = url_img;
                return new Image(imageUrl, true);  // true - загружать в фоне
            }

            @Override
            protected void succeeded() {
                Image image = getValue();
                System.out.println(image);
                contentVImage.setImage(image); // Устанавливаем изображение после завершения загрузки
            }

            @Override
            protected void failed() {
                getException().printStackTrace();
            }
        };

        new Thread(loadImageTask).start();

    }
}
