package desbytes.controllers;

import desbytes.Repositories.ProductRepository;
import desbytes.Repositories.ShoppingCartRepository;
import desbytes.Repositories.StoreRepository;
import desbytes.models.Product;
import desbytes.models.Shopping_Cart;
import desbytes.models.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The index controller that maps requests to our code.
 * @author Daniel
 */
@Controller
public class IndexController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {

        List<Product> productList = productRepository.findTopProducts(25, 0);

        Shopping_Cart shopping_cart = shoppingCartRepository.getShoppingCartByID(4);
        model.addAttribute("name", name);
        model.addAttribute("productList", productList);
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin() {
        // TODO Enable form login with Spring Security (trigger error for now)
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
