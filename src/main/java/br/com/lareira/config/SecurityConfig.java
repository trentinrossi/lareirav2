package br.com.lareira.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.lareira.security.JWTAuthenticationFilter;
import br.com.lareira.security.JWTAuthorizationFilter;
import br.com.lareira.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private Environment env;

    @Autowired
    private JWTUtil jwtUtil;

    private static final String[] PUBLIC_MATCHERS = { "/h2-console/**", "logout/**" };

    // Retorna as URL's que são permitidas somente com o GET
    private static final String[] PUBLIC_MATCHERS_GET = {};

    // Retorna as URL's que são permitidas somente com o POST
    private static final String[] PUBLIC_MATCHERS_POST = { "/usuarios*" };

    // Vou dizer quem é o usuário e estamos autenticando e qual o algoritimo de
    // codigicação da senha
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Necessário para o Swagger
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
                "/swagger-ui.html", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Configuração necessária para que o console do H2 funcione quando o profile em
        // uso seja o test
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.cors().and().csrf().disable(); // Desabilita o CORS e o ataque CSRF (armazenamento de autenticação em
                                            // Sessão, como nosso sistema é stateless então ele não armazena a sessão)
        // http.cors().disable(); // Desabilita o CORS e o ataque CSRF (armazenamento de
        // autenticação em Sessão, como nosso sistema é stateless então ele não armazena
        // a sessão)

        http.authorizeRequests().antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll() // Permite somente para
                                                                                                // chamadas POST
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // Permite somente para chamadas GET
                .antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated() // Qualquer outra deve estar
                                                                                       // autenticado
                .and().logout().logoutSuccessUrl("/login").permitAll();

        // Adiciona o filtro de autenticação
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));

        // Adiciona o filtro de autorização
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));

        // Política de armazenamento das sessões, somente stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Permitindo que requisições de qualquer fonte será permitida em nossa
     * aplicação
     * 
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration conf = new CorsConfiguration().applyPermitDefaultValues();
        conf.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", conf);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}