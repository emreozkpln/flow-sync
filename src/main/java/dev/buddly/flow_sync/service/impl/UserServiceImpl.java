package dev.buddly.flow_sync.service.impl;

import dev.buddly.flow_sync.dto.ChangePasswordRequest;
import dev.buddly.flow_sync.dto.UserResponse;
import dev.buddly.flow_sync.dto.UserResponseForAdmin;
import dev.buddly.flow_sync.dto.converter.UserResponseForAdminConverter;
import dev.buddly.flow_sync.exception.AlreadyExistsException;
import dev.buddly.flow_sync.exception.NotFoundException;
import dev.buddly.flow_sync.model.UserDb;
import dev.buddly.flow_sync.repository.UserRepository;
import dev.buddly.flow_sync.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserResponseForAdminConverter userResponseForAdmin;

    @Override
    public void changePassword(
            ChangePasswordRequest request,
            Principal connectedUser
    ) {
        var user = (UserDb) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        //check if the current password is correct

        if (!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())){
            log.error("Wrong password");
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())){
            log.error("Password not same");
            throw new IllegalStateException("Password not same");
        }

        //update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        //save the new password
        userRepository.save(user);
    }

    @Override
    public void updateUser(
            Principal connectedUser,
            UserDb user
    ) {
        var user2 = (UserDb) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        user2.setFirstName(user.getFirstName());
        user2.setLastName(user.getLastName());
        user2.setUsername(user.getUsername());

        userRepository.save(user2);
    }

    @Override
    public List<UserResponseForAdmin> findAllUsers() {
        List<UserDb> users = userRepository.findAll();
        return users
                .stream()
                .map(userResponseForAdmin::convert)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseForAdmin findById(Integer id) {
        UserDb user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        return userResponseForAdmin.convert(user);
    }

    @Override
    public UserResponse findByUsername(String username) {
        UserDb user = userRepository.findByUsername(username)
                .orElseThrow(()->new NotFoundException("User not found"));
        return modelMapper.map(user,UserResponse.class);
    }


    @Override
    public void deleteUser(Integer id) {
        UserDb user = userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public void updateUserforAdmin(UserDb user, Integer id) {
        UserDb user2 = userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User not found"));
        UserDb existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if(existingUser != null && !existingUser.getId().equals(user2.getId())){
            throw new AlreadyExistsException("User already exist");
        }
        user2.setFirstName(user.getFirstName());
        user2.setLastName(user.getLastName());
        user2.setUsername(user.getUsername());
        if (!user.getPassword().equals(user2.getPassword())) {
            user2.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user2.setApproved(user.isApproved());
        user2.setRole(user.getRole());
        userRepository.save(user2);
    }
}
