package com.apitran.tran.controllers;

import com.apitran.tran.models.dto.ContactDTO;
import com.apitran.tran.models.dto.UserRQDTO;
import com.apitran.tran.services.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = UserController.PATH)
public class UserController {

    protected static final String PATH = "/api/v1/user";

    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRQDTO user) {
        return userService.createUser(user);
    }

    @PostMapping(value = "/{userId}/save-contacts")
    public ResponseEntity<?> saveContacts(@PathVariable Long userId, @RequestBody List<ContactDTO> contacts) {
        return userService.saveContacts(userId, contacts);
    }

    @GetMapping(value = "common-contact/{userIdA}/{userIdB}")
    public ResponseEntity<?> obtainCommonContacts(@PathVariable Long userIdA, @PathVariable Long userIdB) {
        return userService.obtainCommonContacts(userIdA, userIdB);
    }

    @GetMapping(value = "contacts/{userId}")
    public ResponseEntity<?> getContactsByUser(@PathVariable Long userId) {
        return userService.getContactsByUser(userId);
    }
}
