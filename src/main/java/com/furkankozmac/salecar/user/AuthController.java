package com.furkankozmac.salecar.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        User signedUpUser = userService.registerUser(user);
        return ResponseEntity.ok(signedUpUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<User> signin(@RequestBody User user) {
        User signedInUser = userService.loginUser(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(signedInUser);
    }

}
