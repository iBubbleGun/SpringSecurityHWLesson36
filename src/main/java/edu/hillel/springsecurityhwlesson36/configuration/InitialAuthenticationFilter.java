package edu.hillel.springsecurityhwlesson36.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hillel.springsecurityhwlesson36.dto.UserDTO;
import edu.hillel.springsecurityhwlesson36.enums.Endpoints;
import edu.hillel.springsecurityhwlesson36.enums.Headers;
import edu.hillel.springsecurityhwlesson36.model.UsernamePasswordAuthentication;
import edu.hillel.springsecurityhwlesson36.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;

@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(InitialAuthenticationFilter.class);
    private final JwtService jwtService;
    private final UsernamePasswordAuthenticationProvider provider;

    public InitialAuthenticationFilter(JwtService jwtService, UsernamePasswordAuthenticationProvider provider) {
        this.jwtService = jwtService;
        this.provider = provider;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws IOException {
        if (request.getHeader(Headers.AUTHORIZATION.getHeader()) == null) {
            LOG.info("Use initial doFilterInternal() rule with header \"" + Headers.AUTHORIZATION.getHeader() + "\".");

            final StringBuilder sb = new StringBuilder();
            final BufferedReader reader = request.getReader();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            final String jsonBody = sb.toString();
            final ObjectMapper mapper = new ObjectMapper();
            final UserDTO userDTO = mapper.readValue(jsonBody, UserDTO.class);
            final String username = userDTO.getUsername();
            final String password = userDTO.getPassword();

            Authentication authentication = new UsernamePasswordAuthentication(username, password);
            authentication = provider.authenticate(authentication);

            final String jwt = jwtService.generatedJwt(authentication);
            response.setHeader(Headers.AUTHORIZATION.getHeader(), "Bearer: ".concat(jwt));
        }
    }

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        LOG.info("Use initial shouldNotFilter() rule.");
        return !request.getServletPath().equals(Endpoints.LOGIN.getEndpoint());
    }
}
