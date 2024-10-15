package com.furkankozmac.salecar.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if ((user.getUsername() == null || user.getUsername().isEmpty()) ||
                (user.getPassword() == null || user.getPassword().isEmpty()) ||
                (user.getEmail() == null || user.getEmail().isEmpty()) ||
                (user.getRole() == null || user.getRole().isEmpty()) ||
                (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty())) {
            throw new IllegalArgumentException("Please provide all the required fields");
        }


        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setPassword(hashPassword(user.getPassword()));

        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        if(!checkPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        return user;
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean checkPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

}
