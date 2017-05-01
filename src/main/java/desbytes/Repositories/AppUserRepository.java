package desbytes.Repositories;

import desbytes.models.App_User;
import desbytes.models.Customer;
import desbytes.utils.QueryReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zanegrasso
 * Created on 5/1/17.
 */
public class AppUserRepository {

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public App_User findUserById(int role_id){
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("App_User_query", "get_user.sql");
        return jdbcTemplate.queryForObject(content,
                new Object[]{role_id}, new AppUserRepository.UserRowMapper());
    }

    public class UserRowMapper implements RowMapper<App_User> {
        @Override
        public App_User mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String phone = rs.getString("phone");
            String address = rs.getString("address");
            int role_id = rs.getInt("role_id");
            return new App_User(id, username, name, password, phone, address, role_id);
        }
    }
}
