import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

public class APIControllerTest {

    @Test
    void testCheck() throws IOException, InterruptedException, URISyntaxException, JSONException {
        // Setup mock HTTP response
        String responseBody = "{\"response\":[{\"league\":{\"standings\":[[{\"rank\":1,\"points\":100,\"team\":{\"name\":\"Team A\",\"id\":123},\"group\":\"A\"}]]}}]}";
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(mockResponse.body()).thenReturn(responseBody);

        // Setup mock HTTP client
        HttpClient mockClient = Mockito.mock(HttpClient.class);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        // Use Mockito to mock the static method call to HttpClient.newHttpClient()
        try (MockedStatic<HttpClient> httpClientMockedStatic = Mockito.mockStatic(HttpClient.class)) {
            httpClientMockedStatic.when(HttpClient::newHttpClient).thenReturn(mockClient);

            // Call APIController.check()
            String team = "123";
            APIController.check(team);

            // Verify that DBController.setData() was called with the expected arguments
            ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> groupCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Integer> seasonCaptor = ArgumentCaptor.forClass(Integer.class);
            ArgumentCaptor<Integer> pointsCaptor = ArgumentCaptor.forClass(Integer.class);
            ArgumentCaptor<Integer> rankCaptor = ArgumentCaptor.forClass(Integer.class);
            Mockito.verify(DBController.class).setData(
                    nameCaptor.capture(),
                    groupCaptor.capture(),
                    seasonCaptor.capture(),
                    pointsCaptor.capture(),
                    rankCaptor.capture()
            );
            assertEquals("Team A", nameCaptor.getValue());
            assertEquals("A", groupCaptor.getValue());
            assertEquals(2019, seasonCaptor.getValue());
            assertEquals(100, pointsCaptor.getValue());
            assertEquals(1, rankCaptor.getValue());
        }
    }
}
