package desbytes.controllers;

import desbytes.Repositories.AppUserRepository;
import desbytes.Repositories.CustomerRepository;
import desbytes.Repositories.EmployeeRepository;
import desbytes.models.App_User;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EmployeeRepository employeeRepository;
    private AppUserRepository appUserRepository;

    private int roleId = 0;

    @ModelAttribute("CurrentRole")
    public int currentRole() {
        return this.roleId;
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public String username(@RequestParam(value = "username", required = false, defaultValue = "World") String username, Model model) {
        App_User name = appUserRepository.findUserById(roleId);

        model.addAttribute("name", name);

        return "profile";
    }







}
