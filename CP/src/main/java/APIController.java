import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIController {

    static String api_key = "fbdd9b85653960b2412a4465f433454a"; // security risk

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

    }

   public static void check(String team) throws IOException, InterruptedException, URISyntaxException {
       HttpClient client = HttpClient.newHttpClient();


       HttpRequest request = HttpRequest.newBuilder()
               .uri(new URI("https://v3.football.api-sports.io/standings?league=39&season=2019&" + team))// + value = GUI
               .header("x-rapidapi-key", api_key)
               .header("x-rapidapi-host", "v3.football.api-sports.io")
               .GET()
               .build();


       HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


       System.out.println(response.body());

       // Split det vi er interessert i;

       // Navn på holdet
       // ligaen
       // Årstal
       // point
       // Position i ligaen

       // smid det i database tabel metode klader dbhandler metode
       // db returner values






   }
   }

