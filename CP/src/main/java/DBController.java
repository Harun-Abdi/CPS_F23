import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

    public class DBController {

        public static Connection c;

        static String user = "root";
        static String password = "Vithe!098";
        static String url = "jdbc:mysql://localhost:3306/Information";

        public static void main(String[] args) {
            getConnection(url, user, password);
           initialiseDatabase();
           initialiseTable();
        }

        /**
         *
         * Database Connection
         */
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

        /**
         * We initialise our database.
         * We also check whether the database already exsits and if it does then we want to use it
         * If not then create a database with that name
         */
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


        /**
         * We create out table
         */
        public static void initialiseTable() {
            String tableName = "stats_table";
            String initializerTable = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n" +
                    "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "teamName VARCHAR(255) NOT NULL UNIQUE,\n" +
                    "liga VARCHAR(255) NOT NULL,\n" +
                    "year INT NOT NULL,\n" +
                    "points INT NOT NULL,\n" +
                    "position INT NOT NULL\n" +
                    ");";

            try {
                Connection connection = DBController.getConnection("jdbc:mysql://localhost:3306/Information", user, password);
                if (!tableExists(connection, tableName)) {
                    connection.prepareStatement(initializerTable).executeUpdate();
                    System.out.println("Table created successfully.");
                } else {
                    System.out.println("Table already exists.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        private static boolean tableExists(Connection connection, String tableName) throws SQLException {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
            return resultSet.next();
        }


        /**
         * Method for inserting data to the database
         */
        public static void setData(String teamName, String liga, int year, int points, int position){
            String statement = "INSERT INTO stats_table (teamName, liga, year, points, position) VALUES ('" + teamName + "', '" + liga + "', '" + year + "', '" + points + "', '" + position + "');";
            try {
                DBController.getConnection("jdbc:mysql://localhost:3306/Information", user, password).createStatement().executeUpdate(statement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Method for pulling the data
         */

        public static String getData() {
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

