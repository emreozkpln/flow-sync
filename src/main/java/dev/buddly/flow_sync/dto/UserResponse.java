package dev.buddly.flow_sync.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
}
