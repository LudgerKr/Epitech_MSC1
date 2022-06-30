package fr.epitech.cash.manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // Prepare the UserDetailsService
    @Resource(name = "userDetailsService")
    private UserDetailsService userDetailsService;

    // Prepare the PasswordEncoder
    @Bean(name = "passwordEncoderSecurity")
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Use a Java class for authentication
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }


    // Prevent all problem for CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));

        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList(
                RequestMethod.POST.name(),
                RequestMethod.PATCH.name(),
                RequestMethod.PUT.name(),
                RequestMethod.DELETE.name(),
                RequestMethod.GET.name(),
                RequestMethod.OPTIONS.name(),
                RequestMethod.TRACE.name(),
                RequestMethod.HEAD.name()
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    // Manage the security of the api
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.sessionManagement().sessionFixation().none();

        httpSecurity
                    .csrf()
                    .disable()
                    .cors()
                .and()
                    .formLogin()
                    // The address for Login
                    .loginProcessingUrl("/api/signin")
                    .successHandler(new AuthentificationLoginSuccessHandler())
                    .failureHandler(new AuthenticationLoginFailureHandler())
                .and()
                    .logout()
                    // The address for Logout
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler(new AuthentificationLogoutSuccessHandler())
                    .invalidateHttpSession(true)
                .and()
                    .authorizeRequests()
                    // The address which need an authentication for using
                    .antMatchers("/api/auth/**")
                    .authenticated()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new CustomRestAuthenticationEntryPoints())
                    .accessDeniedHandler(new CustomRestAccessDeniedHandlers())
                .and()
                    .authorizeRequests()
                    .anyRequest()
                    .permitAll();
    }

    // Custom the denied access response
    private static class CustomRestAuthenticationEntryPoints implements AuthenticationEntryPoint
    {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().print("You need to login first in order to perform this action.");
        }
    }

    // Custom the denied access response if the role does not match
    public static class CustomRestAccessDeniedHandlers implements AccessDeniedHandler
    {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException arg2) throws IOException
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().print("You don't have required role to perform this action.");
        }
    }

    // Custom the access response
    private static class AuthentificationLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    // Custom the failed access response
    private static class AuthenticationLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // Custom the logout response
    private static class AuthentificationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

}