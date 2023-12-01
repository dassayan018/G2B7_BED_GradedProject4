package com.gl.empMgmt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.gl.empMgmt.service.impl.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
	
	protected void configure(HttpSecurity http) throws Exception{
		http
        .authorizeRequests()
            .antMatchers("/employees/new").hasRole("ADMIN") // Access to the new employee form is restricted to ADMIN
            .antMatchers("/employees/edit/**").hasRole("ADMIN") // Access to edit employee forms is restricted to ADMIN
            .antMatchers("/employees/**").authenticated() // All other employee-related pages require authentication
            .anyRequest().permitAll() // All other pages are accessible without authentication
        .and()
        .formLogin()
            .loginPage("/login") // Define a custom login page URL if needed
            .loginProcessingUrl("/authenticateTheUser")
            .permitAll()
            .defaultSuccessUrl("/employees", true) // Redirect to this URL after successful login
        .and()
        .logout()
            .logoutSuccessUrl("/login?logout") // Redirect to this URL after logout
        .and()
        .exceptionHandling()
            .accessDeniedPage("/403"); // Custom 403 access denied page
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserServiceImpl();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

}
