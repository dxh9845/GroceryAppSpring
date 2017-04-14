package desbytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

/**
 * Create the Database
 * Created by danie on 4/14/2017.
 */
@Configuration
public class DataSourceConfig {

    private static final String temp_dir = System.getProperty("java.io.tmpdir");

    @Bean(name="mainDataSource")
    public DataSource createMainDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:./grocery;");
        return ds;
    }
}
