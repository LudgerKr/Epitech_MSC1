package fr.epitech.cash.manager.controller;

import fr.epitech.cash.manager.dto.UserDto;
import fr.epitech.cash.manager.repository.UserRepository;
import fr.epitech.cash.manager.service.UserService;
import fr.epitech.cash.manager.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    // Prepare the UserService
    @Autowired
    private UserService userService;

    // Prepare the UserRepositoy
    @Autowired
    private UserRepository userRepository;

    // Register User
    @PostMapping(value = "/api/users/signup")
    public ResponseEntity<UserDto> createUser(
            @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password,
            @RequestParam String street, @RequestParam String city, @RequestParam String zipCode, @RequestParam String phoneNumber,
            @RequestParam Integer role_id)
    {
        // User UserUtil custom class for check the email with regex
        if (!UserUtil.validate(email))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        UserDto userDto = userService.createUser(
                firstName, lastName, email, password,
                street, city, zipCode, phoneNumber, role_id
        );
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    // Delete an user with its id
    @DeleteMapping(value = "/api/auth/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {

        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all users
    @GetMapping(value = "/api/auth/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtoList = userService.getUsers();

        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    // Get an user with its id
    @GetMapping(value = "/api/auth/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id) {
        UserDto userDto = userService.getUser(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // Get an user with its email
    @GetMapping(value = "/api/auth/users/email/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        UserDto userDto = userService.getUserbyEmail(email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // Update an user with its id
    @PatchMapping("/api/auth/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") Integer id,
                                             @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}