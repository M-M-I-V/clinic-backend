package dev.mmiv.clinic.controller;

import dev.mmiv.clinic.entity.Users;
import dev.mmiv.clinic.service.UsersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    UsersService usersService;

    public AuthController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return usersService.verifyUser(user);
    }

}
