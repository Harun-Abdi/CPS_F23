import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIController {

    static String api_key = "fbdd9b85653960b2412a4465f433454a"; // security risk
    private static final String SERVER_URL = "https://v3.football.api-sports.io/standings?league=39&season=2019&team=";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

    }

   public static void check(String team) throws IOException, InterruptedException, URISyntaxException {
       HttpClient client = HttpClient.newHttpClient();


       HttpRequest request = HttpRequest.newBuilder()
               .uri(new URI(SERVER_URL + team))// + value = GUI
               .header("x-rapidapi-key", api_key)
               .header("x-rapidapi-host", "v3.football.api-sports.io")
               .GET()
               .build();


       HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


       System.out.println(response.body());

       // Split det vi er interessert i:


       
       // Navn på holdet
       // ligaen
       // Årstal
       // point
       // Position i ligaen

       // smid det i database tabel metode klader dbhandler metode
       // få db til at retunerer værdierne til GUI
       // db returner values





   }
    public static String handlePostRequest(String json) throws IOException, JSONException {
//String json = "{\"State\":1,\"Program name\":\"MoveToAssemblyOperation\"}";
        System.out.println("landet");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(SERVER_URL )
                .put(body)
                .build();

        Response response = client.newCall(request).execute();
        //System.out.println(response.body().string());

        String responseBody = response.body().string();
        //String cleanedInput = responseBody.replaceAll("[{}]", "");


        JSONObject jsonObject = new JSONObject(responseBody);

        String teamName = jsonObject.getString("name");
        String liga = jsonObject.getString("group");
        int year = jsonObject.getInt("season");
        int points = jsonObject.getInt("points");
        int position = jsonObject.getInt("rank");

        System.out.println("Team name: " + teamName);
        System.out.println("League" + liga);
        System.out.println("Year: " + year);
        System.out.println("Points: " + points);
        System.out.println("position: " + position);

        //DBController.setData();


        return json;
    }
   }

