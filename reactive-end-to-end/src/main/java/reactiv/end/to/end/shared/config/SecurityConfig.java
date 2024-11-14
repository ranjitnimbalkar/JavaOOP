package reactiv.end.to.end.shared.config;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import reactiv.end.to.end.shared.security.AppAuthenticationManager;
import reactiv.end.to.end.shared.security.AppSecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppAuthenticationManager authenticationManager;
    private final AppSecurityContextRepository securityContextRepository;

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {

        return serverHttpSecurity.cors(getCorsSpecCustomizer())
                  .authenticationManager(authenticationManager)
                  .securityContextRepository(securityContextRepository)
                  .authorizeExchange() //
                  .pathMatchers(HttpMethod.GET, //
                          "/", //
                          "", //
                          "/index.html", //
                          "/swagger-ui/**", //
                          "/swagger-ui.html", //
                          "/webjars/swagger-ui/**",
                          "/v3/api-docs/**", //
                          "/auth/token/**",
                          "/actuator/**") //
                 .permitAll() //
                 .anyExchange().authenticated() //
                 .and() //
                 .formLogin().disable() //
                 .logout().disable() //
                 .httpBasic().disable() //
                 .csrf().disable() //
                 .exceptionHandling()
                 .authenticationEntryPoint((exchange, exception) -> executeInRunnable(exchange, HttpStatus.UNAUTHORIZED))
                 .accessDeniedHandler((exchange, denied) -> executeInRunnable(exchange, HttpStatus.FORBIDDEN))
                 .and()
                 .build();//
    }

    @NotNull
    private Mono<Void> executeInRunnable(ServerWebExchange exchange, HttpStatus status) {
        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(status));
    }

    @NotNull
    private Customizer<ServerHttpSecurity.CorsSpec> getCorsSpecCustomizer() {
        return corsSpec -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedOrigin("*");
            configuration.addAllowedMethod(HttpMethod.GET);
            configuration.addAllowedMethod(HttpMethod.POST);
            configuration.addAllowedHeader("*");
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            corsSpec.configurationSource(source);
        };
    }

}
