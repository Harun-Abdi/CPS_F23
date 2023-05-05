import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Objects;

public class Handler implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException { //This method handles the http exchange, by checking whether it is a GET or a POST request.
        String requestParamValue = null;

        if ("GET".equals(httpExchange.getRequestMethod())) {
            try {
                requestParamValue = getValue(httpExchange);
            } catch (InterruptedException | URISyntaxException e) {
                throw new RuntimeException(e);
            }

        } else if ("POST".equals(httpExchange)) {


        }
        handleResponse(httpExchange, requestParamValue); //It then uses both the return value of the way we handled our request, and the http exchange, to execute the method that actually handles the response.
    }


    private String  getValue(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException {

        System.out.println(httpExchange.getRequestURI());
        if (httpExchange.getRequestURI().toString().contains("team=40")) {
            String team = "team=40";

            APIController.check(team);
            return "Worked";


        } else
            System.out.println("fejl");
        return "fejl";

    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
        String encoding = "UTF-8";
        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();


        htmlBuilder.append(Objects.requireNonNullElse(requestParamValue, "Object/value returned is null"));//This checks to see if requestParamValue is null, and if it is, appends the String instead of the value.

        String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());//Here we encode the String created as HTML content.

        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); //This line needs documentation, but should really be focused on, as it is a huge security breach.
        httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
