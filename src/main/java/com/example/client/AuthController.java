package com.example.client;

import com.example.client.dto.JwtAuthenticationResponse;
import com.example.client.dto.SignInRequest;
import com.example.client.dto.SignUpRequest;
import com.example.client.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

public class AuthController {



    public Pane singUp_tab;
    public Pane singIn_tab;
    public Button singInButtn;
    public Button to_SignUPanel;
    public Button singUPButton;
    public Button to_SignInPanel;
    public PasswordField singUpPasswordFiled;
    public TextField singUpEmailFiled;
    public TextField singUpLoginFiled;
    public TextField singInLoginFiled;
    public PasswordField singInPasswordFiled;


    private User currentUser;

    public void initialize() {
        currentUser = new User(); // Инициализируем currentUser
    }
    @FXML
    private void to_SignUPanelButtonAction() {singIn_tab.toFront(); }
    @FXML
    public void to_SignInPanelButtonAction(ActionEvent actionEvent) {
        singIn_tab.toFront();
    }
    @FXML
    public void to_SignUPPanelButtonAction(ActionEvent actionEvent) {
        singUp_tab.toFront();
    }




    public void SingUpButtonAction(ActionEvent actionEvent) {
        String login = singUpLoginFiled.getText();
        String email = singUpEmailFiled.getText();
        String password = singUpPasswordFiled.getText();

        // Создание запроса
        SignUpRequest signUpRequest = new SignUpRequest(login, email, password);
        ObjectMapper objectMapper = new ObjectMapper();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("http://localhost:8080/auth/sign-up");
            String json = objectMapper.writeValueAsString(signUpRequest);
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);
            request.setHeader("Content-Type", "application/json");

            // Отправка запроса и получение ответа
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getCode() == 200) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    JwtAuthenticationResponse jwtResponse = objectMapper.readValue(responseBody, JwtAuthenticationResponse.class);


                    String token = jwtResponse.getToken();


                    System.out.println("Received token: " + token);
                    JSONObject user_dataJson = DateFromToken(token);
                    System.out.println();
                    String emailUser  = user_dataJson.get("email").toString();
                    int idUser =  Integer.parseInt(user_dataJson.get("id").toString());
                    String nameUser = user_dataJson.get("sub").toString();
                    User user = new User( idUser,emailUser,token,nameUser );
                    handleButtonAction(user);

                } else {
                    System.err.println("Registration failed: " + response.getReasonPhrase());
                    // вывод в ui
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private JSONObject DateFromToken(String token){
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        System.out.println(header);
        System.out.println(payload);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(payload);
        }catch (JSONException err){
            err.printStackTrace();
        }
        System.out.println(jsonObject);
        return jsonObject;
    }

    private void handleButtonAction(User user) throws Exception {
        // Загружаем FXML для второго окна
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Art_main2.fxml"));
        Parent root = loader.load();

        // Получаем контроллер второго окна и передаем данные
        CustomTabMenuController controller = loader.getController();
        controller.initData(user);

        // Создаем новое окно
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Second Window");
        stage.show();
        System.out.println("некст окно");
        Stage currentStage = (Stage)to_SignInPanel.getScene().getWindow();
        // Закрываем текущее окно
        currentStage.close();
    }

    public void SingInButtonAction(ActionEvent actionEvent) {
        String login = singInLoginFiled.getText();

        String password = singInPasswordFiled.getText();

        // Создание запроса
        SignInRequest signUpRequest = new SignInRequest(login,password);
        ObjectMapper objectMapper = new ObjectMapper();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("http://localhost:8080/auth/sign-in");
            String json = objectMapper.writeValueAsString(signUpRequest);
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);
            request.setHeader("Content-Type", "application/json");

            // Отправка запроса и получение ответа
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getCode() == 200) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    JwtAuthenticationResponse jwtResponse = objectMapper.readValue(responseBody, JwtAuthenticationResponse.class);

                    String token = jwtResponse.getToken();

                    System.out.println("Received token: " + token);
                    JSONObject user_dataJson = DateFromToken(token);
                    System.out.println();
                    String emailUser  = user_dataJson.get("email").toString();
                    int idUser =  Integer.parseInt(user_dataJson.get("id").toString());
                    String nameUser = user_dataJson.get("sub").toString();
                    User user = new User( idUser,emailUser,token,nameUser );
                    handleButtonAction(user);

                } else {
                    System.err.println("Registration failed: " + response.getReasonPhrase());
                    // вывод в ui
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
