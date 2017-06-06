package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBProcessor {
    private Connection connection;

    public DBProcessor() {}

    //creating connection, or finding out that we already have created it (returns connection)
    public Connection getConnection(String URLFIXED, String USERNAME, String PASSWORD) throws SQLException {
        if(connection != null) {
            return connection;
        }else {
            connection = DriverManager.getConnection(URLFIXED, USERNAME, PASSWORD);
            return connection;
        }
    }
}
