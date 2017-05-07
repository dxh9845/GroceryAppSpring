package desbytes.Repositories;

import desbytes.models.Employee;
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
 * Created by zanegrasso
 * Created on 4/27/17.
 */
@Repository
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
        String sql = reader.readQueryFile(
                "employee_queries", "create_new_employee.sql");
        jdbcTemplate.update(sql,
                newEmployee.getUser_id(), newEmployee.getSalary(), newEmployee.getWork_store_id());
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

