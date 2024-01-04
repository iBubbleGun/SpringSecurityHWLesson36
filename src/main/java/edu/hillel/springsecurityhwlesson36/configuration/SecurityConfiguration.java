package edu.hillel.springsecurityhwlesson36.configuration;

import edu.hillel.springsecurityhwlesson36.enums.Endpoints;
import edu.hillel.springsecurityhwlesson36.enums.RoleTypes;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Contract(" -> new")
    @Bean
    public static @NotNull PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity security, InitialAuthenticationFilter filter) throws Exception {
        LOG.info("Call securityFilterChain() method.");

        security.addFilterAt(filter, BasicAuthenticationFilter.class);

        security.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(Endpoints.PING.getEndpoint()).hasAnyAuthority(RoleTypes.ROLE_USER.getRole())
                .requestMatchers(Endpoints.PRODUCTS.getEndpoint().concat("/**")).hasAnyAuthority(RoleTypes.ROLE_ADMIN.getRole())
                .requestMatchers(Endpoints.USER.getEndpoint().concat("/**")).hasAnyAuthority(RoleTypes.ROLE_ADMIN.getRole())
                .anyRequest().authenticated());

        security.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);

        security.httpBasic(Customizer.withDefaults());

        return security.build();
    }
}
