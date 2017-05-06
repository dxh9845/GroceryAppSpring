package desbytes.Repositories;

import desbytes.models.Grocery_Order;
import desbytes.models.OrderHistory;
import desbytes.models.Product;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Jeff
 */
@Repository
public class OrderHistoryRepository {
    @Autowired
    private StoreRepository storeRespository;

    @Autowired
    private Grocery_OrderRepository grocery_orderRepository;

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

        return jdbcTemplate.queryForObject(content, new Object[]{id}, new OrderHistoryRowMapper());
    }

    public List<OrderHistory> findOrderByUser(int id)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("order_history_queries", "get_order_history_by_user.sql");

        List<Grocery_Order> orders = grocery_orderRepository.getOrdersByUser(id);
        List<OrderHistory> history = new ArrayList<OrderHistory>();

        for(Grocery_Order g : orders)
        {
            history.add(findOrderById(g.getOrder_id()));
        }
        return history;
    }

    public OrderHistory insertOrder(OrderHistory newHistory)
    {
        Grocery_Order newOrder = new Grocery_Order();
        newOrder.setOrder_time(newHistory.getOrder_time());
        newOrder.setStore_id(newHistory.getStore_id());
        newOrder.setUser_id(newHistory.getUser_id());

        Grocery_Order orderWithId = grocery_orderRepository.insertGrocery_Order(newOrder);

        QueryReader r = new QueryReader();
        String content = r.readQueryFile("order_history_queries", "insert_order.sql");

        for(Product p : newHistory.getProductList().keySet())
        {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(content);
                    ps.setInt(1, orderWithId.getOrder_id());
                    ps.setString(2, p.getProduct_id());
                    ps.setInt(3, newHistory.getProductList().get(p));
                    return ps;
                }
            });
        }

        return newHistory;
    }

    public class OrderHistoryRowMapper implements RowMapper<OrderHistory> {
        @Override
        public OrderHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderHistory history = new OrderHistory();

            history.setOrder_time(rs.getTimestamp("order_time"));
            history.setStore_id(rs.getInt("store_id"));
            history.setOrder_id(rs.getInt("order_id"));

            HashMap<Product, Integer> productList = new HashMap<>();

            do {
                String prodId = rs.getString("product_id");
                String prodName = rs.getString("name");
                float prodPrice = rs.getFloat("price");
                int qty = rs.getInt("qty");
                Product prod = new Product(prodId, prodName, prodPrice);
                productList.put(prod, qty);
            } while (rs.next());

            history.setProductList(productList);

            return history;
        }
    }
}
