package it.uniroma3.siw.auth;

import it.uniroma3.siw.model.OAuth;
import it.uniroma3.siw.repository.OAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.web.reactive.function.client.WebClient;

import javax.sql.DataSource;

import java.util.*;

import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;
import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource datasource;

    @Autowired
    OAuthRepository oAuthRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // authorization paragraph: qui definiamo chi può accedere a cosa
                .authorizeRequests()
                // chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
                .antMatchers(HttpMethod.GET, "/", "/fragments/**", "/index", "/login","/loginPage", "/logout", "/register","/style.css" ,"/script/**","/images/**", "/show/**").permitAll()
                // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
                .antMatchers(HttpMethod.POST, "/login", "/logout", "/loginPage", "/register").permitAll()
                // solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /admin/**
                .antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                // tutti gli utenti autenticati possono accere alle pagine rimanenti 
                .anyRequest().authenticated()

                // login paragraph: qui definiamo come è gestita l'autenticazione
                // usiamo il protocollo formlogin 
                .and().formLogin()
                // la pagina di login si trova a /login
                // NOTA: Spring gestisce il post di login automaticamente
                .loginPage("/loginPage")
                // se il login ha successo, si viene rediretti al path /index
                .defaultSuccessUrl("/index")

                // logout paragraph: qui definiamo il logout
                .and().logout(l -> l
                        // il logout è attivato con una richiesta GET a "/logout"
                        .logoutUrl("/logout")
                        // in caso di successo, si viene reindirizzati alla /index page
                        .logoutSuccessUrl("/index")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true).permitAll())
                .oauth2Login();

//        http
//                .authorizeRequests(a -> a
//                        .antMatchers("/", "/error", "/webjars/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(e -> e
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
//                .logout(l -> l
//                        .logoutSuccessUrl("/index")
//                        .invalidateHttpSession(true)
//                        .clearAuthentication(true).permitAll()
//                )
//                .oauth2Login();
    }

    /**
     *      * This method provides the SQL queries to get username and password.   
     *  
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                // use the autowired datasource to access the saved credentials
                .dataSource(this.datasource)
                // retrieve username and role
                .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
                // retrieve username, password and a bool specifying whether the user is enabled
                // (always enabled for us)
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    /**
     * This method defines a "passwordEncoder" Component (a Bean in the EE slang).
     * The passwordEncoder Bean is used to encrypt and decrpyt the Credentials passwords.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//OAuth

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService(WebClient rest) {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return request -> {
            OAuth2User user = delegate.loadUser(request);
            if (!"github".equals(request.getClientRegistration().getRegistrationId())) {
                return user;
            }

            OAuth2AuthorizedClient client = new OAuth2AuthorizedClient
                    (request.getClientRegistration(), user.getName(), request.getAccessToken());
            String url = user.getAttribute("organizations_url");
            List<Map<String, Object>> orgs = rest
                    .get().uri(url)
                    .attributes(oauth2AuthorizedClient(client))
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();

            return this.checkUserOrSave(user);

//            if (orgs.stream().anyMatch(org -> "spring-projects".equals(org.get("login")))) {
//                return user;
//            }

//            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "Not in Spring Team", ""));
        };
    }

    @Bean
    public ClientRegistrationRepository oauthRegistrationRepository()
    {
        return null;
    }

    private OAuth2User checkUserOrSave(OAuth2User user) {
        Integer idInt = user.getAttribute("id");
        Long idLong = idInt.longValue();
        Optional<OAuth> oAuth = this.oAuthRepository.findById(idLong);
        OAuth OAuthUser;
        if(oAuth.isPresent())
        {
            OAuthUser = oAuth.get();
            if(OAuthUser.getRole().equals(ADMIN_ROLE))
            {
                Set<OAuth2UserAuthority> authorities = (Set)user.getAuthorities();
                OAuth2UserAuthority actualAuthority = null;
                for (OAuth2UserAuthority authority: authorities) {
                    if(authority.getAuthority().equals("ROLE_USER"))
                    {
                        actualAuthority = authority;
                        break;
                    }
                }
                Set<OAuth2UserAuthority> set = new HashSet<>();
                set.addAll(authorities);
                set.remove(actualAuthority);
                set.add(new OAuth2UserAuthority(ADMIN_ROLE, actualAuthority.getAttributes()));
                DefaultOAuth2User result = new DefaultOAuth2User(set, user.getAttributes(), "id");
                return result;
            }
        }
        else
        {
            OAuthUser = new OAuth();
            OAuthUser.setId(idLong);
            OAuthUser.setNome(user.getAttribute("login"));
            this.oAuthRepository.save(OAuthUser);
        }
        return user;

    }

    @Bean
    public WebClient rest(ClientRegistrationRepository clients, OAuth2AuthorizedClientRepository authz) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clients, authz);
        return WebClient.builder()
                .filter(oauth2).build();
    }

}