import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sleepbear on 2015-04-03.
 */
@Configuration
class DaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDao();
    }
    @Bean
    public ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }

}
