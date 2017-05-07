package desbytes.Repositories;


import desbytes.models.ProductInfo;
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
    public List<ProductInfo> findProductInventory(){
        return findProductInventoryByStoreId(1);
    }

    public List<ProductInfo> findProductInventoryByStoreId(int storeId) {
        QueryReader reader = new QueryReader();
        String sql = reader.readQueryFile(
                "manage_queries",
                "get_product_info.sql");
        return jdbcTemplate.query(sql, new ManageProductInfoRowMapper(), storeId);
    }

    public void updateProductInfo(ProductInfo info) {
        QueryReader reader = new QueryReader();
        String sql = reader.readQueryFile("manage_queries", "update_product.sql");
        jdbcTemplate.update(sql,
                info.getName(), info.getPrice(), info.getProduct_id());

        sql = reader.readQueryFile("manage_queries", "update_inventory.sql");
        jdbcTemplate.update(sql,
                info.getAisle(), info.getQty(), info.getProduct_id(), info.getStore_id());
    }

    public void insertProduct(ProductInfo productInfo) {
        QueryReader reader = new QueryReader();

        // Make new product row
        String sqlNewProduct = reader.readQueryFile(
                "manage_queries", "insert_product.sql");
        jdbcTemplate.update(sqlNewProduct,
                productInfo.getProduct_id(), productInfo.getName(), productInfo.getPrice()
        );
    }

    public void insertProductInfo(ProductInfo productInfo) {
        QueryReader reader = new QueryReader();

        // Make new inventory row
        String sqlNewInventory = reader.readQueryFile(
                "manage_queries", "insert_inventory.sql");
        jdbcTemplate.update(sqlNewInventory,
                productInfo.getProduct_id(), productInfo.getStore_id(), productInfo.getQty(),
                productInfo.getAisle()
        );
    }

    public void removeProductInfo(ProductInfo info) {
        QueryReader reader = new QueryReader();

        // Make new inventory row
        String sql = reader.readQueryFile(
                "manage_queries", "remove_inventory.sql");
        jdbcTemplate.update(sql, info.getProduct_id(), info.getStore_id());
    }

    public class ManageProductInfoRowMapper implements RowMapper<ProductInfo> {
        @Override
        public ProductInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            String productId = rs.getString("product_id");
            int storeId = rs.getInt("store_id");
            String productName = rs.getString("name");
            float price = rs.getFloat("price");
            int qty = rs.getInt("qty");
            int aisle = rs.getInt("aisle");
            return new ProductInfo(productId, storeId, productName, price, qty, aisle);
        }
    }
}
