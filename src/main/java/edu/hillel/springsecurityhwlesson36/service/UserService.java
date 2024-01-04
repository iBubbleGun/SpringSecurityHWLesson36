package edu.hillel.springsecurityhwlesson36.service;

import edu.hillel.springsecurityhwlesson36.controller.UserController;
import edu.hillel.springsecurityhwlesson36.enums.RoleTypes;
import edu.hillel.springsecurityhwlesson36.model.Role;
import edu.hillel.springsecurityhwlesson36.model.User;
import edu.hillel.springsecurityhwlesson36.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<User> getUser(String username) {
        LOG.info("Call getUser() method with username={} parameter.", username);
        return ResponseEntity.status(HttpStatus.OK).body(
                userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                        "User with name=" + username + " not found!"))
        );
    }

    public ResponseEntity<List<User>> getAllUsers() {
        LOG.info("Call getAllUsers() method.");
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    public ResponseEntity<String> addUser(User user) {
        LOG.info("Call addUser() method with user={} parameter.", user);

        // encoding plain user password with BCrypt password encoder before saving to a database
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        // check user roles and add a default user role if empty
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            addDefaultUserRole(user);
        }

        userRepository.save(user);

        if (userRepository.existsById(user.getId())) {
            LOG.info("New user " + user + " successfully added.");
            return ResponseEntity.status(HttpStatus.OK).body("New user " + user + " successfully added.");
        } else {
            LOG.error("Error occurred while trying to add user: " + user);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error. Please, try again.");
        }
    }

    public ResponseEntity<String> deleteUser(String username) {
        LOG.info("Call deleteUser() method with username={} parameter.", username);
        userRepository.deleteByUsername(username);
        if (userRepository.findByUsername(username).isEmpty()) {
            LOG.info("User with username=" + username + " successfully deleted.");
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User with username=" + username + " successfully deleted.");
        } else {
            LOG.error("Error occurred while trying to delete user with username=" + username + ".");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error occurred while trying to delete user with username=" + username + ".");
        }
    }

    private void addDefaultUserRole(@NotNull User user) {
        LOG.info("Set default role for user=\"{}\".", user.getUsername());

        // default user role for new users is "ROLE_USER"
        final Role defaultRole = new Role();
        defaultRole.setId(RoleTypes.ROLE_USER.getRoleId());
        defaultRole.setName(RoleTypes.ROLE_USER.getRole());
        user.setRoles(List.of(defaultRole));
    }
}
