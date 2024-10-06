package dev.buddly.flow_sync.security;


import dev.buddly.flow_sync.dto.AuthenticationResponse;
import dev.buddly.flow_sync.model.Role;
import dev.buddly.flow_sync.model.Token;
import dev.buddly.flow_sync.model.UserDb;
import dev.buddly.flow_sync.repository.TokenRepository;
import dev.buddly.flow_sync.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(UserDb request) {

        // check if user already exist. if exist than authenticate the user
        if(repository.findByUsername(request.getUsername()).isPresent()) {
            log.error("User already exists");
            return new AuthenticationResponse(null, "User already exist");
        }

        UserDb user = new UserDb();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        repository.save(user);
        /*String jwt = jwtService.generateToken(user);
            saveUserToken(jwt, user);
            return new AuthenticationResponse(jwt, "Admin registration was successful");*/
        log.info("User registration was successful");
        return new AuthenticationResponse(null, "User registration was successful");

    }

    public AuthenticationResponse authenticate(UserDb request) {
        log.info(String.valueOf(request));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            log.error("Invalid username or password");
            return new AuthenticationResponse(null, "Invalid username or password");
        }

        UserDb user = repository.findByUsername(request.getUsername()).orElseThrow();
        if (!user.isApproved()){
            log.error("User not approved");
            return new AuthenticationResponse(null,"User not approved");
        }

        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        log.info("User login was successful");
        return new AuthenticationResponse(jwt, "User login was successful");
    }

    private void revokeAllTokenByUser(UserDb user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String jwt, UserDb user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
