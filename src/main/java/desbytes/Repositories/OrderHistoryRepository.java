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


        return jdbcTemplate.query(content, new Object[]{id}, new OrderHistoryRowMapper());
    }

    public OrderHistory insertOrder(OrderHistory newHistory)
    {
        Grocery_Order newOrder = new Grocery_Order();
        newOrder.setOrder_time(newHistory.getOrder_time());
        newOrder.setStore_id(newHistory.getStore().getStore_id());
        newOrder.setUser_id(newHistory.getUser_id());

        Grocery_Order orderWithId = grocery_orderRepository.insertGrocery_Order(newOrder);

        QueryReader r = new QueryReader();
        String content = r.readQueryFile("order_history_queries", "create_new_order.sql");

        for(Product p : newHistory.getProductList().keySet())
        {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(content);
                    ps.setInt(1, orderWithId.getOrder_id());
                    ps.setDouble(2, p.getProduct_id());
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

            history.setOrder_time(rs.getDate("order_time"));
            history.setStore(storeRespository.findStoreById(rs.getInt("store_id")));

            HashMap<Product, Integer> productList = new HashMap<>();

            do {
                double prodId = rs.getDouble("product_id");
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
