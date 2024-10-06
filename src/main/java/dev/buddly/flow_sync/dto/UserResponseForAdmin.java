package dev.buddly.flow_sync.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseForAdmin {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
    private boolean isApproved;
}
