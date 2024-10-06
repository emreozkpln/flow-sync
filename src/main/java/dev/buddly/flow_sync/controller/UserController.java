package dev.buddly.flow_sync.controller;

import dev.buddly.flow_sync.dto.ChangePasswordRequest;
import dev.buddly.flow_sync.model.UserDb;
import dev.buddly.flow_sync.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ){
        userService.changePassword(request,connectedUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user")
    public ResponseEntity<?> updateUser(
            @RequestBody UserDb user,
            Principal connectedUser
    ){
        userService.updateUser(connectedUser,user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

}
