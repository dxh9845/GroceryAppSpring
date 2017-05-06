package desbytes.controllers;

import desbytes.Repositories.AppUserRepository;
import desbytes.Repositories.CustomerRepository;
import desbytes.Repositories.OrderHistoryRepository;
import desbytes.Repositories.ShoppingCartRepository;
import desbytes.models.Grocery_Order;
import desbytes.models.OrderHistory;
import desbytes.models.Product;
import desbytes.models.Shopping_Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

/**
 * @author Jeff
 */
@Controller
public class ShoppingCartController {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("/cart")
    public String greeting(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            int id = userRepository.findUserByName(username).getId();

            Shopping_Cart cart = shoppingCartRepository.getShoppingCartByID(id);

            if(cart == null)
            {
                cart = new Shopping_Cart();
                cart.setProductList(new HashMap<Product, Integer>());
            }

            model.addAttribute("cart", cart);
            model.addAttribute("cartProducts", cart.getProducts());
        }

        return "cart";
    }

    @PostMapping("/cart")
    public String orderShoppingCart(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            int id = userRepository.findUserByName(username).getId();

            Shopping_Cart cart = shoppingCartRepository.getShoppingCartByID(id);

            if(cart != null)
            {
                OrderHistory order = new OrderHistory();
                order.setProductList(cart.getProductList());
                order.setUser_id(cart.getCustomer_id());
                order.setStore_id(customerRepository.findCustomerByID(id).getPref_store_id());
                order.setOrder_time(new java.sql.Timestamp(new java.util.Date().getTime()));

                orderHistoryRepository.insertOrder(order);
                shoppingCartRepository.deleteCart(id);
            }
        }

        return "redirect:/history";
    }

}