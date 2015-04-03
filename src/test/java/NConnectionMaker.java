import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
* Created by sleepbear on 2015-04-03.
*/
class NConnectionMaker implements ConnectionMaker {
    public Connection makeConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/spring", "root", "gom0119!1");
        return connection;
    }
}
