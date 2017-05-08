package desbytes.Repositories;

import desbytes.models.App_User;
import desbytes.utils.QueryReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by zanegrasso
 * Created on 5/1/17.
 */
@Repository
public class AppUserRepository {

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public App_User findUserById(int role_id){
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("user_query", "get_user.sql");
        return jdbcTemplate.queryForObject(content,
                new Object[]{role_id}, new AppUserRepository.UserRowMapper());
    }

    // TODO: figure out why this won't work. Maybe something to do with security config?
    public App_User findUserByName(String username) {
        username = "'" + username + "'";
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("user_query", "get_user_by_username.sql");
        content = content + username;
        return jdbcTemplate.queryForObject(content, new UserRowMapper());
    }

    public App_User insertUser(App_User newUser) {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("user_query", "create_new_user.sql");
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator()  {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, newUser.getName());
                ps.setString(2, newUser.getUsername());
                ps.setString(3, newUser.getPassword());
                ps.setString(4, newUser.getPhone());
                ps.setString(5, newUser.getAddress());
                ps.setInt(6, newUser.getRole_id());
                return ps;
            }
        }, holder);

        int newId = holder.getKey().intValue();
        newUser.setId(newId);
        return newUser;
    }

    public class UserRowMapper implements RowMapper<App_User> {
        @Override
        public App_User mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("user_id");
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
