package desbytes.controllers;

import desbytes.config.DataSourceConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by zanegrasso
 * Created on 4/21/17.
 */
@Controller
public class ShoppingCartController {
    /*private DataSourceConfig dataSourceConfig;

    public ShoppingCartController() { this.dataSourceConfig = new DataSourceConfig();}

    @RequestMapping(value = "/{shoppingcart}", method = RequestMethod.GET)
    public ShoppinCart getShoppingCart(@PathVariable long customerId) {
        try{
            final Connection connection =
        }
    }
    */
}
