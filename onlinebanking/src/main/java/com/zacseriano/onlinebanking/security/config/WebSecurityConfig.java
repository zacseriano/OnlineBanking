package com.zacseriano.onlinebanking.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.zacseriano.onlinebanking.exceptions.handler.auth.AuthenticationHandler;
import com.zacseriano.onlinebanking.security.jwt.JwtRequestFilter;

/*
 * Classe que implementa as configurações da Spring Security do Online Banking API
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Autowired
	private AuthenticationHandler authHandler;
	
	/*
	 * Campo que indica para que o filtro de autenticação não impeça o Swagger de funcionar
	 */
	private static final String[] AUTH_WHITELIST = {

            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/users",
            "/auth"
    };
	
	/* 
	 * Método que configura o tipo de autenticação, usando o BCrypt como encoder 
	 * de senha
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	/*
	 * Criando o Bean do AuthenticationManager 
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	/*
	 * Configurando a autorização de todas as URLs da API
	 */
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity.csrf().disable().authorizeRequests()
		.antMatchers("/account/**").hasRole("USER")
		.antMatchers(AUTH_WHITELIST).permitAll()  
		.anyRequest().authenticated().and()
		.exceptionHandling().accessDeniedHandler(authHandler).authenticationEntryPoint(authHandler)
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}		
}