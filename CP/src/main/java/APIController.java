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



   public static void check(String team) throws IOException, InterruptedException, URISyntaxException, JSONException {

        HttpClient client = HttpClient.newHttpClient();


       HttpRequest request = HttpRequest.newBuilder()
               .uri(new URI(SERVER_URL + team))// + value = GUI
               .header("x-rapidapi-key", api_key)
               .header("x-rapidapi-host", "v3.football.api-sports.io")
               .GET()
               .build();


       HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());



       String responseBody = response.body();

       JSONObject json = new JSONObject(responseBody);

       // nested json

       /*
        However, in the given JSON structure,
        the "name" key is nested inside other objects and arrays.
        To extract the value of "name" correctly, you need to navigate through the JSON structure.
        Here's an updated code snippet that should work for your case:
        */
       String name = json.getJSONArray("response")
               .getJSONObject(0)
               .getJSONObject("league")
               .getJSONArray("standings")
               .getJSONArray(0)
               .getJSONObject(0)
               .getJSONObject("team")
               .getString("name");
       int points = json.getJSONArray("response")
               .getJSONObject(0)
               .getJSONObject("league")
               .getJSONArray("standings")
               .getJSONArray(0)
               .getJSONObject(0)
               .getInt("points");

       String group = json.getJSONArray("response")
               .getJSONObject(0)
               .getJSONObject("league")
               .getJSONArray("standings")
               .getJSONArray(0)
               .getJSONObject(0)
               .getString("group");

       int season = json.getJSONArray("response")
               .getJSONObject(0)
               .getJSONObject("league")
               .getInt("season");

       int rank = json.getJSONArray("response")
               .getJSONObject(0)
               .getJSONObject("league")
               .getJSONArray("standings")
               .getJSONArray(0)
               .getJSONObject(0)
               .getInt("rank");

       System.out.println(name + " " + group + " " +  points + " " + season + " " + rank);
       DBController.setData(name,group,season,points,rank);

   }
}

