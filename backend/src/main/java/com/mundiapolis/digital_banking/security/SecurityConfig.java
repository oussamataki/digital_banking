package com.mundiapolis.digital_banking.security;

import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;

import com.mundiapolis.digital_banking.security.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    // @Value("${jwt.secret}")
    // private String secretKey;

    private UserDetailServiceImpl userDetailServiceImpl;

    // @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        PasswordEncoder passwordEncoder = packagePasswordEncoder();
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("1234")).authorities("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).authorities("USER", "ADMIN")
                        .build());
    }

   // @Bean
    PasswordEncoder packagePasswordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf((csrf) -> csrf.disable())
                .cors(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(ar -> ar.requestMatchers("/auth/login/**","/swagger-ui/**").permitAll())
                .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
                .userDetailsService(userDetailServiceImpl)
                .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()));

        return httpSecurity.build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        String secretKey = "9faa372517ac1d389758d3750fc07acf00f542277f26fec1ce4593e93f64e338";
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
    }

    @Bean
    JwtDecoder jwtDecoder() {
        String secretKey = "9faa372517ac1d389758d3750fc07acf00f542277f26fec1ce4593e93f64e338";
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "RSA");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();

    }




    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source; // Retourne la nouvelle configuration réactive
    }


    //@Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
////        case a caucher pour se rappeler des infos
//        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**","/h2-console/**","/auth/login/**","/customers","/**").permitAll();
//        httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
//        httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
//
//        httpSecurity.cors(Customizer.withDefaults());
//        // .httpBasic(Customizer.withDefaults())
//
//        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
//        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
//        httpSecurity.userDetailsService(userDetailServiceImpl);
//
//
//
//
//        return httpSecurity.build();
//
//
//
//
//    }

    @Bean
    AuthenticationManager authenticationManagr(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(packagePasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);

    }

}
