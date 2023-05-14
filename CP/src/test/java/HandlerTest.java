import com.sun.net.httpserver.HttpExchange;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class HandlerTest {
    private HttpExchange mockHttpExchange;

    @BeforeEach
    void setUp() {
        mockHttpExchange = Mockito.mock(HttpExchange.class);
    }

    @Test
    void testGetValueWhenUrlContainsNumberFromTeamArray() throws IOException, URISyntaxException, InterruptedException, JSONException {
        when(mockHttpExchange.getRequestURI()).thenReturn(new URI("/teams/40"));

        String expected = "team";
        String actual = Handler.getValue(mockHttpExchange);

        assertEquals(expected, actual);
    }

    @Test
    void testGetValueWhenUrlContainsData() throws IOException, URISyntaxException, InterruptedException, JSONException {
        when(mockHttpExchange.getRequestURI()).thenReturn(new URI("/data"));

        String expected = DBController.getData();
        String actual = Handler.getValue(mockHttpExchange);

        assertEquals(expected, actual);
    }
}
