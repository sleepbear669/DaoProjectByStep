import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
/**
 * Created by sleepbear on 2015-04-03.
 */
public class DaoTest {
    private User user;
    private User nUser;

    @Before
    public void setUser() {
        user = new User("sleepbear", "gom", "gom0119!1");
        nUser = new User("n", "gom", "gom0119!!");
    }

    @Test(expected = SQLException.class)
    public void testDeleteUser() throws Exception {
        UserDao userDao = new UserDao(new NConnectionMaker());
        userDao.delete(user.getId());
        assertNull(userDao.get(user.getId()));
    }

    @Test
    public void testGetAndAddUser() throws Exception {
        UserDao userDao = new UserDao(new NConnectionMaker());

        userDao.add(user);
        User userGet = userDao.get(user.getId());

        assertThat(userGet.getId(), is(user.getId()));
        assertThat(userGet.getName(), is(user.getName()));
        assertThat(userGet.getPassword(), is(user.getPassword()));

    }

    @Test
    public void testMakeNUserDao() throws Exception {
        UserDao userDao = new UserDao(new NConnectionMaker());
        userDao.add(nUser);

        User user = userDao.get(nUser.getId());

        assertThat(user.getId(), is(nUser.getId()));
        assertThat(user.getName(), is(nUser.getName()));
        assertThat(user.getPassword(), is(nUser.getPassword()));

        userDao.delete(user.getId());
    }

    public class UserDao {
        private ConnectionMaker connectionMaker;

        public UserDao(ConnectionMaker connectionMaker) {
            this.connectionMaker = connectionMaker;
        }

        public User get(String id) throws ClassNotFoundException, SQLException {
            Connection connection = connectionMaker.makeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return user;
        }

        public void add(User user) throws ClassNotFoundException, SQLException {
            Connection connection = connectionMaker.makeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(id, name, password) VALUES (? , ?, ?)");
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }

        public void delete(String id) throws ClassNotFoundException, SQLException {
            Connection connection = connectionMaker.makeConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
    }

    private class NConnectionMaker implements ConnectionMaker {
        public Connection makeConnection() throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/spring", "root", "gom0119!1");
            return connection;
        }
    }
}
