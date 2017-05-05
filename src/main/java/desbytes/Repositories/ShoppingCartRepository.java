package desbytes.Repositories;

import desbytes.models.Customer;
import desbytes.models.Product;
import desbytes.models.Shopping_Cart;
import desbytes.utils.QueryReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import javax.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zanegrasso
 * Created on 4/27/17.
 */
@Repository
public class ShoppingCartRepository {

    @Autowired
    private CustomerRepository customerRepository;

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Shopping_Cart getShoppingCartByID(int id)
    {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("shopping_cart_queries", "get_shopping_cart.sql");
        return jdbcTemplate.queryForObject(content, new Object[]{id}, new ShoppingCartRowMapper(id));
    }

    public void addProductToCart(int user_id, String product_id, int qty)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("shopping_cart_queries", "add_product.sql");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content);
                ps.setInt(1, user_id);
                ps.setString(2, product_id);
                ps.setInt(3, qty);
                return ps;
            }
        });
    }

    public void updateShoppingCart(int user_id, String product_id, int qty)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("shopping_cart_queries", "update_shopping_cart.sql");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content);
                ps.setInt(2, user_id);
                ps.setString(3, product_id);
                ps.setInt(1, qty);
                return ps;
            }
        });
    }

    public void deleteShoppingCart(int user_id, String product_id)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("shopping_cart_queries", "delete_shopping_cart.sql");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content);
                ps.setInt(1, user_id);
                ps.setString(2, product_id);
                return ps;
            }
        });
    }


    public class ShoppingCartRowMapper implements RowMapper<Shopping_Cart> {
        private int customerId;

        public ShoppingCartRowMapper(int customerId) {
            this.customerId = customerId;
        }

        @Override
        public Shopping_Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = customerRepository.findCustomerByID(customerId);

            HashMap<Product, Integer> productList = new HashMap<>();
            do {
                String prodId = rs.getString("product_id");
                String prodName = rs.getString("name");
                float prodPrice = rs.getFloat("price");
                int qty = rs.getInt("qty");
                Product prod = new Product(prodId, prodName, prodPrice);
                productList.put(prod, qty);
            } while (rs.next());

            return new Shopping_Cart(customer, productList);

        }
    }
}
