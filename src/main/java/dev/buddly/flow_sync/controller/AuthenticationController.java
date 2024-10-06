package dev.buddly.flow_sync.controller;


import dev.buddly.flow_sync.dto.AuthenticationResponse;
import dev.buddly.flow_sync.model.UserDb;
import dev.buddly.flow_sync.security.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserDb request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody UserDb request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
