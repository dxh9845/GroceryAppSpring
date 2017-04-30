package desbytes.controllers;

import desbytes.Repositories.ManageProductRepository;
import desbytes.models.Manage_Product_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by zach on 4/29/17.
 */
@Controller
public class ManageController {
    @Autowired
    private ManageProductRepository productInfoRepo;
    private int storeId = 7;

    @ModelAttribute("ProductInfos")
    public List<Manage_Product_Info> ProductInfos(){
        return this.productInfoRepo.findProductInventoryPage(0, this.storeId);
    }

    @ModelAttribute("CurrentStore")
    public int currentStore(){
        return this.storeId;
    }

    @GetMapping("/manage")
    public String render(Model model){
        model.addAttribute("productInfo", new Manage_Product_Info());
        return "manage";
    }

    @PostMapping("/manage")
    public  String addProductInfo(Model model, @Valid @ModelAttribute("productInfo") Manage_Product_Info productInfo, final BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/manage";
        }
        this.productInfoRepo.insertProductInfo(productInfo);
//        model.asMap().replace("productInfo", new Manage_Product_Info());
        return "redirect:/manage";
    }
}
