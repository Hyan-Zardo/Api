package com.projeto.apibiblioteca.controllers;

import com.projeto.apibiblioteca.entities.User;
import com.projeto.apibiblioteca.mappers.UserMapper;
import com.projeto.apibiblioteca.records.UserRecord;
import com.projeto.apibiblioteca.records.UserRequest;
import com.projeto.apibiblioteca.repositories.UserRepository;
import com.projeto.apibiblioteca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService service;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<UserRequest> addUser(@RequestBody UserRequest userRequest) {
        User user = UserMapper.INSTANCE.toUser(userRequest);
        service.registerUser(userRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userRequest);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserRecord> getUser(@PathVariable("id") UUID id) {
        UserRecord userRecord = service.getUserDetails(id);
        return ResponseEntity.ok().body(userRecord);
    }

    @GetMapping("/search")
    public ResponseEntity<UserRecord> searchUser(@RequestParam(required = false) String email, @RequestParam(required = false) String name) {
        return ResponseEntity.ok(service.searchUser(email, name));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletUser(@PathVariable("id") UUID id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
