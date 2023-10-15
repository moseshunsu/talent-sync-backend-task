package talentsync.moseshunsu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import talentsync.moseshunsu.dto.Response;
import talentsync.moseshunsu.dto.UserDto;
import talentsync.moseshunsu.dto.UserRequest;
import talentsync.moseshunsu.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Tag(
        name = "User Controller REST APIs/Endpoint",
        description = "This controller includes endpoints which allow users registration, details update and " +
                "retrieval"
)
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "This endpoint allows registration of users",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "User Code Exists",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Response> createUser(@Validated @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @Operation(
            summary = "This endpoint allows retrieval of user details",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid Id",
                            responseCode = "500"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<UserDto> getUserDetails(@RequestParam String usernameOrEmail) {
        return userService.getUserDetails(usernameOrEmail);
    }

    @Operation(
            summary = "This endpoint allows updating of user details",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid Id",
                            responseCode = "500"
                    )
            }
    )
    @PutMapping
    public ResponseEntity<Response> updateUser(@Validated @RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }
}
