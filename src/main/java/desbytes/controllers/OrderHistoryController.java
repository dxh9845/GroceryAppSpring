package desbytes.controllers;

import desbytes.Repositories.AppUserRepository;
import desbytes.Repositories.OrderHistoryRepository;
import desbytes.models.Grocery_Order;
import desbytes.models.OrderHistory;
import desbytes.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

/**
 * @author Jeff
 */
@Controller
public class OrderHistoryController {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    @Autowired
    private AppUserRepository userRepository;

    @RequestMapping("/history")
    public String greeting(@RequestParam(value="username", required=false, defaultValue="psullivan")String username, Model model) {

        int id = userRepository.findUserByName(username).getId();

        List<OrderHistory> orderList = orderHistoryRepository.findOrderByUser(id);
        model.addAttribute("orderList", orderList);

        return "history";
    }

}
