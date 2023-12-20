package edu.hillel.springsecurityhwlesson36.service;

import edu.hillel.springsecurityhwlesson36.model.User;
import edu.hillel.springsecurityhwlesson36.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class JwtService {

    private final UserRepository userRepository;
    @Value("${jwt.signing.key}")
    private String signingKey;
    @Value("${jwt.key.expiration}")
    private Long jwtExpiration;
    private SecretKey key;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private SecretKey generate() {
        if (key == null) {
            key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        }
        return key;
    }

    public String generatedJwt(@NotNull Authentication authentication) {
        final Optional<User> user = userRepository.findByUsername(authentication.getName());
        return Jwts.builder().setClaims(
                        Map.of("Name", authentication.getName(),
                                "Roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                                "ID", user.isPresent() ? user.get().getId() : -1)
                ).setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .setSubject(authentication.getName())
                .signWith(generate())
                .compact();
    }
}
