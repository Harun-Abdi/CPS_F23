import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Handler implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException { //This method handles the http exchange, by checking whether it is a GET or a POST request.


        if ("GET".equals(httpExchange.getRequestMethod())) {
            try {
                getValue(httpExchange);
            } catch (InterruptedException | URISyntaxException e) {
                throw new RuntimeException(e);
            }

        } else if ("POST".equals(httpExchange)) {


        }
        handleResponse(httpExchange); //It then uses both the return value of the way we handled our request, and the http exchange, to execute the method that actually handles the response.
    }


    private void  getValue(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException {

        // smid id på holdene i et array
        // lav en enkel if sætning der tjekker om url har et af de tal der er i arrayet

        int[] teams = {40,50}; // smid alle id'er på holdene her
        String url = httpExchange.getRequestURI().toString(); // get the URL from the request

        String regex = String.join("|", Arrays.stream(teams).mapToObj(String::valueOf).toArray(String[]::new));


        if (url.matches(".*\\b(" + regex + ")\\b.*")) {
            System.out.println("The URL contains a number from the array");
            Pattern pattern = Pattern.compile("\\b(" + regex + ")\\b");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                String team = matcher.group(1);
                System.out.println(team);
           APIController.check(team);

        } else {
            System.out.println("The URL does not contain a number from the array");
        }

        }
    }
    private void handleResponse(HttpExchange httpExchange) throws IOException {
        String encoding = "UTF-8";
        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();


        //htmlBuilder.append(Objects.requireNonNullElse(requestParamValue, "Object/value returned is null"));//This checks to see if requestParamValue is null, and if it is, appends the String instead of the value.

        //String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());//Here we encode the String created as HTML content.

        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); //This line needs documentation, but should really be focused on, as it is a huge security breach.
        httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
        //httpExchange.sendResponseHeaders(200, htmlResponse.length());
        //outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
