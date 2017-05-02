package desbytes.controllers;

import desbytes.Repositories.ProductRepository;
import desbytes.Repositories.ShoppingCartRepository;
import desbytes.models.Product;
import desbytes.models.Shopping_Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * A controller to view product objects
 */
@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @RequestMapping(value="/product", method = RequestMethod.GET)
    public String greeting(@RequestParam("id") String id, Model model) {

        Product prod = productRepository.findProductById(id);
        model.addAttribute("product", prod);
        return "product_detail";
    }

}
