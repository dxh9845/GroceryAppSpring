package desbytes.controllers;

import desbytes.Repositories.InventoryRepository;
import desbytes.Repositories.ProductRepository;
import desbytes.Repositories.StoreRepository;
import desbytes.models.Inventory;
import desbytes.models.Product;
import desbytes.models.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * The index controller that maps requests to our code.
 * @author Daniel
 */
@Controller
public class SearchController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value="query", required=true) String query,
                         @RequestParam(value="storeId", required=false) Integer storeId, Model model) {

        model.addAttribute("searchTerm", query);
        if (storeId == null) {
            List<Product> productList = productRepository.searchProducts(query);
            model.addAttribute("productList", productList);
            model.addAttribute("inventoryList", new ArrayList<Inventory>());
        } else {
            List<Inventory> inventoryList = inventoryRepository.searchStoreInventory(storeId, query);
            model.addAttribute("productList", new ArrayList<Product>());
            model.addAttribute("inventoryList", inventoryList);
        }
        return "search";
    }
}
