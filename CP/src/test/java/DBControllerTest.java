import org.json.JSONException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DBControllerTest {

    private static Connection connection;
    private static final String USER = "root";
    private static final String PASSWORD = "Vithe!098";
    private static final String URL = "jdbc:mysql://localhost:3306/Information";

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void testSetAndGet() throws JSONException {
        // inserting data in the database
        DBController.setData("team1", "liga1", 2022, 10, 1);
        DBController.setData("team2", "liga2", 2022, 8, 2);

        // retrieving the data from database
        String result = DBController.getData();

        // see if the retrieved data is as expected
        String expected = "1, team1, liga1, 2022, 10\n2, team2, liga2, 2022, 8\n";
        assertEquals(expected, result);
    }

    @Test
    public void testInitialiseTable() throws SQLException {
        // drop the table if it exists
        Statement dropStatement = connection.createStatement();
        dropStatement.executeUpdate("DROP TABLE IF EXISTS stats_table");

        // call initialiseTable
        DBController.initialiseTable();

        // check if the table exists
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW TABLES LIKE 'stats_table'");
        boolean tableExists = resultSet.next();
        assertTrue(tableExists);
    }

}
