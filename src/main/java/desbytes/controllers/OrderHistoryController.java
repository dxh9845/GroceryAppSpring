package desbytes.controllers;

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

    @RequestMapping("/history")
    public String greeting(Model model) {

        int id = 1;
        //List<OrderHistory> orderList = orderHistoryRepository.findOrderByUser(id);
        OrderHistory history = orderHistoryRepository.findOrderById(1);

        //model.addAttribute("orderList", orderList);
        model.addAttribute("productList", history.getProductList().keySet());
        return "history";
    }

}
