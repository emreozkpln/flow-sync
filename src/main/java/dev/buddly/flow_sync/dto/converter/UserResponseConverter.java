package dev.buddly.flow_sync.dto.converter;

import dev.buddly.flow_sync.dto.UserResponse;
import dev.buddly.flow_sync.model.UserDb;

public class UserResponseConverter {

    public UserResponse convert(UserDb user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .build();
    }
}
