package dev.mmiv.clinic.controller;

import dev.mmiv.clinic.entity.Users;
import dev.mmiv.clinic.service.UsersService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
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
