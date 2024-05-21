package rs.raf.student.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;

public class MySQLAbstractRepository {

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String SCHEME = "public_blog";
    private static final String USERNAME = "Nemanja";
    private static final String PASSWORD = "123456788";

    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException exception) { exception.printStackTrace(System.err); }
    }

    protected Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                MessageFormat.format("jdbc:mysql://{0}:{1}/{2}", HOST, PORT, SCHEME),
                USERNAME,
                PASSWORD
        );
    }

}
