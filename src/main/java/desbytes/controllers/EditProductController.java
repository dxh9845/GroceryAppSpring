package desbytes.controllers;

import desbytes.Repositories.AppUserRepository;
import desbytes.Repositories.EmployeeRepository;
import desbytes.Repositories.ManageProductRepository;
import desbytes.Repositories.StoreRepository;
import desbytes.models.App_User;
import desbytes.models.ProductInfo;
import desbytes.models.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private int storeId = 1;

    @ModelAttribute("ProductInfo")
    public ProductInfo newProduct(){
        ProductInfo newInfo = new ProductInfo();
        newInfo.setStore_id(this.storeId);
        return newInfo;
    }

    @ModelAttribute("ProductInfos")
    public List<ProductInfo> ProductInfos(){
        return this.productInfoRepo.findProductInventoryByStoreId(this.storeId);
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
        model.addAttribute("productInfo", new ProductInfo());
        return "redirect:/edit/"+this.storeId;
    }

    @GetMapping("/edit/{storeId}")
    public String editStore(ModelMap model,
                              @PathVariable("storeId") int store){
        // TODO Fix error if this key isn't in our db
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            App_User user = userRepository.findUserByName(auth.getName());
            if (user != null) {
                if (user.getRole_id() != 0) {
                    if (user.getRole_id() == 1){
                        int workStoreId = employeeRepository.findEmployeeByID(user.getId()).getWork_store_id();
                        if (this.storeId != workStoreId) {
                            return "redirect:/edit/" + workStoreId;
                        }
                    }
                    this.storeId = store;
                    model.put("CurrentStore", currentStore());
                    model.put("ProductInfos", ProductInfos());

                    return "edit";
                }
            }
        }
        return "redirect:/";
    }

    @PostMapping("/edit")
    public  String editProductInfo(ModelMap model,
                                   @Valid @ModelAttribute("ProductInfo") ProductInfo productInfo,
                                   final BindingResult bindingResult,
                                   HttpServletRequest req){
        if (bindingResult.hasErrors()){
            return "/edit";
        }
        this.productInfoRepo.updateProductInfo(productInfo);
        model.put("ProductInfos", ProductInfos());
        return "edit";
    }



    @PostMapping("/add")
    public  String addProductInfo(ModelMap model,
                                  @Valid @ModelAttribute("ProductInfo") ProductInfo productInfo,
                                  final BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/edit";
        }
        productInfo.setStore_id(this.storeId);
        this.productInfoRepo.insertProductInfo(productInfo);
        model.put("ProductInfos", ProductInfos());
        return "edit";
    }

}
