package desbytes.authentication;

import desbytes.Repositories.AppUserRepository;
import desbytes.Repositories.CustomerRepository;
import desbytes.Repositories.EmployeeRepository;
import desbytes.models.App_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * Created by danie on 5/5/2017.
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        App_User user = userRepository.findUserByName(authentication.getName());
        // Are we a user
        if (roles.contains("0")) {
            int prefStoreId = customerRepository.findCustomerByID(user.getId()).getPref_store_id();
            httpServletRequest.getSession().setAttribute("storeId", prefStoreId);
            httpServletResponse.sendRedirect("/");
        }
        // Are we an employee
        else if (roles.contains("1") || roles.contains("2")) {
            int workStoreId = employeeRepository.findEmployeeByID(user.getId()).getWork_store_id();
            httpServletRequest.getSession().setAttribute("storeId", workStoreId);
            httpServletResponse.sendRedirect("/");
        }
    }
}
