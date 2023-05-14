import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

    public class DBController {

        public static Connection c;

        static String user = "root";
        static String password = "Vithe!098";
        static String url = "jdbc:mysql://localhost:3306/";

        public static void main(String[] args) {
            getConnection(url, user, password);
           initialiseDatabase();
           initialiseTable();
        }

        //DB connection
        public static Connection getConnection(String url, String user, String password) {
            try {
                if (c == null) {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    c = DriverManager.getConnection(url, user, password);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return c;
        }

        // Laver databasen
        // Tjekker om databasen findes, hvis ja så brug samme database, hvis nej så lav en database med det navn
        public static void initialiseDatabase() {
            Connection connection = getConnection("jdbc:mysql://localhost:3306/", user, password);

            String checkDatabase = "SHOW DATABASES LIKE 'Information';";
            String useDatabase = "USE Information";
            String initializeDatabase = "CREATE DATABASE Information";
            try {
                if (connection != null) { // check if connection is not null
                    ResultSet resultSet = connection.prepareStatement(checkDatabase).executeQuery();
                    if (resultSet.next()) {
                        try {
                            connection.prepareStatement(useDatabase).executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            connection.prepareStatement(initializeDatabase).executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    System.out.println("Connection is null");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        //Laver tabel
        // Kun lav tabel hvis den ikke findes
        public static void initialiseTable() {
            String initializerTable = "CREATE TABLE stats_table (\n" +
                    "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "teamName VARCHAR(255) NOT NULL UNIQUE,\n" +
                    "liga VARCHAR(255) NOT NULL,\n" +
                    "year INT NOT NULL,\n" +
                    "points INT NOT NULL,\n" +
                    "position INT NOT NULL\n" +
                    ");";
            try {
                DBController.getConnection("jdbc:mysql://localhost:3306/Information", user, password).prepareStatement(initializerTable).executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // Send data TO Database
        // Hvis dataen allerede eksiterer i db så lad hver med at smide den ind
        public static void setData(String teamName, String liga, int year, int points, int position){
            String statement = "INSERT INTO stats_table (teamName, liga, year, points, position) VALUES ('" + teamName + "', '" + liga + "', '" + year + "', '" + points + "', '" + position + "');";
            try {
                DBController.getConnection("jdbc:mysql://localhost:3306/Information", user, password).createStatement().executeUpdate(statement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        /*
        public static void resetDatabase(){
            String statement = "DROP TABLE brewer_backlog;";
            try {
                DBController.getConnection("jdbc:mysql://localhost:3306/Information", "***", "***").createStatement().executeUpdate(statement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
*/
        // Her skal GUI tage fra

        public static String getData() throws JSONException {
            PreparedStatement ps = null;
            ResultSet rs = null;
            StringBuilder sb = new StringBuilder();
            String statement = "SELECT * FROM stats_table;";


            try {
                ps = getConnection("jdbc:mysql://localhost:3306/Information", user, password).prepareStatement(statement);
                rs = ps.executeQuery();
                while(rs.next()){
                    sb.append(rs.getString(1)).append(", ").append(rs.getString(2)).append(", ").append(rs.getString(3)).append(", ").append(rs.getString(4)).append(", ").append(rs.getString(5)).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


           return sb.toString();
        }


    }

