package desbytes.controllers;

import desbytes.Repositories.StoreRepository;
import desbytes.models.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The index controller that maps requests to our code.
 * @author Daniel
 */
@Controller
public class IndexController {

    @Autowired
    private StoreRepository storeRepository;

    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {

        List<Store> storeList = storeRepository.findAllStores();

        model.addAttribute("name", name);
        model.addAttribute("storeList", storeList);
        return "index";
    }
}
