package com.example.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // phân quyền trên method ở thư mục controller
public class WebSecurityConfig {

    private final String [] PUBLIC_ENDPOINTS = {"/users/**","/log-in","/introspect", "/users/api", "/log-out","/refresh", "/storage/**","/api/posts/search/**", "/api/locations/**"};
    private final String [] PUBLIC_ENDPOINTS_GET = {"/api/posts/getall", "/api/**","/storage/**"};

//    @Value("${jwt.signerKey}")
//    private String signerKey;
    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).permitAll()
                                .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINTS_GET).permitAll()
//                                        .requestMatchers(HttpMethod.GET,"/users/getUsers")
//                                        .hasAuthority("SCOPE_ADMIN") // đường dẫn users chỉ chấp nhập cho role là admin truy cập vào
                                .anyRequest().authenticated()) // với endpoint /customer/** sẽ yêu cầu authenticate

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)) // decoder với token
                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()) // khi token thất bại là hết hạn dùng
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    };

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("http://localhost:5173");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512"); // mã hoá bằng thuật toán bên service
//
//        return NimbusJwtDecoder
//                .withSecretKey(secretKeySpec)
//                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    };
}
