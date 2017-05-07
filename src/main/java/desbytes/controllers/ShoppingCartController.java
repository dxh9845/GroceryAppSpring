package desbytes.controllers;

import desbytes.Repositories.*;
import desbytes.models.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/cart")
    public String greeting(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            int id = userRepository.findUserByName(username).getId();

            Shopping_Cart cart = shoppingCartRepository.getShoppingCartByID(id);

            if (cart == null) {
                cart = new Shopping_Cart();
                cart.setProductList(new HashMap<Product, Integer>());
            } else {
                HashMap<Product, Integer> productList = cart.getProductList();
            }

            model.addAttribute("cart", cart);
            model.addAttribute("cartProducts", cart.getProducts());
        }

        return "cart";
    }

    @PostMapping("/cart")
    public String orderShoppingCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            int id = userRepository.findUserByName(username).getId();

            Shopping_Cart cart = shoppingCartRepository.getShoppingCartByID(id);

            if (cart != null) {
                OrderHistory order = new OrderHistory();
                order.setProductList(cart.getProductList());
                order.setUser_id(cart.getCustomer_id());
                order.setStore_id(customerRepository.findCustomerByID(id).getPref_store_id());
                order.setOrder_time(new java.sql.Timestamp(new java.util.Date().getTime()));

                try {
                    orderHistoryRepository.insertOrder(order);
                    shoppingCartRepository.deleteCart(id);
                }
                catch(IllegalArgumentException e)
                {
                    return "redirect:/cart";
                }
            }
        }


        return "redirect:/history";
    }


    @RequestMapping(value = "/cart/product_id", method=RequestMethod.POST, params = "action=update")
    public String qtyByProductId(@RequestParam("quantity") int quantity, @RequestParam("product_id") String product_id,
                                 HttpServletRequest request, Model model, RedirectAttributes redir) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            int id = userRepository.findUserByName(username).getId();

            // Get the product
            Product prod = productRepository.findProductById(product_id);
            // Get the user's preferred store
            Integer prefStore = (Integer) request.getSession().getAttribute("storeId");
            // If we have overdrawn from the store's inventory
            Inventory inventoryItem = inventoryRepository.getItemFromStore(prefStore, product_id);
            if (inventoryItem.getQty() < quantity) {
                redir.addFlashAttribute("updateError", true);
                redir.addFlashAttribute("errorMsg", "There are not enough of " + prod.getName() + "' in stock. " +
                        "We have at most " + inventoryItem.getQty() + " of this item in stock.");
                return "redirect:/cart";
            }


            shoppingCartRepository.updateShoppingCart(id, product_id, quantity);
        }

        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart/product_id", method=RequestMethod.POST,  params = "action=delete")
    public String deleteByProductId(@RequestParam("product_id") String product_id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            int id = userRepository.findUserByName(username).getId();

            shoppingCartRepository.deleteItem(id, product_id);
        }
        return"redirect:/cart"; 
    }

}