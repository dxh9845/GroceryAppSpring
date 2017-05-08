package desbytes.controllers;

import desbytes.Repositories.AppUserRepository;
import desbytes.Repositories.CustomerRepository;
import desbytes.Repositories.EmployeeRepository;
import desbytes.Repositories.StoreRepository;
import desbytes.models.App_User;
import desbytes.models.Store;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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

    @Autowired
    private StoreRepository storeRepository;


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String username(HttpServletRequest request, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        App_User user = appUserRepository.findUserByName(auth.getName());
        Integer storeId = (Integer) request.getSession().getAttribute("storeId");
        Store prefStore = storeRepository.findStoreById(storeId);
        model.addAttribute("prefStore", prefStore);
        model.addAttribute("loggedUser", user);

        return "profile";
    }
}
