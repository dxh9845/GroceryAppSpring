package desbytes.Repositories;

import desbytes.models.Inventory;
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

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ServletContext context;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Inventory insertInventory(Inventory newInventory)
    {
        QueryReader r = new QueryReader();
        String content = r.readQueryFile("inventory_queries", "create_new_inventory.sql");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content);
                ps.setDouble(0, newInventory.getProduct_id());
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
                ps.setDouble(0, newInventory.getProduct_id());
                ps.setInt(1, newInventory.getStore_id());
                return ps;
            }
        });

        return newInventory;
    }

    public class InventoryRowMapper implements RowMapper<Inventory> {
        @Override
        public Inventory mapRow(ResultSet rs, int rowNum) throws SQLException {

            double product_id = rs.getDouble("product_id");
            int store_id = rs.getInt("store_id");
            int qty = rs.getInt("qty");
            int aisle = rs.getInt("aisle");
            return new Inventory(product_id, store_id, qty, aisle);
        }
    }
}
