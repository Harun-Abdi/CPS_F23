import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HandlerTest {

    private HttpExchange mockHttpExchange;

    @BeforeEach
    void setUp() {
        mockHttpExchange = mock(HttpExchange.class);
    }

    /**
     * Test whether the getValue method returns "team" when the URL contains a number from the teams array.
     */
    @Test
    void testGetValueWhenUrlContainsNumberFromTeamArray() throws Exception {
        // Mock the request URI
        when(mockHttpExchange.getRequestURI()).thenReturn(new URI("/teams/40"));

        // Expected return value
        String expected = "team";

        // Call the getValue method
        String actual = Handler.getValue(mockHttpExchange);

        // Assert the expected return value is equal to the actual return value
        assertEquals(expected, actual);
    }

    /**
     * Test whether the getValue method returns data from the DBController when the URL contains "data".
     */
    @Test
    void testGetValueWhenUrlContainsData() throws Exception {
        // Mock the request URI
        when(mockHttpExchange.getRequestURI()).thenReturn(new URI("/data"));

        // Expected return value
        String expected = DBController.getData();

        // Call the getValue method
        String actual = Handler.getValue(mockHttpExchange);

        // Assert the expected return value is equal to the actual return value
        assertEquals(expected, actual);
    }
}
