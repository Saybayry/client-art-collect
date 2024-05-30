package com.example.client;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class MainController {



    @FXML
    private VBox contentVBox;


    @FXML
    public void initialize() {
        System.out.println("выводим тест");
        get_request();
    }


    public void update(){
    }

    public void get_request() {
        String url = "http://localhost:8080/arts";
        try {
            JSONArray jsonArray = fetchJsonArray(url);


            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    loadArtCard(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static JSONArray fetchJsonArray(String url) throws IOException, InterruptedException {
        // Создаем HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Создаем запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Java HttpClient")
                .build();

        // Выполняем запрос и получаем ответ
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        // Логируем ответ для отладки
        System.out.println("HTTP Response: " + responseBody);

        // Преобразуем ответ в JSON массив
        try {
            return new JSONArray(responseBody);
        } catch (JSONException e) {
            // Логируем ошибку и ответ, который вызвал ошибку
            System.err.println("Invalid JSON response: " + responseBody);
            throw e;
        }
    }
    private void loadArtCard(JSONObject jsonObject ) {
        System.out.println("выводим тест");
        try {
            // Загружаем FXML в основном потоке
            FXMLLoader loader = new FXMLLoader(getClass().getResource("art_card.fxml"));
            Node node = loader.load();

            ArtCardController controller = loader.getController();
            controller.setData(jsonObject);

            contentVBox.getChildren().add(node); // Добавляем узел в VBox
            System.out.println(contentVBox.heightProperty());
            contentVBox.prefHeightProperty().bind(contentVBox.heightProperty());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}