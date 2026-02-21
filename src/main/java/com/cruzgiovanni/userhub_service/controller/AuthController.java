package com.cruzgiovanni.userhub_service.controller;

import com.cruzgiovanni.userhub_service.dto.request.AuthDTO;
import com.cruzgiovanni.userhub_service.dto.request.RegisterDTO;
import com.cruzgiovanni.userhub_service.dto.response.LoginResponseDTO;
import com.cruzgiovanni.userhub_service.infrastructure.entities.User.User;
import com.cruzgiovanni.userhub_service.infrastructure.repositories.UserRepository;
import com.cruzgiovanni.userhub_service.infrastructure.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) Objects.requireNonNull(auth.getPrincipal()));

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {
        if (this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        this.userRepository.save(User.builder()
                .login(data.login())
                .email(data.email())
                .name(data.name())
                .password(encryptedPassword)
                .role(data.role())
                .build());

        return ResponseEntity.ok().build();

    }

}
