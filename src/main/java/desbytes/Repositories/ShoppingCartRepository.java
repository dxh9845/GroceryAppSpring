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
                double prodId = rs.getDouble("product_id");
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
