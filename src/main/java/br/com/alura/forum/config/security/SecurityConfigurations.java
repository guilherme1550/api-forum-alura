package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
@Profile(value = {"prod", "test"})
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutenticacaoService autenticacaoService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	@Bean // É necessário sobreescrever este método e anotar com o @Bean para poder
			// utilizar injeção de dependência no authenticationManager.
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	// Configurações de autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	// Configurações de Autorização
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll()
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.antMatchers(HttpMethod.GET, "/actuator/**").permitAll() // O correto é deixar o acesso ao Actuator
																		// restrito.
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers(HttpMethod.DELETE, "/topicos/*").hasRole("MODERADOR")
			.anyRequest().authenticated()
			// .and().formLogin(); // É utilizado para criar form de login e cria também
			// sessão. Não é uma boa prática do REST.
			.and().csrf().disable() // CSRF é um tipo de ataque hacker, mas como a autenticação será feita por
									// token, não é necessário realizar essa verificação.
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			// Libera o frameOptions para abrir H2 no navegador
			.and().headers().frameOptions().sameOrigin()
				// Faz com que seja executado primeiro o método AutenticacaoViaTokenFilter
			.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository),
					UsernamePasswordAuthenticationFilter.class);

	}

	// Configurações de recursos estaticos(jss, css, imagens, etc)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**",
				"/swagger-resources/**");
	}

}
