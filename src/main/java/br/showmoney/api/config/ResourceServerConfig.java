package br.showmoney.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

//@EnableWebSecurity
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


	/*
	 * @Autowired public void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.inMemoryAuthentication()
	 * //.withUser("admin").password("admin").roles("ROLE");
	 * .withUser("admin").password(passwordEncoder().encode("admin")).roles("ROLE");
	 * }
	 */
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				/*
				 * .antMatchers("/categorias").permitAll()
				 * .antMatchers("/armas/armas-fogo/modelos",
				 * "/armas/armas-fogo/salvarClassificacaoAjax",
				 * "/armas/armas-fogo/salvarMarcaAjax", "/agentes/listar")
				 * .hasRole("CADASTRAR_ARMAS") .antMatchers("/usuarios/**", "/patentes/**",
				 * "/agentes/**", "/armas/**") .hasRole("ADMIN")
				 */
				.antMatchers(HttpMethod.POST, "/categorias/**").hasAuthority("ROLE_CADASTRAR_CATEGORIA")
				.antMatchers(HttpMethod.POST, "/categorias/**").hasAuthority("ROLE_CADASTRAR_CATEGORIA")
				.antMatchers(HttpMethod.POST, "/pessoas/**").hasAuthority("ROLE_CADASTRAR_PESSOA")
				.antMatchers(HttpMethod.POST, "/lancamentos/**").hasAuthority("ROLE_CADASTRAR_LANCAMENTO")
				.antMatchers(HttpMethod.PUT, "/pessoas/**").hasAuthority("ROLE_CADASTRAR_PESSOA")
				.antMatchers(HttpMethod.PUT, "/lancamentos/**").hasAuthority("ROLE_CADASTRAR_LANCAMENTO")
				.antMatchers(HttpMethod.DELETE, "/pessoas/**").hasAuthority("ROLE_CADASTRAR_PESSOA")
				.antMatchers(HttpMethod.DELETE, "/lancamentos/**").hasAuthority("ROLE_CADASTRAR_LANCAMENTO")
				.anyRequest().authenticated()
			.and()
				.httpBasic()
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.csrf().disable();
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}
	
	
  @Bean 
  public PasswordEncoder passwordEncoder() { 
  	return new BCryptPasswordEncoder();
  }
  
  @Bean
  public MethodSecurityExpressionHandler createExpressionHandler() {
  	return new OAuth2MethodSecurityExpressionHandler();
  }
	 
	/*
	 * @Bean public PasswordEncoder criptografiaBcrypt() { return new
	 * BCryptPasswordEncoder(); }
	 */
	 
	
	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return
	 * NoOpPasswordEncoder.getInstance(); }
	 */
	 
	
}
