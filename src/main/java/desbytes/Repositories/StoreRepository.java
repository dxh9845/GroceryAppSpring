package desbytes.Repositories;

import desbytes.models.Store;
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
public class StoreRepository {

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Store> findAllStores() {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("store_queries", "select_all_stores.sql");
        return jdbcTemplate.query(content, new StoreRowMapper());
    }

    public List<Store> findStoresThatHaveProduct(String productId) {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("store_queries", "find_stores_that_have_product.sql");
        return jdbcTemplate.query(content, new Object[]{productId}, new StoreRowMapper());
    }

    public Store findStoreById(int id) {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("store_queries", "get_store.sql");
        return jdbcTemplate.queryForObject(content, new StoreRowMapper(), id);
    }

    public Store insertStore(Store newStore) {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("store_queries", "create_new_store.sql");
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, newStore.getName());
                ps.setString(2, newStore.getLocation());
                return ps;
            }
        }, holder);

        int newStoreId = holder.getKey().intValue();
        newStore.setStore_id(newStoreId);
        return newStore;
    }

    public class StoreRowMapper implements RowMapper<Store> {
        @Override
        public Store mapRow(ResultSet rs, int rowNum) throws SQLException {
            int store_id = rs.getInt("store_id");
            String store_name = rs.getString("name");
            String store_location = rs.getString("location");
            return new Store(store_id, store_name, store_location);
        }
    }
}
