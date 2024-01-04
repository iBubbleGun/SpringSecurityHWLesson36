package edu.hillel.springsecurityhwlesson36.controller;

import edu.hillel.springsecurityhwlesson36.model.User;
import edu.hillel.springsecurityhwlesson36.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{username}")
    public User getUser(@PathVariable("username") String username) {
        return userService.getUser(username).getBody();
    }

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        return userService.addUser(user).getBody();
    }

    @DeleteMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        return userService.deleteUser(username).getBody();
    }
}
