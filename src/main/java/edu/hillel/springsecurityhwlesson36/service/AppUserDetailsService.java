package edu.hillel.springsecurityhwlesson36.service;

import edu.hillel.springsecurityhwlesson36.model.AppUserDetails;
import edu.hillel.springsecurityhwlesson36.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(AppUserDetailsService.class);
    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("Try to receive UserDetails for user \"{}\".", username);
        return new AppUserDetails(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User \"" + username + "\" not found!")));
    }
}
