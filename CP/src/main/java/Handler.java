import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Handler implements HttpHandler {

    /**
     *
     * @param httpExchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     *     This method handles the http exchange, by checking whether if it is a GET request
     */

    public void handle(HttpExchange httpExchange) throws IOException {

        String requestParamValue = null;

        if ("GET".equals(httpExchange.getRequestMethod())) {
            try {
                requestParamValue = getValue(httpExchange);
            } catch (InterruptedException | URISyntaxException | JSONException e) {
                throw new RuntimeException(e);
            }

        } else if ("POST".equals(httpExchange)) {


        }
        handleResponse(httpExchange, requestParamValue); //It then uses both the return value of the way we handled our request, and the http exchange, to execute the method that actually handles the response.
    }


    /**
     The functionality of the following lines of code is to firstly
     take the request URL and split it up and check whether the url contains a number in the teams array.
     If it does then call the check method in api controller.
     Else if it does not check whether it contains data, if it does then call the getData method in the DBController class

     */
    static String getValue(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException, JSONException {


        int[] teams = {40,50,33,49,46,47,39
                        ,42,62,44,41,45,34,52,
                        51,48,66,35,38,71};



        String url = httpExchange.getRequestURI().toString();

        String regex = String.join("|", Arrays.stream(teams).mapToObj(String::valueOf).toArray(String[]::new));


        if (url.matches(".*\\b(" + regex + ")\\b.*")) {
            System.out.println("The URL contains a number from the array");
            Pattern pattern = Pattern.compile("\\b(" + regex + ")\\b");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                String team = matcher.group(1);
                System.out.println(team);
                APIController.check(team);

                return "team";

        }

        } else if( httpExchange.getRequestURI().toString().contains("data")){
            System.out.println("data if sætning");

            return DBController.getData();
        }
        else {
            System.out.println("The URL does not contain a number from the array");

            return "fault";
        }
        return "fault 2";

    }

    /**
     This methods checks to see if requestParamValue is null, and if it is, appends the String instead of the value.
     Encode the String created as HTML content.
     We then set the "Access-Control-Allow-Origin to *, which allows any domain to access the resource

     */

    private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
        String encoding = "UTF-8";
        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();


        htmlBuilder.append(Objects.requireNonNullElse(requestParamValue, "Object/value returned is null"));

        String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());

        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
        httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
