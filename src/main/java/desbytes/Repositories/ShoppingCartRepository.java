package desbytes.Repositories;
import com.oracle.javafx.jmx.SGMXBeanImpl;
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

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import javax.sql.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zanegrasso
 * Created on 4/27/17.
 */
public class ShoppingCartRepository {

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public Shopping_Cart getShoppingCartByID(int id)
    {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("queries", "get_shopping_cart.sql");
        return jdbcTemplate.queryForObject(content, new Object[]{id}, new ShoppingCartRowMapper());
    }

    public class ShoppingCartRowMapper implements RowMapper<Shopping_Cart> {
        @Override
        public Shopping_Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new CustomerRepository().findCustomerByID(rs.getInt("user_id"));
            HashMap<Product, Integer> productList = new ProductRepository().findProductByID(rs.getArray("product_id"));

            return new Shopping_Cart(customer,productList);
        }
    }
}
