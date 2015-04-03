import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by sleepbear on 2015-04-03.
 */
public interface ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
