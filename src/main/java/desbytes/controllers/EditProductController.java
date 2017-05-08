package desbytes.controllers;

import desbytes.Repositories.*;
import desbytes.models.App_User;
import desbytes.models.Product;
import desbytes.models.ProductInfo;
import desbytes.models.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Created by zach on 5/5/17.
 */
@Controller
public class EditProductController {
    @Autowired
    private ManageProductRepository productInfoRepo;

    @Autowired
    private ProductRepository productRepo;

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

    @ModelAttribute("AllProducts")
    public List<Product> AllProductInfos(){
        return this.productRepo.getAll();
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
    public String render(Model model, HttpServletRequest request){
        model.addAttribute("productInfo", new ProductInfo());
        Integer storeId = (Integer) request.getSession().getAttribute("storeId");
        return "redirect:/edit/"+ storeId;
    }

    @GetMapping("/edit/{storeId}")
    public String editStore(ModelMap model,
                              @PathVariable("storeId") int store, HttpServletRequest request){
        Integer storeId = (Integer) request.getSession().getAttribute("storeId");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        if (roles.contains("0")) {
            return "redirect:/error";
        }
        else if (storeId != store && roles.contains("1")) {
            return "redirect:/edit/" + storeId;
        } else {

            this.storeId = store;
            model.put("CurrentStoreId", currentStoreId());
            model.put("CurrentStore", currentStore());
            model.put("ProductInfos", ProductInfos());

            return "edit";
        }
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

    @PostMapping("/addProduct")
    public  String addProduct(ModelMap model,
                                  @Valid @ModelAttribute("ProductInfo") ProductInfo productInfo,
                                  final BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "edit";
        }
        productInfo.setStore_id(this.storeId);
        this.productInfoRepo.insertProduct(productInfo);
        model.put("ProductInfos", ProductInfos());
        return "edit";
    }


    @PostMapping("/addInventory")
    public  String addInventory(ModelMap model,
                                  @Valid @ModelAttribute("ProductInfo") ProductInfo productInfo,
                                  final BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "edit";
        }
        productInfo.setStore_id(this.storeId);
        this.productInfoRepo.insertProductInfo(productInfo);
        model.put("ProductInfos", ProductInfos());
        return "edit";
    }

    @PostMapping("/remove")
    public  String removeProductInfo(ModelMap model,
                                     @RequestParam("deleteItem")String productId){
        ProductInfo productInfo = newProduct();
        productInfo.setStore_id(this.storeId);
        productInfo.setProduct_id(productId);
        this.productInfoRepo.removeProductInfo(productInfo);
        model.put("ProductInfos", ProductInfos());
        return "edit";
    }

}
