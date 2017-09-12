package com.textbookvalet.ui.www.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.textbookvalet.ui.www.springboot.Application;
 

@Configuration
@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	AuthenticationManagerExtended authenticationManager; 
	 
	@Autowired 
	LogoutHandlerExtended logoutHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.parentAuthenticationManager(authenticationManager);  
    } 
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	SimpleUrlAuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
    	authenticationFailureHandler.setDefaultFailureUrl("/login");
    	authenticationFailureHandler.setAllowSessionCreation(true); 
    	
        http
        	//.csrf().disable()
        	
            .authorizeRequests().
            	antMatchers("/**").permitAll().
            	antMatchers(Application.BASE_REST_API + "/**").permitAll().
            	antMatchers("/swagger*").permitAll().
            	antMatchers("/webjars*").permitAll().
            	antMatchers("/home/**").hasAnyRole("ADMIN")
             .anyRequest().authenticated() //Anything else must be authenticated
                .and()
                //.authenticationProvider(new AuthenticationProviderExtended())
                //.addFilter(filter)
             .formLogin() 
                .loginPage("/login")
                .loginProcessingUrl("/performLogin")
                .failureUrl("/login")
                .authenticationDetailsSource(new CustomWebAuthenticationDetailsSource())
                .failureHandler(authenticationFailureHandler)
                .defaultSuccessUrl("/home", true)
                .usernameParameter("email").passwordParameter("password") 
                .permitAll()
                .and()
            .logout()
            	.logoutUrl("/logout")
            	.logoutSuccessUrl("/login")
            	.addLogoutHandler(logoutHandler)
            	.and()
             .sessionManagement()
             	 .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .expiredUrl("/login"); 
        
        http.csrf().ignoringAntMatchers(Application.BASE_REST_API + "/**", "/swagger**", "/webjars/**");
       
       // http.rememberMe().rememberMeServices(rememberMeServices()).key("password");
    }
    
    /*
    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("password", userService);
        rememberMeServices.setCookieName("cookieName");
        rememberMeServices.setParameter("rememberMe");
        return rememberMeServices;
    }
    */
}