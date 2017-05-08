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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zach on 4/29/17.
 */
@Controller
public class ManageController {
    @Autowired
    private ManageProductRepository productInfoRepo;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private int storeId = 1;

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

    @GetMapping("/manage")
    public String render(Model model, HttpServletRequest request){
        Integer storeId = (Integer) request.getSession().getAttribute("storeId");
        return "redirect:/manage/"+ storeId;
    }

    @GetMapping("/manage/{storeId}")
    public String changeStore(ModelMap model,
                              @PathVariable("storeId") int store,
                              HttpServletRequest request){
        // TODO Fix error if store_id key isn't in our db
        Integer storeId = (Integer) request.getSession().getAttribute("storeId");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            App_User user = userRepository.findUserByName(auth.getName());
            if (user != null) {
                if (user.getRole_id() != 0) {
                    if (user.getRole_id() == 1){
                        if (store != storeId) {
                            return "redirect:/manage/" + storeId;
                        }
                    }
                    model.put("CurrentStore", currentStore());
                    model.put("CurrentStoreId", currentStoreId());
                    model.put("ProductInfos", ProductInfos());
                    model.addAttribute("productInfo", new ProductInfo());

                    return "manage";
                }
            }
        }
        return "redirect:/";
    }

}
