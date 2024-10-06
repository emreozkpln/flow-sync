package dev.buddly.flow_sync.dto.converter;

import dev.buddly.flow_sync.dto.UserResponseForAdmin;
import dev.buddly.flow_sync.model.UserDb;
import org.springframework.stereotype.Component;

@Component
public class UserResponseForAdminConverter {

    public UserResponseForAdmin convert(UserDb user) {
        return UserResponseForAdmin.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .isApproved(user.isApproved())
                .password(user.getPassword())
                .build();
    }
}
