package desbytes.Repositories;

import desbytes.models.Inventory;
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
import java.util.List;

/**
 * A store repository that uses our queries to run against the DB.
 * @author Jeff
 */
@Repository
public class InventoryRepository {

    @Autowired
    private ProductRepository productRepository;

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ServletContext context;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<Inventory> getStoreInventory(int storeId) {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("inventory_queries", "get_store_inventory.sql");
        return jdbcTemplate.query(content, new Object[]{storeId}, new InventoryRowMapper());
    }

    public List<Inventory> searchStoreInventory(int storeId, String storeQuery) {
        storeQuery = storeQuery.toLowerCase();
        storeQuery = '%' + storeQuery + '%';
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("inventory_queries", "search_store_inventory.sql");
        return jdbcTemplate.query(content, new Object[]{storeQuery, storeId}, new InventoryRowMapper());
    }

    public Inventory getItemFromStore(int store_id, String product_id)
    {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("inventory_queries", "get_store_product.sql");

        return jdbcTemplate.queryForObject(content, new Object[]{store_id, product_id}, new InventoryRowMapper());
    }

    public Inventory insertInventory(Inventory newInventory)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("inventory_queries", "create_new_inventory.sql");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content);
                ps.setString(0, newInventory.getProduct_id());
                ps.setInt(1, newInventory.getStore_id());
                ps.setInt(2, newInventory.getQty());
                ps.setInt(3, newInventory.getAisle());
                return ps;
            }
        });

        return newInventory;
    }

    public Inventory updateInventory(Inventory newInventory)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("inventory_queries", "update_inventory.sql");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content);
                ps.setInt(2, newInventory.getQty());
                ps.setInt(3, newInventory.getAisle());
                ps.setString(0, newInventory.getProduct_id());
                ps.setInt(1, newInventory.getStore_id());
                return ps;
            }
        });

        return newInventory;
    }

    public void updateInventoryQty(int store_id, String product_id, int qty)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("inventory_queries", "update_inventory_qty.sql");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content);
                ps.setInt(1, qty);
                ps.setString(3, product_id);
                ps.setInt(2, store_id);
                return ps;
            }
        });
    }

    public class InventoryRowMapper implements RowMapper<Inventory> {
        @Override
        public Inventory mapRow(ResultSet rs, int rowNum) throws SQLException {

            String product_id = rs.getString("product_id");
            Product inventoryProduct = productRepository.findProductById(product_id);
            int store_id = rs.getInt("store_id");
            int qty = rs.getInt("qty");
            int aisle = rs.getInt("aisle");
            Inventory newInventory = new Inventory(product_id, store_id, qty, aisle);
            newInventory.setProduct(inventoryProduct);
            return newInventory;
        }
    }
}
