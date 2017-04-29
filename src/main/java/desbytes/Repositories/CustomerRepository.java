package desbytes.Repositories;

import desbytes.models.Customer;
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
 * Created by zanegrasso
 * Created on 4/27/17.
 */
@Repository
public class CustomerRepository {

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public List<Customer> findAllCustomers(){
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("customer_queries", "select_all_customer.sql");
        return jdbcTemplate.query(content, new CustomerRowMapper());
    }

    public Customer findCustomerByID(int id){
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("customer_queries", "get_customer.sql");
        return jdbcTemplate.queryForObject(content,
                new Object[]{id}, new CustomerRowMapper());
    }


    public List<Customer> findCustomersByStore(){
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("customer_queries","get_customer_by_store.sql");
        return jdbcTemplate.query(content, new CustomerRowMapper());
    }

    public Customer insertCustomer(Customer newCustomer) {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("customer_queries", "create_new_customer.sql");
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, newCustomer.getPref_store_id());
                return ps;
            }
        }, holder);

        int newCustomerId = holder.getKey().intValue();
        newCustomer.setUser_id(newCustomerId);
        return newCustomer;
    }

    public class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            int user_id = rs.getInt("user_id");
            int pref_store_id = rs.getInt("pref_store_id");
            return new Customer(user_id, pref_store_id);
        }
    }
}
