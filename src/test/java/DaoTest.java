import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
/**
 * Created by sleepbear on 2015-04-03.
 */
public class DaoTest {
    private User user;
    @Before
    public void setUser() {
        user = new User("sleepbear", "gom", "gom0119!1");
    }

    @Test
    public void testGetAndAddUser() throws Exception {
        UserDao userDao = new UserDao();
        User user = new User();
        String id = String.valueOf(new Random().nextInt()/100);
        String name = "gom";
        String password = "gom0119!1";
        user.setId(id);
        user.setName(name);
        user.setPassword(password);

        userDao.add(user);

        User userGet = userDao.get(id);

        assertThat( userGet.getId(), is( user.getId() ));
        assertThat( userGet.getName(), is(user.getName() ));
        assertThat(userGet.getPassword(), is(user.getPassword()));

    }

    @Test(expected = SQLException.class)
    public void testDeleteUser() throws Exception {
        UserDao userDao = new UserDao();
        User user = new User("test1", "gom", "gom0119!1");
        userDao.add(user);
        userDao.delete(user.getId());

        assertNull(userDao.get(user.getId()));
    }

    private class UserDao {

        public User get(String id) throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/spring", "root", "gom0119!1");
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
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/spring", "root", "gom0119!1");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(id, name, password) VALUES (? , ?, ?)");
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }

        public void delete(String id) throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/spring", "root", "gom0119!1");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        }
    }
}
