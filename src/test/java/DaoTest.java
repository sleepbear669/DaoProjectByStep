import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
/**
 * Created by sleepbear on 2015-04-03.
 */
public class DaoTest {
    private User user = new User("sleepbear", "gom", "gom0119!1");
    private UserDao userDao;
    @Before
    public void setUser() {
        ApplicationContext context = new GenericXmlApplicationContext("Dao.xml");
        userDao = context.getBean("userDao", UserDao.class);
    }

    @Test
    public void testGetAndAddUser() throws Exception {
        userDao.add(user);
        User userGet = userDao.get(user.getId());
        assertThat(userGet.getId(), is(user.getId()));
        assertThat(userGet.getName(), is(user.getName()));
        assertThat(userGet.getPassword(), is(user.getPassword()));
        userDao.delete(userGet.getId());
    }

    @Test
    public void testDaoFactory() throws Exception {
        userDao.add(user);
        User userGet= userDao.get(user.getId());

        assertThat(userGet.getId(), is(user.getId()));
        assertThat(userGet.getName(), is(user.getName()));
        assertThat(userGet.getPassword(), is(user.getPassword()));
        userDao.delete(userGet.getId());
    }

}
