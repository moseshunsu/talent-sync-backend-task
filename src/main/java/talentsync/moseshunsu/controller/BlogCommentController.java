package talentsync.moseshunsu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import talentsync.moseshunsu.dto.BlogCommentRequest;
import talentsync.moseshunsu.dto.Response;
import talentsync.moseshunsu.service.BlogCommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/comments")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Blog Comment Controller REST APIs/Endpoint",
        description = "This controller includes endpoints which allow users to add, edit, and delete comments"
)
public class BlogCommentController {
    private final BlogCommentService blogCommentService;

    @Operation(
            summary = "This endpoint allows addition of comments",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Response> addComment(@RequestBody @Validated BlogCommentRequest commentRequest) {
        return blogCommentService.addComment(commentRequest);
    }

    @Operation(
            summary = "This endpoint allows editing of comments",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Comment Not Found",
                            responseCode = "204"
                    )
            }
    )
    @PutMapping
    public ResponseEntity<Response> editComment(@RequestBody @Validated BlogCommentRequest commentRequest) {
        return blogCommentService.editComment(commentRequest);
    }

    @Operation(
            summary = "This endpoint allows deletion of comments",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Comment Not Found",
                            responseCode = "204"
                    )
            }
    )
    @DeleteMapping
    public ResponseEntity<Response> deleteComment(@RequestBody @Validated BlogCommentRequest commentRequest) {
        return blogCommentService.deleteComment(commentRequest);
    }

}
