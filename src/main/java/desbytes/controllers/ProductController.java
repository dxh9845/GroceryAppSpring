package desbytes.controllers;

import desbytes.Repositories.*;
import desbytes.models.App_User;
import desbytes.models.Inventory;
import desbytes.models.Product;
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public int getUserStore(App_User user) {
        // Are we a user
        if (user.getRole_id() == 0) {
            int prefStoreId = customerRepository.findCustomerByID(user.getId()).getPref_store_id();
            return prefStoreId;
        }
        // Are we an employee
        else {
            int workStoreId = employeeRepository.findEmployeeByID(user.getId()).getWork_store_id();
            return workStoreId;
        }
    }

    @RequestMapping(value="/product", method = RequestMethod.GET)
    public String greeting(@RequestParam("id") String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Product prod = productRepository.findProductById(id);
        model.addAttribute("product", prod);
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            App_User user = userRepository.findUserByName(auth.getName());
            int storeId = getUserStore(user);
            Inventory inventoryItem = inventoryRepository.getItemFromStore(storeId, id);
            model.addAttribute("inventoryItem", inventoryItem);
        }
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
