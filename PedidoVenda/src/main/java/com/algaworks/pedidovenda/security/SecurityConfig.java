package com.algaworks.pedidovenda.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public AppUserDetailsService userDetailsService() {
		return new AppUserDetailsService();
	}
	
	/**
	 * Configurações de Segurança
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntry.setLoginFormUrl("/Login.xhtml");
		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());
		
		JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
		jsfDeniedEntry.setLoginPath("/AcessoNegado.xhtml");
		jsfDeniedEntry.setContextRelative(true);
		
		http
			 //Desabilita proteção CSRF.
			.csrf().disable()
			
			 //Permite Frame da mesma origem.(Resolve problema de Diálogos)
			.headers().frameOptions().sameOrigin()
			.and()
		
		//Requisições Autorizadas
		.authorizeRequests()
			 // Sem autorização e autenticação.
			.antMatchers("/Login.xhtml", "/Erro.xhtml", "/javax.faces.resource/**").permitAll()
			
			 //Somente usuários autenticados.
			.antMatchers("/Home.xhtml", "/AcessoNegado.xhtml", "/dialogos/**").authenticated()
			
			//Autenticados e Autorizados.
			.antMatchers("/pedidos/**").hasAnyRole("VENDEDORES", "AUXILIARES", "ADMINISTRADORES")
			
			//Autenticados e Autorizados.
			.antMatchers("/produtos/**", "/relatorios/**").hasRole("ADMINISTRADORES")
			.and()
		
		//Formulário de Login
		.formLogin()
			.loginPage("/Login.xhtml")
			.failureUrl("/Login.xhtml?invalid=true")
			.and()
		
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.and()
		
		.exceptionHandling()
			.accessDeniedPage("/AcessoNegado.xhtml")
			.authenticationEntryPoint(jsfLoginEntry)
			.accessDeniedHandler(jsfDeniedEntry);
	}

}
