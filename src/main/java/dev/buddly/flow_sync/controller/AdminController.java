package dev.buddly.flow_sync.controller;

import dev.buddly.flow_sync.dto.ResponseHandler;
import dev.buddly.flow_sync.dto.UserResponseForAdmin;
import dev.buddly.flow_sync.model.UserDb;
import dev.buddly.flow_sync.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin-only")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Admin")
public class AdminController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> adminlogin() {
        return ResponseEntity.ok("admin page");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseForAdmin>> findAllUsers(){
        List<UserResponseForAdmin> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseForAdmin> findUserById(@PathVariable Integer id){
        UserResponseForAdmin user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseHandler.responseBuilder("User deleted success", HttpStatus.OK, id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestBody UserDb user){
        userService.updateUserforAdmin(user,id);
        return ResponseHandler.responseBuilder("User updated success", HttpStatus.OK, user);
    }

}
