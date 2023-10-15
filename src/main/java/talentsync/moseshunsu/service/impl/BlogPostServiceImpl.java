package talentsync.moseshunsu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import talentsync.moseshunsu.dto.BlogPostRequest;
import talentsync.moseshunsu.dto.Response;
import talentsync.moseshunsu.entity.BlogPost;
import talentsync.moseshunsu.entity.User;
import talentsync.moseshunsu.repository.BlogPostRepository;
import talentsync.moseshunsu.repository.UserRepository;
import talentsync.moseshunsu.service.BlogPostService;
import talentsync.moseshunsu.utils.ResponseUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {
    private final BlogPostRepository blogRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Response> createBlog(BlogPostRequest blogRequest) {

        User user = userRepository.findByUsernameOrEmail(blogRequest.getUsernameOrEmail(),
                blogRequest.getUsernameOrEmail()).get();

        boolean isBlogExist = blogRepository.existsByAuthorAndTitleAllIgnoreCase(user.getName(),
                blogRequest.getTitle());

        if (isBlogExist) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .responseCode(ResponseUtils.BLOG_EXISTS_CODE)
                            .responseMessage(ResponseUtils.BLOG_EXISTS_MESSAGE)
                            .build()
            );
        }

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(blogRequest.getTitle());
        blogPost.setContent(blogRequest.getContent());
        blogPost.setAuthor(user.getName());
        blogPost.setUser(user);

        blogRepository.save(blogPost);

        return new ResponseEntity<>(
                Response.builder()
                        .responseCode(ResponseUtils.SUCCESS_CODE)
                        .responseMessage(ResponseUtils.BLOG_CREATION_SUCCESS_MESSAGE)
                        .build(), HttpStatus.CREATED
        );

    }

    @Override
    public ResponseEntity<BlogPost> readBlog(String author, String title) {
        return blogRepository.findAll()
                .stream()
                .filter(blogPost -> blogPost.getAuthor().equalsIgnoreCase(author) &&
                                    blogPost.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet( () -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Set<BlogPost>> searchByAuthor(String author) {
        Set<BlogPost> blogPosts = blogRepository.findAll()
                .stream()
                .filter(blogPost -> blogPost.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toSet());

        if (blogPosts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(blogPosts);
        }
    }

    @Override
    public ResponseEntity<List<BlogPost>> searchByTitle(String title) {
        List<BlogPost> blogPosts = blogRepository.findAll()
                .stream()
                .filter(blogPost -> blogPost.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();

        if (blogPosts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(blogPosts);
        }
    }

    @Override
    public ResponseEntity<Response> updateBlog(BlogPostRequest blogRequest) {

        User user = userRepository.findByUsernameOrEmail(blogRequest.getUsernameOrEmail(),
                blogRequest.getUsernameOrEmail()).get();

        boolean isBlogExist = blogRepository.findAll()
                .stream()
                .anyMatch(blogPost -> blogPost.getTitle().equalsIgnoreCase(blogRequest.getTitle()) &&
                                      blogPost.getAuthor().equalsIgnoreCase(user.getName()));

        if (!isBlogExist) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .responseCode(ResponseUtils.BLOG_NOT_FOUND_CODE)
                            .responseMessage(ResponseUtils.BLOG_NOT_FOUND_MESSAGE)
                            .build()
            );
        }

        boolean isTitleExist = blogRepository.existsByAuthorAndTitleAllIgnoreCase(user.getName(),
                blogRequest.getNewTitle());

        if (isTitleExist) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .responseCode(ResponseUtils.BLOG_EXISTS_CODE)
                            .responseMessage(ResponseUtils.BLOG_EXISTS_MESSAGE)
                            .build()
            );
        }

        BlogPost fetchedBlog = blogRepository.findByTitleContainsIgnoreCase(blogRequest.getTitle());
        fetchedBlog.setTitle(blogRequest.getNewTitle());
        fetchedBlog.setContent(blogRequest.getContent());
        blogRepository.save(fetchedBlog);

        return ResponseEntity.ok().body(
                Response.builder()
                        .responseCode(ResponseUtils.SUCCESS_CODE)
                        .responseMessage(ResponseUtils.DETAILS_UPDATE_SUCCESS_MESSAGE)
                        .build()
        );
    }

    @Override
    public ResponseEntity<Response> deleteBlog(BlogPostRequest blogRequest) {
        User user = userRepository.findByUsernameOrEmail(blogRequest.getUsernameOrEmail(),
                blogRequest.getUsernameOrEmail()).get();

        Optional<BlogPost> blog = blogRepository.findAll()
                .stream()
                .filter(blogPost -> blogPost.getAuthor().equals(user.getName()) &&
                                    blogPost.getTitle().equalsIgnoreCase(blogRequest.getTitle()))
                .findFirst();

        if (blog.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .responseCode(ResponseUtils.BLOG_NOT_FOUND_CODE)
                            .responseMessage(ResponseUtils.BLOG_NOT_FOUND_MESSAGE)
                            .build()
            );
        }

        blogRepository.delete(blog.get());
        return ResponseEntity.ok().body(
                Response.builder()
                        .responseCode(ResponseUtils.SUCCESS_CODE)
                        .responseMessage(ResponseUtils.BLOG_DELETE_MESSAGE)
                        .build()
        );
    }
}
