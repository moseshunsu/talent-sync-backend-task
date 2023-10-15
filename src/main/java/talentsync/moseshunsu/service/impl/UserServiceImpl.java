package talentsync.moseshunsu.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import talentsync.moseshunsu.dto.Response;
import talentsync.moseshunsu.dto.UserDto;
import talentsync.moseshunsu.dto.UserRequest;
import talentsync.moseshunsu.entity.User;
import talentsync.moseshunsu.repository.UserRepository;
import talentsync.moseshunsu.service.UserService;
import talentsync.moseshunsu.utils.ResponseUtils;
import talentsync.moseshunsu.utils.Role;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<Response> createUser(UserRequest userRequest) {

        boolean isMailExists = userRepository.existsByEmail(userRequest.getEmail());
        boolean isUserNameExists = userRepository.existsByUsername(userRequest.getUsername());
        boolean isPhoneNumber = userRepository.existsByPhoneNumber(userRequest.getPhoneNumber());

        if (isMailExists) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                    .responseCode(ResponseUtils.MAIL_EXISTS_CODE)
                    .responseMessage(ResponseUtils.MAIL_EXISTS_MESSAGE)
                    .build()
            );
        } else if (isUserNameExists) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                    .responseCode(ResponseUtils.USERNAME_EXISTS_CODE)
                    .responseMessage(ResponseUtils.USERNAME_EXISTS_MESSAGE)
                    .build()
            );
        } else if (isPhoneNumber) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                    .responseCode(ResponseUtils.PHONE_NUMBER_EXISTS_CODE)
                    .responseMessage(ResponseUtils.PHONE_NUMBER_EXISTS_MESSAGE)
                    .build()
            );
        }

        User newUser = new User();
        newUser.setName(userRequest.getName());
        newUser.setUsername(userRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setEmail(userRequest.getEmail());
        newUser.setPhoneNumber(userRequest.getPhoneNumber());
        newUser.setRole(Role.USER);
        newUser.setAge(userRequest.getAge());

        userRepository.save(newUser);

        return new ResponseEntity<>(
                Response.builder()
                .responseCode(ResponseUtils.SUCCESS_CODE)
                .responseMessage(ResponseUtils.USER_REGISTRATION_SUCCESS_MESSAGE)
                .build(), HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<UserDto> getUserDetails(String usernameOrEmail) {
        User user = this.userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get();
        return ResponseEntity.ok().body(this.modelMapper.map(user, UserDto.class));
    }

    @Override
    public ResponseEntity<Response> updateUser(UserRequest userRequest) {
        User fetchedUser = userRepository.findByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail()).get();

        fetchedUser.setName(userRequest.getName());
        fetchedUser.setUsername(userRequest.getUsername());
        fetchedUser.setPassword(userRequest.getPassword());
        fetchedUser.setEmail(userRequest.getEmail());
        fetchedUser.setPhoneNumber(userRequest.getPhoneNumber());
        fetchedUser.setAge(userRequest.getAge());

        userRepository.save(fetchedUser);

        return ResponseEntity.ok().body(
                Response.builder()
                .responseCode(ResponseUtils.SUCCESS_CODE)
                .responseMessage(ResponseUtils.DETAILS_UPDATE_SUCCESS_MESSAGE)
                .build()
        );
    }

}
