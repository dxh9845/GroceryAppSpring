package desbytes.controllers;

import desbytes.Repositories.ManageProductRepository;
import desbytes.models.Manage_Product_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Created by zach on 4/29/17.
 */
@Controller
public class ManageController {
    @Autowired
    private ManageProductRepository productInfoRepo;

    @GetMapping("/manage")
    public String render(Model model){
        int storeId = 7;
        List<Manage_Product_Info> productInfos =
                productInfoRepo.findProductInventoryPage(0, storeId);
        model.addAttribute("ProductInfos", productInfos);
        model.addAttribute("CurrentStore", storeId);
        model.addAttribute("productInfo", new Manage_Product_Info());
        return "manage";
    }

    @PostMapping(value = "/manage")
    public  String addProductInfo(@ModelAttribute Manage_Product_Info productInfo){
        this.productInfoRepo.insertProductInfo(productInfo);
        return "manage";
    }
}
