package com.example.joueur.securities;

import authent.modele.Joueur;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
<<<<<<< HEAD:joueur/src/main/java/com/example/joueur/securities/SecurityConfig.java
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
=======
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
>>>>>>> b99f314b8594bebd4ffb1353a94c7c6564c5babb:ServiceJoueur/src/main/java/config/AuthConfig.java
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
<<<<<<< HEAD:joueur/src/main/java/com/example/joueur/securities/SecurityConfig.java
import java.time.temporal.ChronoUnit;
import java.util.Map;
=======
>>>>>>> b99f314b8594bebd4ffb1353a94c7c6564c5babb:ServiceJoueur/src/main/java/config/AuthConfig.java
import java.util.function.Function;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(@Autowired CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Value("${jwt.public.key}")
    RSAPublicKey key;

    @Value("${jwt.private.key}")
    RSAPrivateKey priv;

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
<<<<<<< HEAD:joueur/src/main/java/com/example/joueur/securities/SecurityConfig.java
                .authorizeHttpRequests(autorize -> autorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                        .anyRequest().authenticated())
                .csrf((csrf) -> csrf.disable())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions
=======
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/authentication/inscriprtion").permitAll()
                    .requestMatchers(HttpMethod.POST, "/authentication/login").permitAll()
                    .anyRequest().authenticated()
            )
            .csrf((csrf) -> csrf.ignoringRequestMatchers("/authentication/**"))
            .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(
                    org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                )).exceptionHandling(exception -> exception
>>>>>>> b99f314b8594bebd4ffb1353a94c7c6564c5babb:ServiceJoueur/src/main/java/config/AuthConfig.java
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
<<<<<<< HEAD:joueur/src/main/java/com/example/joueur/securities/SecurityConfig.java
    Function<Joueur, String> genererToken(JwtEncoder jwtEncoder) {
=======
    public PasswordEncoder delegatePasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    Function<Joueur,String> genereTokenFunction(JwtEncoder jwtEncoder) {
>>>>>>> b99f314b8594bebd4ffb1353a94c7c6564c5babb:ServiceJoueur/src/main/java/config/AuthConfig.java
        return joueur -> {
            Instant now = Instant.now();

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
<<<<<<< HEAD:joueur/src/main/java/com/example/joueur/securities/SecurityConfig.java
                    .expiresAt(now.plus(1, ChronoUnit.HOURS))
                    .subject(joueur.getNomJoueur())
                    .claim("scope", "")
=======
                    .expiresAt(now.plusSeconds(expiry))
                    .subject(joueur.getLogin())
                    .claim("roles", "ADMIN")
>>>>>>> b99f314b8594bebd4ffb1353a94c7c6564c5babb:ServiceJoueur/src/main/java/config/AuthConfig.java
                    .build();

            return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        };
    }
}

