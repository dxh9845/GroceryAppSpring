package desbytes.controllers;

import desbytes.Repositories.AppUserRepository;
import desbytes.Repositories.ProductRepository;
import desbytes.Repositories.ShoppingCartRepository;
import desbytes.models.Product;
import desbytes.models.Shopping_Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private AppUserRepository userRepository;

    @RequestMapping(value="/product", method = RequestMethod.GET)
    public String greeting(@RequestParam("id") String id, Model model) {

        Product prod = productRepository.findProductById(id);
        model.addAttribute("product", prod);
        return "product_detail";
    }

    @PostMapping("/product")
    public String addToCart(Model model, @Valid @ModelAttribute("product") Product product){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            int id = userRepository.findUserByName(username).getId();
            String prod_id = product.getProduct_id();
            int qty = 1;

            try {
                this.shoppingCartRepository.addProductToCart(id, prod_id, qty);
            }
            catch(Exception e) //already in the cart
            {

            }
        }


        return "redirect:/cart";
    }

}
