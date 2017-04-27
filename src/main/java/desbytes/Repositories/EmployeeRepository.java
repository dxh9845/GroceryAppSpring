package desbytes.Repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import desbytes.models.Employee;
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
public class EmployeeRepository {

    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public List<Employee> findAllEmployees(){
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("employee_queries", "select_all_employee.sql");
        return jdbcTemplate.query(content, new EmployeeRowMapper());
    }

    public Employee findEmployeeByID(int id){
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("employee_queries", "get_employee.sql");
        return jdbcTemplate.queryForObject(content,
                new Object[]{id}, new EmployeeRowMapper());
    }


    public List<Employee> findEmployeesByStore(){
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("employee_query","get_employee_by_store.sql");
        return jdbcTemplate.query(content, new EmployeeRowMapper());
    }

    public Employee insertEmployee(Employee newEmployee) {
        QueryReader reader = new QueryReader();
        String content = reader.readQueryFile("employee_queries", "create_new_employee.sql");
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(content, Statement.RETURN_GENERATED_KEYS);
                ps.setFloat(1, newEmployee.getSalary());
                ps.setInt(2, newEmployee.getWork_store_id());
                return ps;
            }
        }, holder);

        int newEmployeeId = holder.getKey().intValue();
        newEmployee.setUser_id(newEmployeeId);
        return newEmployee;
    }

    public class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            int user_id = rs.getInt("user_id");
            float salary = rs.getFloat("salary");
            int work_store_id = rs.getInt("work_store_id");
            return new Employee(user_id, salary, work_store_id);
        }
    }
}

