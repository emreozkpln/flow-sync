package dev.buddly.flow_sync.controller;

import dev.buddly.flow_sync.model.Token;
import dev.buddly.flow_sync.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @GetMapping("/{token}")
    public ResponseEntity<Token> getToken(@PathVariable String token){
        Token result = tokenService.findByToken(token);
        return ResponseEntity.ok(result);
    }
}
