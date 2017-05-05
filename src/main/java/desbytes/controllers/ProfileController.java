package desbytes.controllers;

import desbytes.Repositories.AppUserRepository;
import desbytes.Repositories.CustomerRepository;
import desbytes.Repositories.EmployeeRepository;
import desbytes.models.App_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

/**
 * Created by zanegrasso
 * Created on 4/29/17.
 */
@Controller
public class ProfileController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AppUserRepository appUserRepository;


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String username(@RequestParam("username") String username, Model model) {
        App_User user = appUserRepository.findUserByName(username);
        int storeId = customerRepository.findCustomerByID(user.getId()).getPref_store_id();
        model.addAttribute("storeId", storeId);
        model.addAttribute("loggedUser", user);

        return "profile";
    }
}
