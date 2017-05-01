package desbytes.Repositories;

import desbytes.models.OrderHistory;
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
 * @author Jeff
 */
public class OrderHistoryRepository {
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ServletContext context;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public OrderHistory findOrderById(int id){
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("order_history_queries", "get_order_history.sql");

        return jdbcTemplate.queryForObject(content, new Object[id], new OrderHistoryRowMapper());
    }

    public List<OrderHistory> findOrderByUser(int id)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("order_history_queries", "get_order_history_by_user.sql");


        return jdbcTemplate.query(content, new OrderHistoryRowMapper());
    }

    public class OrderHistoryRowMapper implements RowMapper<OrderHistory> {
        @Override
        public OrderHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderHistory history = new OrderHistory();

            history.setDate(rs.getDate("order_time"));
            history.setStore(new StoreRepository().findStoreById(rs.getInt("store_id")));

            while(rs.next())
            {


            }

            return history;
        }
    }
}
