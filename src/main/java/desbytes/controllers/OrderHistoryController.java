package desbytes.controllers;

import desbytes.Repositories.Grocery_OrderRepository;
import desbytes.models.Grocery_Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

/**
 * Created by Jeff on 4/29/2017.
 */
@Controller
public class OrderHistoryController {

    @RequestMapping("/OrderHistory")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        Grocery_OrderRepository r = new Grocery_OrderRepository();
        int id = 1;
        List<Grocery_Order> orderList = r .getOrdersByUser(id);

        model.addAttribute("name", name);
        return "index";
    }

}
