package talentsync.moseshunsu.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BlogPostRequest {

    @NotNull(message = "username or email must not be null")
    private String usernameOrEmail;

    @NotNull(message = "title must not be null")
    @Pattern(regexp = "^.{2,}$", message = "blog title must be more than 2 characters")
    private String title;

    @NotNull(message = "content must not be null")
    @Pattern(regexp = "^.{50,5000}$", message = "blog content must be between 50 characters and 5000 characters")
    private String content;

    @Pattern(regexp = "^.{2,}$", message = "blog title must be more than 2 characters")
    private String newTitle;
}
