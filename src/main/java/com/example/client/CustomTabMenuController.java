package com.example.client;

import com.example.client.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CustomTabMenuController {
    public Label nameUserLable;
    @FXML
    private StackPane contentPane;

    private String token = "";

    @FXML
    private VBox tab1Content;

    @FXML
    private VBox tab2Content;

    @FXML
    private VBox tab3Content;

    @FXML
    private Button tab1Button;

    @FXML
    private Button tab2Button;

    @FXML
    private Button tab3Button;


    @FXML
    private VBox contentVBox;
    @FXML
    public void initialize() {

    }


    public void updateMyCollect(){
        contentVBox.getChildren().clear();
        get_request();
    }


    @FXML
    private void handleTab1ButtonAction() {
        tab1Content.toFront();
        updateTabStyles(tab1Button);
    }

    @FXML
    private void handleTab2ButtonAction() {
        tab2Content.toFront();
        updateTabStyles(tab2Button);
    }

    @FXML
    private void handleTab3ButtonAction() {
        tab3Content.toFront();
        updateTabStyles(tab3Button);
    }



    private void add_upload_form() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("upload_art_form.fxml"));
            Node node = loader.load();

            UploadArtForm controller = loader.getController();
            controller.setMainController(this,token);
            tab2Content.getChildren().add(node); // Добавляем узел в VBox

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTabStyles(Button activeButton) {
        tab1Button.setStyle("-fx-background-color: #3a5a40;-fx-text-fill: #a3b18a; ");
        tab2Button.setStyle("-fx-background-color: #3a5a40;-fx-text-fill: #a3b18a; ");
        tab3Button.setStyle("-fx-background-color: #3a5a40;-fx-text-fill: #a3b18a; ");
        tab1Button.setEffect(null);
        tab2Button.setEffect(null);
        tab3Button.setEffect(null);

        activeButton.setStyle("-fx-background-color: #dad7cd; -fx-background-radius: 10 0 0 10; -fx-text-fill: #344e41;");
        activeButton.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.web("#344e41"),4,0,2,0));

    }
    /// часть с запросом
    public void get_request() {
        String url = "http://localhost:8080/arts/my-arts";
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




    public JSONArray fetchJsonArray(String url) throws IOException, InterruptedException {
        // Создаем HttpClient
        HttpClient client = HttpClient.newHttpClient();


        // Создаем запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Java HttpClient")
                .header("Authorization", "Bearer " + token)
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

    private User currentUser;
    public void initData(User currentUser) {
        this.currentUser = currentUser;
        nameUserLable.setText(currentUser.getName());
        this.token =  currentUser.getToken();
        tab1Content.toFront(); // Показываем первый таб по умолчанию
        updateTabStyles(tab1Button);
        get_request();

        add_upload_form();
    }
}



