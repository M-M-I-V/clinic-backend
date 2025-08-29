package dev.mmiv.clinic.controller;

import dev.mmiv.clinic.entity.Users;
import dev.mmiv.clinic.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/")
public class UsersController {

    UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("auth/login")
    public String login(@RequestBody Users user) {
        return usersService.verifyUser(user);
    }

    @PostMapping("admin/add-user")
    public ResponseEntity<String> addUser(@RequestBody Users user) {
        usersService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("admin/users")
    public ResponseEntity<List<Users>> getUsers() {
        return new ResponseEntity<>(usersService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("admin/users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(usersService.findUserById(id), HttpStatus.OK);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody Users user) {
        usersService.updateUser(user);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("admin/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            usersService.deleteUserById(id);
            return ResponseEntity.ok().build();
        }
        catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
        catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getCause());
        }
    }
}