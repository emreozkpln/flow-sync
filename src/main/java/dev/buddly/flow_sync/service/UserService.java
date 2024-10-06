package dev.buddly.flow_sync.service;

import dev.buddly.flow_sync.dto.ChangePasswordRequest;
import dev.buddly.flow_sync.dto.UserResponse;
import dev.buddly.flow_sync.dto.UserResponseForAdmin;
import dev.buddly.flow_sync.model.UserDb;

import java.security.Principal;
import java.util.List;

public interface UserService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    List<UserResponseForAdmin> findAllUsers();

    UserResponseForAdmin findById(Integer id);

    UserResponse findByUsername(String username);

    void updateUser(Principal connectedUser, UserDb user);

    void deleteUser(Integer id);

    void updateUserforAdmin(UserDb user,Integer id);
}
