package io.github.skshiydv.bankingsystem.controllers;
import io.github.skshiydv.bankingsystem.Entity.User;
import io.github.skshiydv.bankingsystem.Repositories.UserRepository;
import io.github.skshiydv.bankingsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class publicController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail())==null) {
            User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail());
            userService.save(newUser);
            return new ResponseEntity<>("Registered Successfully", HttpStatus.CREATED) ;
        }
        return new ResponseEntity<> ("User already exists",HttpStatus.BAD_REQUEST);
    }

}
