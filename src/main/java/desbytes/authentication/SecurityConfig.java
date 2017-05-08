package desbytes.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Configure the login authentication.
 */

@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    AuthSuccessHandler securityHandler;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    DataSource dataSource;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/").permitAll()
                // Fix for h2 console
                .antMatchers("/css/**").permitAll()
                .antMatchers("/console/**").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/edit").hasAnyAuthority("1", "2")
                .antMatchers("/register/employee").hasAuthority( "2")
                .antMatchers("/manage").hasAnyAuthority("1","2");
        http
                .formLogin().loginPage("/login").failureUrl("/login-error")
                .successHandler(securityHandler)
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout");
        http
                .csrf().disable();
        http.headers().frameOptions().disable();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, 'true' as enabled from APP_USER where username=?")
                .authoritiesByUsernameQuery("SELECT username, role_id from APP_USER WHERE username=?");
    }
}