package desbytes.controllers;

import desbytes.Repositories.*;
import desbytes.models.App_User;
import desbytes.models.Customer;
import desbytes.models.Inventory;
import desbytes.models.Product;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * The index controller that maps requests to our code.
 * @author Daniel
 */
@Controller
public class IndexController {
    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

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

    @RequestMapping("/")
    public String greeting(Model model, HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            List<Product> productList = productRepository.findTopProducts(25, 0);
            model.addAttribute("productList", productList);
        }
        else {
            App_User user = userRepository.findUserByName(auth.getName());
            int storeId = getUserStore(user);
            List<Inventory> inventoryList = inventoryRepository.getStoreInventory(storeId);
            model.addAttribute("inventoryList", inventoryList);
        }

        return "index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping(value = "/register")
    public String register(Model model, RedirectAttributes redir) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new App_User());
        }
        model.addAttribute("storeList", storeRepository.findAllStores());
        return "register_user";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String postRegister(@ModelAttribute @Valid App_User user, BindingResult bindingResult,
                                     int storeId, Model model, RedirectAttributes redir) {
        if (bindingResult.hasErrors()) {
            redir.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redir.addFlashAttribute("user", user);
            return "redirect:/register";
        }

        try {
            userRepository.insertUser(user);
            customerRepository.insertCustomer(new Customer(user.getId(), storeId));
        } catch (DuplicateKeyException exc) {
            exc.printStackTrace();
            redir.addFlashAttribute("registerError", true);
            redir.addFlashAttribute("errorMsg", "The username '" + user.getUsername() + "' has already been taken.");
            return "redirect:/register";
        }

        model.addAttribute("registerSuccess", true);
        return "/login";
    }



}
