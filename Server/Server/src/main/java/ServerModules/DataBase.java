package ServerModules;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "17Dima03";
    private static Connection connection;
    @Getter
    private static Statement statement;
    private static final Logger logger = LogManager.getLogger();

    public DataBase(String userName) {
        connection();
    }

    private void connection(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");

        }catch (SQLException e) {
            System.err.println("Connection Failed");
        }
    }
}
