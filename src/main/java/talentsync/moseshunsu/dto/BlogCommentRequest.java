package talentsync.moseshunsu.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BlogCommentRequest {

    private Long blogCommentId;

    private Long blogPostId;

    @NotNull(message = "username or email must not be null")
    private String usernameOrEmail;

    @Pattern(regexp = "^.{2,}$", message = "text must be more than 2 characters")
    private String text;

}
