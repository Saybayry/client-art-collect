package com.example.client;

import com.example.client.model.ArtMovement;
import com.example.client.model.Artist;
import com.example.client.model.Genre;
import com.example.client.model.Technique;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.util.*;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class UploadArtForm {

    @FXML
    public TextField GenreTextField;
    @FXML
    public TextField TechniqueTextField1;
    @FXML
    public TextField ArtMovementTextField;
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField sizeTextField;

    @FXML
    private TextField tagsTextField;

    @FXML
    private DatePicker creationDatePicker;
    private File selectedFile;

    @FXML
    private ImageView imagePreview;




    @FXML
    private ChoiceBox<Genre> genreIdChoice;
    @FXML
    private ChoiceBox<ArtMovement> artMovementdChoice;
    @FXML
    public ChoiceBox<Artist> artistChoice;
    @FXML
    public ChoiceBox<Technique> techniqueChoice;

    @FXML
    public TextField otherNameArtistTextField;
    @FXML
    public TextField lastNameArtistTextField;
    @FXML
    public TextField firstNameArtistTextField;
    @FXML
    public DatePicker artistDateofBirthDatePicker;


    public void  submitArtist(){
        Date creationDate = Date.from(artistDateofBirthDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String firstName = firstNameArtistTextField.getText();
        String lastName = lastNameArtistTextField.getText();
        String otherName = otherNameArtistTextField.getText();
        String formattedCreationDate = sdf.format(creationDate);

        // Создаем JSON-объект
        JSONObject json = new JSONObject();
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("otherName", otherName);
        json.put("dateOfBirth", formattedCreationDate);

        // Создаем HTTP-клиент
        HttpClient client = HttpClient.newHttpClient();

        // Создаем HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/artist"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        // Отправляем HTTP-запрос и обрабатываем ответ
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });

        setChoiseBox();
        firstNameArtistTextField.setText("");
        lastNameArtistTextField.setText("");
        otherNameArtistTextField.setText("");
        artistDateofBirthDatePicker.setValue(null);

    }
    public void submitArtMovement(ActionEvent actionEvent) {
        String ArtMovementName = ArtMovementTextField.getText();
        JSONObject json = new JSONObject();
        json.put("name", ArtMovementName);

        // Создаем HTTP-клиент
        HttpClient client = HttpClient.newHttpClient();

        // Создаем HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/art_movement"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        // Отправляем HTTP-запрос и обрабатываем ответ
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
        ArtMovementTextField.setText("");
        setChoiseBox();
    }

    public void submitTechnique(ActionEvent actionEvent) {
        String TechniqueName = TechniqueTextField1.getText();
        JSONObject json = new JSONObject();
        json.put("name", TechniqueName);

        // Создаем HTTP-клиент
        HttpClient client = HttpClient.newHttpClient();

        // Создаем HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/technique"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        // Отправляем HTTP-запрос и обрабатываем ответ
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
        TechniqueTextField1.setText("");
        setChoiseBox();
    }

    public void submitGenre(ActionEvent actionEvent) {

        String generName = GenreTextField.getText();
        JSONObject json = new JSONObject();
        json.put("name", generName);

        // Создаем HTTP-клиент
        HttpClient client = HttpClient.newHttpClient();

        // Создаем HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/genre"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        // Отправляем HTTP-запрос и обрабатываем ответ
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
        GenreTextField.setText("");
        setChoiseBox();
    }









    private ObservableList<Genre> genres = FXCollections.observableArrayList();
    private ObservableList<ArtMovement> artMovements = FXCollections.observableArrayList();
    private ObservableList<Artist> artits = FXCollections.observableArrayList();
    private ObservableList<Technique> techniques = FXCollections.observableArrayList();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void get_requestGenre() {
        String urlGenre = "http://localhost:8080/genre";
        String urlArtMovement = "http://localhost:8080/art_movement";
        String urlArtist = "http://localhost:8080/artist";
        String urlTechnique = "http://localhost:8080/technique";
        try {
            genres.clear();
            artMovements.clear();
            artits.clear();
            techniques.clear();

            JSONArray jsonArrayGenre = mainController.fetchJsonArray(urlGenre);
            for (int i = 0; i < jsonArrayGenre.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArrayGenre.getJSONObject(i);
                    Long id = jsonObject.getLong("id");
                    String name = jsonObject.getString("name");
                    genres.add(new Genre(id, name));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            JSONArray jsonArrayArtist = mainController.fetchJsonArray(urlArtist);
            for (int i = 0; i < jsonArrayArtist.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArrayArtist.getJSONObject(i);

                    Long id = jsonObject.getLong("id");
                    String firstName = jsonObject.getString("firstName");
                    String lastName = jsonObject.getString("lastName");
                    String otherName = jsonObject.getString("otherName");
                    Date dateOfBirth = dateFormat.parse(jsonObject.getString("dateOfBirth") ) ;

                    artits.add(new Artist( id ,firstName, lastName,  otherName, dateOfBirth));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            JSONArray jsonArrayArtMovement = mainController.fetchJsonArray(urlArtMovement);
            for (int i = 0; i < jsonArrayArtMovement.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArrayArtMovement.getJSONObject(i);
                    Long id = jsonObject.getLong("id");
                    String name = jsonObject.getString("name");
                    artMovements.add(new ArtMovement(id, name));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            JSONArray jsonArrayTechnique = mainController.fetchJsonArray(urlTechnique);
            for (int i = 0; i < jsonArrayTechnique.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArrayTechnique.getJSONObject(i);
                    Long id = jsonObject.getLong("id");
                    String name = jsonObject.getString("name");
                    techniques.add(new Technique(id, name));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void setChoiseBox(){
        get_requestGenre();
//        ObservableList<String> genres = FXCollections.observableArrayList("Genre 1", "Genre 2", "Genre 3");
        genreIdChoice.setItems(genres);
        artMovementdChoice.setItems(artMovements);
        artistChoice.setItems(artits);
        techniqueChoice.setItems(techniques);

    }








    @FXML
    private void initialize() {

        // Инициализация вашего контроллера
        if (mainController == null) {
            System.err.println("MainController is not initialized!");
        } else {
            // Инициализация компонента
            mainController.get_request();
        }


    }





    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        selectedFile = fileChooser.showOpenDialog(new Stage());
        Image image = new Image(selectedFile.toURI().toString());
        imagePreview.setImage(image);
    }

    @FXML
    private void submit() throws IOException, URISyntaxException, InterruptedException {
        // Получаем данные из полей формы
        String name = nameTextField.getText();
        Long genreId = genreIdChoice.getValue().getId();
//        Long genreId = Long.parseLong(genreIdTextField.getText());
        String artMovementId = artMovementdChoice.getValue().getId().toString(); //artMovementIdTextField.getText();
        String techniqueId = techniqueChoice.getValue().getId().toString();//techniqueIdTextField.getText();
        String artistId = artistChoice.getValue().getId().toString(); //artistIdTextField.getText();
        Date creationDate = Date.from(creationDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        File sendFile = selectedFile;
        String size = sizeTextField.getText();
        String tags = tagsTextField.getText();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedCreationDate = sdf.format(creationDate);


        sendMultipartRequest("http://localhost:8080/arts/arts/save", name,genreId ,artMovementId , techniqueId,artistId,formattedCreationDate,size,tags,sendFile);
        mainController.updateMyCollect();
    }

    private CustomTabMenuController mainController;
    public void setMainController(CustomTabMenuController mainController, String token) {
        this.mainController = mainController;
        this.token =token;
        setChoiseBox();
    }


private String token;
//
public void sendMultipartRequest(String url, String name, Long genreId, String artMovementId,
                                        String techniqueId, String artistId, String creationDate,
                                        String size, String tags, File file) throws IOException,
        InterruptedException, URISyntaxException {
    String boundary = "-------------oiawn4tp89n4e9p5";

    Map<String, Object> data = new HashMap<>();
    data.put("name", name);
    data.put("genreId", genreId);
    data.put("artMovementId", artMovementId);
    data.put("techniqueId", techniqueId);
    data.put("artistId", artistId);
    data.put("creationDate", creationDate);
    data.put("size", size);
    data.put("tags", tags);
    data.put("image", file);

    HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(url))
            .headers("Content-Type", "multipart/form-data; boundary=" + boundary)
            .header("Authorization", "Bearer " + token)
            .POST(ofMimeMultipartData(data, boundary))
            .build();

    HttpClient client = HttpClient.newBuilder()
            .connectTimeout(java.time.Duration.ofSeconds(10L))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println("Response status code = " + response.statusCode());
    System.out.println("Response body = " + response.body());
}

    public static HttpRequest.BodyPublisher ofMimeMultipartData(Map<String, Object> data, String boundary) throws IOException {
        var byteArrays = new ArrayList<byte[]>();
        byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(StandardCharsets.UTF_8);

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            byteArrays.add(separator);

            if (entry.getValue() instanceof File) {
                var file = (File) entry.getValue();
                Path path = file.toPath();
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + file.getName() + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + "\r\n").getBytes(StandardCharsets.UTF_8));
            }
        }

        byteArrays.add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }



    public void upload(String name, String genreId, String artMovementId, String techniqueId,
                              String artistId, Date creationDate, String size, String tags, Path selectedFilePath) {

        try {
            // Устанавливаем параметры HTTP клиента
            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            // Загружаем содержимое файла в байтовый массив
            byte[] fileContent = Files.readAllBytes(selectedFilePath);

            // Формируем разделитель для multipart формата
            String boundary = "----Boundary" + System.currentTimeMillis();


//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String formattedDate = dateFormat.format(creationDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedCreationDate = sdf.format(creationDate);

            // Формируем содержимое формы в виде строки
            String formData = "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"name\"\r\n\r\n" + name + "\r\n" +
                    "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"genreId\"\r\n\r\n" + genreId + "\r\n" +
                    "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"artMovementId\"\r\n\r\n" + artMovementId + "\r\n" +
                    "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"techniqueId\"\r\n\r\n" + techniqueId + "\r\n" +
                    "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"artistId\"\r\n\r\n" + artistId + "\r\n" +
                    "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"creationDate\"\r\n\r\n" + formattedCreationDate + "\r\n" +
                    "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"size\"\r\n\r\n" + size + "\r\n" +
                    "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"tags\"\r\n\r\n" + tags + "\r\n" +
                    "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"image\"; filename=\"" + selectedFilePath.getFileName() + "\"\r\n" +
                    "Content-Type: application/octet-stream\r\n\r\n ";

            // Формируем тело запроса, объединяя содержимое формы и содержимое файла
            byte[] requestBody = formData.getBytes();
            byte[] boundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes();
            byte[] fileBytes = Files.readAllBytes(selectedFilePath);

            // Создаем массив байтов для всего тела запроса
            byte[] multipartBody = new byte[requestBody.length + fileBytes.length + boundaryBytes.length];
            System.arraycopy(requestBody, 0, multipartBody, 0, requestBody.length);
            System.arraycopy(fileBytes, 0, multipartBody, requestBody.length, fileBytes.length);
            System.arraycopy(boundaryBytes, 0, multipartBody, requestBody.length + fileBytes.length, boundaryBytes.length);

            // Создаем запрос HTTP POST с телом multipart формата
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/arts/arts/save"))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody))
                    .build();

            // Отправляем запрос и получаем ответ
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Выводим информацию о ответе
            System.out.println("Response code: " + response.statusCode());
            System.out.println("Response body: " + response.body());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


}
