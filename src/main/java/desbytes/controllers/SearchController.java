package desbytes.controllers;

import desbytes.Repositories.ProductRepository;
import desbytes.Repositories.StoreRepository;
import desbytes.models.Product;
import desbytes.models.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The index controller that maps requests to our code.
 * @author Daniel
 */
@Controller
public class SearchController {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String greeting(@RequestParam(value="query", required=false) String query, Model model) {

        List<Product> productList = productRepository.searchProducts(query);
        model.addAttribute("productList", productList);
        return "search";
    }
}
