import java.sql.*;

    public class DBController {

        public static Connection c;

        public static void main(String[] args) {
            initialiseTable();
        }

        //DB connection
        public static Connection getConnection(String url, String user, String password) {
            try {
                if (c == null) {
                    Class.forName("com.mysql.jdbc.Driver");
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

            Connection connection = DBController.getConnection("jdbc:mysql://localhost:3306/", "***", "***");

            String checkDatabase = "SHOW DATABASES LIKE 'backlog';";
            String useDatabase = "USE backlog";
            String initializeDatabase = "CREATE DATABASE backlog";
            try {
                ResultSet resultSet = connection.prepareStatement(checkDatabase).executeQuery();
                if (resultSet.next()) {
                    try {
                        DBController.getConnection("jdbc:mysql://localhost:3306/", "***", "***").prepareStatement(useDatabase).executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        DBController.getConnection("jdbc:mysql://localhost:3306/", "***", "***").prepareStatement(initializeDatabase).executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        //Laver tabel
        public static void initialiseTable() {
            String initializerTable = "CREATE TABLE brewer_backlog (batch_id int not null, time_started TIMESTAMP DEFAULT CURRENT_TIMESTAMP, product_id int NOT NULL, initial_speed int NOT NULL, amount_for_prod INT NOT NULL);";
            try {
                DBController.getConnection("jdbc:mysql://localhost:3306/backlog", "***", "***").prepareStatement(initializerTable).executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // Send data TO Database
        public static void setData(int batchId, int productId, int initialSpeed, int amountForProd){
            String statement = "INSERT INTO brewer_backlog (batch_id, product_id, initial_speed, amount_for_prod) VALUES ('" + batchId + "', '" + productId + "', '" + initialSpeed + "', '" + amountForProd + "');";
            try {
                DBController.getConnection("jdbc:mysql://localhost:3306/backlog", "***", "***").createStatement().executeUpdate(statement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public static void resetDatabase(){
            String statement = "DROP TABLE brewer_backlog;";
            try {
                DBController.getConnection("jdbc:mysql://localhost:3306/backlog", "***", "***").createStatement().executeUpdate(statement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Her skal GUI tage fra
        public static String getData() {
            PreparedStatement ps = null;
            ResultSet rs = null;
            StringBuilder sb = new StringBuilder();
            String statement = "SELECT * FROM brewer_backlog ORDER BY time_started ASC LIMIT 10;";

            try {
                ps = getConnection("jdbc:mysql://localhost:3306/backlog", "***", "***").prepareStatement(statement);
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

