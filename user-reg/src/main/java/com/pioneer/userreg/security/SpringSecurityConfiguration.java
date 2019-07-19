package com.pioneer.userreg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.pioneer.userreg.service.AppUserServiceImpl;


//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration /*extends WebSecurityConfigurerAdapter*/ {

		@Autowired
		private AppUserServiceImpl appUserServiceImpl;
		
		//@Override
		protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
			authenticationManagerBuilder.userDetailsService(appUserServiceImpl);
		}
		
		//@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception{
			httpSecurity
            .cors()
            .and()
            .csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/user/**")
            .authenticated()
            .antMatchers("/",
                    "/favicon.ico",
                    "/**/*.png",
                    "/**/*.gif",
                    "/**/*.svg",
                    "/**/*.jpg",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js")
            .permitAll()
            .antMatchers("/user")
            .permitAll()
            .antMatchers("/h2-console/**")
            .permitAll()
            .anyRequest()
            .authenticated();
			
			httpSecurity.headers().frameOptions().disable();

			
		}
}
