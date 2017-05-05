package desbytes.Repositories;


import desbytes.models.Manage_Product_Info;
import desbytes.utils.QueryReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Zach
 */
@Repository
public class ManageProductRepository {

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public List<Manage_Product_Info> findProductInventoryPage() {
        return findProductInventoryPage(0, 0);
    }

    public List<Manage_Product_Info> findProductInventoryPage(int pageNum, int storeId) {
        QueryReader reader = new QueryReader();
        String sql = reader.readQueryFile(
                "manage_queries",
                "get_product_info.sql");
        return jdbcTemplate.query(sql, new ManageProductInfoRowMapper(), storeId);
    }

    public void changeQuantity(int qty, double productId, double storeId) {
        QueryReader reader = new QueryReader();
        String sql = reader.readQueryFile("manage_queries", "update_inventory_qty.sql");
        jdbcTemplate.update(sql, qty, productId, storeId);
    }

    public void changeAisle(int aisle, double productId, double storeId) {
        QueryReader reader = new QueryReader();
        String sql = reader.readQueryFile("manage_queries", "update_inventory_qty.sql");
        jdbcTemplate.update(sql, aisle, productId, storeId);
    }

    public void changeName(String name, double productId) {
        QueryReader reader = new QueryReader();
        String sql = reader.readQueryFile("manage_queries", "update_inventory_qty.sql");
        jdbcTemplate.update(sql, name, productId);
    }

    public void changePrice(float price, double productId) {
        QueryReader reader = new QueryReader();
        String sql = reader.readQueryFile("manage_queries", "update_inventory_qty.sql");
        jdbcTemplate.update(sql, price, productId);
    }

    public void insertProductInfo(Manage_Product_Info productInfo) {
        QueryReader reader = new QueryReader();

        // Make new product row
        String sqlNewProduct = reader.readQueryFile(
                "manage_queries", "insert_product.sql");
        jdbcTemplate.update(sqlNewProduct,
                productInfo.getProduct_id(), productInfo.getName(), productInfo.getPrice()
        );

        // Make new inventory row
        String sqlNewInventory = reader.readQueryFile(
                "manage_queries", "insert_inventory.sql");
        jdbcTemplate.update(sqlNewInventory,
                productInfo.getProduct_id(), productInfo.getStore_id(), productInfo.getQty(),
                productInfo.getAisle()
        );
    }

    public void removeProductInfo(double productId, double storeId) {
        QueryReader reader = new QueryReader();

        // Make new inventory row
        String sql = reader.readQueryFile(
                "manage_queries", "remove_product_info.sql");
        jdbcTemplate.update(sql, productId, storeId);
    }

    public class ManageProductInfoRowMapper implements RowMapper<Manage_Product_Info> {
        @Override
        public Manage_Product_Info mapRow(ResultSet rs, int rowNum) throws SQLException {
            String productId = rs.getString("product_id");
            int storeId = rs.getInt("store_id");
            String productName = rs.getString("name");
            float price = rs.getFloat("price");
            int qty = rs.getInt("qty");
            int aisle = rs.getInt("aisle");
            return new Manage_Product_Info(productId, storeId, productName, price, qty, aisle);
        }
    }
}
