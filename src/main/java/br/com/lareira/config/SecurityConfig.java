package br.com.lareira.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.lareira.security.JWTAuthenticationFilter;
import br.com.lareira.security.JWTUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

	@Autowired
    private Environment env;

    @Autowired
    private JWTUtil jwtUtil;

	private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
            "/usuarios/**"
	};

	private static final String[] PUBLIC_MATCHERS_GET = {
			"/usuarios/**",
			"/casais/**"
    };
    
    // Vou dizer quem é o usuário e estamos autenticando e qual o algoritimo de codigicação da senha
    @Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

        // Configuração necessária para que o console do H2 funcione quando o profile em uso seja o test
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

		http.cors().and().csrf().disable(); // Desabilita o CORS e o ataque CSRF (armazenamento de autenticação em Sessão, como nosso sistema é stateless então ele não armazena a sessão)
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // Permite somente para chamadas GET
			.antMatchers(PUBLIC_MATCHERS).permitAll()
            .anyRequest().authenticated(); // Qualquer outra deve estar autenticado
            
        // Adiciona o filtro do usuário
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));

        // Política de armazenamento das sessões, somente stateless
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

    /**
     * Permitindo que requisições de qualquer fonte será permitida em nossa aplicação
     * @return
     */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}