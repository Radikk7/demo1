package com.example.demo.mvcconfig;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//на все классы включаем сикьюрность , которая даёт доступ только Админу
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;// подключение к базе sql
    @Autowired
    UserService userService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/usercreate").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     //  auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(NoOpPasswordEncoder.
     //          getInstance()).usersByUsernameQuery("select username,password,active from user where username = ?")
     //          .authoritiesByUsernameQuery("select u.username,ur.roles from user as u inner join user_roles as ur on u.id = ur.user_id where u.username =?");
        auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());

    }


    //  @Bean
 //  @Override
 //  public UserDetailsService userDetailsService() {
 //      UserDetails user =
 //              User.withDefaultPasswordEncoder()
 //                      .username("Cuckold")
 //                      .password("22")
 //                      .roles("USER")
 //                      .build();

 //      return new InMemoryUserDetailsManager(user);
 //  }
}