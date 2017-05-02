package desbytes.Repositories;

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

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * A store repository that uses our queries to run against the DB.
 * @author Daniel
 */
@Repository
public class ProductRepository {

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<Product> findTopProducts(int numOfRows, int currentPage) {
        QueryReader reader = new QueryReader();
        // We are on this page with this many products on it, so get the offset accordingly
        int offset = currentPage * numOfRows;
        String content = reader.readQueryFile("product_queries", "select_top_products.sql");
            return jdbcTemplate.query(content, new Object[]{numOfRows, offset},
                new ProductRowMapper());
    }

    public Product findProductById(String id) {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("product_queries", "get_product.sql");
        return jdbcTemplate.queryForObject(content,
                new Object[]{id}, new ProductRowMapper());
    }

    public List<Product> searchProducts(String searchQuery) {
        // Lowercase query and add %
        searchQuery = searchQuery.toLowerCase();
        searchQuery = '%' + searchQuery + '%';
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("product_queries", "search_products.sql" );
        return jdbcTemplate.query(content,
                new Object[]{searchQuery}, new ProductRowMapper());
    }

    public Product insertProduct(Product newProduct) {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("product_queries", "create_new_product.sql");
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content);
                ps.setString(0, newProduct.getProduct_id());
                ps.setString(1, newProduct.getName());
                ps.setFloat(2, newProduct.getPrice());
                return ps;
            }
        });

        return newProduct;
    }

    public Product updateProduct(Product updatedProduct) {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("product_queries", "update_new_store.sql");
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content);
                ps.setString(0, updatedProduct.getName());
                ps.setFloat(1, updatedProduct.getPrice());
                ps.setString(2, updatedProduct.getProduct_id());
                return ps;
            }
        });

        return updatedProduct;
    }

    public class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            String product_id = rs.getString("product_id");
            String store_name = rs.getString("name");
            float price = rs.getFloat("price");
            return new Product(product_id, store_name, price);
        }
    }

}
