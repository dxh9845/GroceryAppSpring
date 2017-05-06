package desbytes.Repositories;

import desbytes.models.Grocery_Order;
import desbytes.utils.QueryReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * A store repository that uses our queries to run against the DB.
 * @author Jeff
 */
@Repository
public class Grocery_OrderRepository {

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ServletContext context;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Grocery_Order insertGrocery_Order(Grocery_Order newOrder)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("grocery_order_queries", "create_new_grocery_order.sql");
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content, Statement.RETURN_GENERATED_KEYS);
                ps.setTimestamp(1, newOrder.getOrder_time());
                ps.setInt(2, newOrder.getStore_id());
                ps.setInt(3, newOrder.getUser_id());
                return ps;
            }
        }, holder);

        int newOrderId = holder.getKey().intValue();
        newOrder.setOrder_id(newOrderId);
        return newOrder;
    }

    public Grocery_Order getGrocery_Order(int id)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("grocery_order_queries", "get_grocery_order.sql");

        return jdbcTemplate.queryForObject(content,
                new Object[]{id}, new Grocery_OrderRowMapper());
    }

    public List<Grocery_Order> getOrdersByUser(int id)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("grocery_order_queries", "get_grocery_order_by_user.sql");

        return jdbcTemplate.query(content,
                new Object[]{id}, new Grocery_OrderRowMapper());
    }


    public class Grocery_OrderRowMapper implements RowMapper<Grocery_Order> {
        @Override
        public Grocery_Order mapRow(ResultSet rs, int rowNum) throws SQLException {

            int order_id = rs.getInt("order_id");
            Timestamp order_time = rs.getTimestamp("order_time");
            int store_id = rs.getInt("store_id");
            int user_id = rs.getInt("user_id");
            return new Grocery_Order(order_id, order_time, store_id, user_id);
        }
    }
}
