package peaksoft.springbootproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.springbootproject.dto.*;
import peaksoft.springbootproject.entity.User;
import peaksoft.springbootproject.repository.UserRepository;
import peaksoft.springbootproject.security.jwt.JwtTokenUtil;
import peaksoft.springbootproject.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
@Tag(name = "Auth Api", description = "we can login and registration")

public class AuthController {

    private final UserService userServise;
    private final UserRepository repository;
    private final JwtTokenUtil jwtTokenUtil;
    private final LoginMapper loginMapper;
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    @Operation(summary = "login", description = "user can login")
    public ResponseEntity<LoginResponse> getLogin(@RequestBody LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = repository.findByEmail(token.getName()).get();
            return ResponseEntity.ok().body(loginMapper.loginView(jwtTokenUtil.generateToken(user), ValidationType.SUCCESSFUL, user));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginMapper.loginView("", ValidationType.LOGIN_FAILED, null));
        }
    }

    @PostMapping("registration")
    @Operation(summary = "registration", description = "user can registration")
    public StudentResponse creat(@RequestBody StudentRequest request) {
        return userServise.register(request);
    }

}
