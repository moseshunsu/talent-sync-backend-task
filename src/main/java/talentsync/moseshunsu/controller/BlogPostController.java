package talentsync.moseshunsu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import talentsync.moseshunsu.dto.BlogPostRequest;
import talentsync.moseshunsu.dto.Response;
import talentsync.moseshunsu.entity.BlogPost;
import talentsync.moseshunsu.service.BlogPostService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/blogs")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Blog Post Controller REST APIs/Endpoint",
        description = "This controller includes endpoints which allow users to create, read, search, update, and " +
                "delete blog posts"
)
public class BlogPostController {
    private final BlogPostService blogPostService;

    @Operation(
            summary = "This endpoint allows creation of blog post. Kindly set the newTitle Json Property to null",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Blog Post Code Exists",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Response> createBlog(@RequestBody @Validated BlogPostRequest blogRequest) {
        return blogPostService.createBlog(blogRequest);
    }

    @Operation(
            summary = "This endpoint allows users read about a blog post",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Blog Post Not Found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<BlogPost> readBlog(@RequestParam String author, @RequestParam String title) {
        return blogPostService.readBlog(author, title);
    }

    @Operation(
            summary = "This endpoint allows users search for a blog by author",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Blog Post Not Found",
                            responseCode = "204"
                    )
            }
    )
    @GetMapping("/author")
    public ResponseEntity<Set<BlogPost>> searchByAuthor(@RequestParam String author) {
        return blogPostService.searchByAuthor(author);
    }

    @Operation(
            summary = "This endpoint allows users search for a blog by title",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Blog Post Not Found",
                            responseCode = "204"
                    )
            }
    )
    @GetMapping("/title")
    public ResponseEntity<List<BlogPost>> searchByTitle(@RequestParam String title) {
        return blogPostService.searchByTitle(title);
    }

    @Operation(
            summary = "This endpoint allows blog post owners update their blog post",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Blog Already Exists",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Blog Post Not Found",
                            responseCode = "204"
                    )
            }
    )
    @PutMapping
    public ResponseEntity<Response> updateBlog(@RequestBody @Validated BlogPostRequest blogRequest) {
        return blogPostService.updateBlog(blogRequest);
    }

    @Operation(
            summary = "This endpoint allows blog post owners delete their blog post",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Blog Post Not Found",
                            responseCode = "204"
                    )
            }
    )
    @DeleteMapping
    public ResponseEntity<Response> deleteBlog(@RequestBody BlogPostRequest blogRequest) {
        return blogPostService.deleteBlog(blogRequest);
    }

}
