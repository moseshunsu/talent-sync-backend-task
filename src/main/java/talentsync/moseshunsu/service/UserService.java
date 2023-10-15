package talentsync.moseshunsu.service;

import org.springframework.http.ResponseEntity;
import talentsync.moseshunsu.dto.Response;
import talentsync.moseshunsu.dto.UserDto;
import talentsync.moseshunsu.dto.UserRequest;

public interface UserService {
    ResponseEntity<Response> createUser(UserRequest userRequest);
    ResponseEntity<UserDto> getUserDetails(String usernameOrEmail);
    ResponseEntity<Response> updateUser(UserRequest userRequest);
}
