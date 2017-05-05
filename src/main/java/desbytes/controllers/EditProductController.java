package desbytes.controllers;

import desbytes.Repositories.ManageProductRepository;
import desbytes.Repositories.StoreRepository;
import desbytes.models.Manage_Product_Info;
import desbytes.models.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by zach on 5/5/17.
 */
@Controller
public class EditProductController {
    @Autowired
    private ManageProductRepository productInfoRepo;

    @Autowired
    private StoreRepository storeRepository;

    private int storeId = 1;

    @ModelAttribute("ProductInfos")
    public List<Manage_Product_Info> ProductInfos(){
        return this.productInfoRepo.findProductInventoryPage(0, this.storeId);
    }

    @ModelAttribute("CurrentStore")
    public String currentStore(){
        return this.storeRepository.findStoreById(this.storeId).getName();
    }

    @ModelAttribute("CurrentStoreId")
    public int currentStoreId(){
        return this.storeId;
    }

    @ModelAttribute("StoreList")
    public List<Store>storeList(){
        return this.storeRepository.findAllStores();
    }


    @GetMapping("/edit")
    public String render(Model model){
        model.addAttribute("productInfo", new Manage_Product_Info());
        return "redirect:/edit/"+this.storeId;
    }

    @GetMapping("/edit/{storeId}")
    public String changeStore(ModelMap model,
                              @PathVariable("storeId") int store){
        // TODO Fix error if this key isn't in our db
        this.storeId = store;
        model.put("CurrentStore", currentStore());
        model.put("ProductInfos", ProductInfos());
        model.addAttribute("productInfo", new Manage_Product_Info());

        return "edit";
    }

}
