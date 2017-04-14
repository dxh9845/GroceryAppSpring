package desbytes.controllers;

import desbytes.config.DataSourceConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by danie on 3/15/2017.
 */
@Controller
public class IndexController {

    private DataSourceConfig dataSourceConfig;

    public IndexController() {
        this.dataSourceConfig = new DataSourceConfig();
    }

    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {

        final DataSource ds = this.dataSourceConfig.createMainDataSource();
        try {
            final Connection connection = ds.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM STORE;");
            while (resultSet.next()) {
                final String owner_name = resultSet.getString("store_id");
                System.out.println("Store_ID is: " + owner_name);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }


        model.addAttribute("name", name);
        return "index";
    }
}
