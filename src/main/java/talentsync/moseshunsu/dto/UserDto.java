package talentsync.moseshunsu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private int age;
    private LocalDateTime createdAt;

    public UserDto() {
        super();
    }

}
