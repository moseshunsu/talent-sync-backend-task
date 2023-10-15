package talentsync.moseshunsu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import talentsync.moseshunsu.dto.BlogCommentRequest;
import talentsync.moseshunsu.dto.Response;
import talentsync.moseshunsu.entity.BlogComment;
import talentsync.moseshunsu.entity.BlogPost;
import talentsync.moseshunsu.entity.User;
import talentsync.moseshunsu.repository.BlogCommentRepository;
import talentsync.moseshunsu.repository.BlogPostRepository;
import talentsync.moseshunsu.repository.UserRepository;
import talentsync.moseshunsu.service.BlogCommentService;
import talentsync.moseshunsu.utils.ResponseUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogCommentServiceImpl implements BlogCommentService {
    private final BlogCommentRepository blogCommentRepository;
    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Response> addComment(BlogCommentRequest commentRequest) {
        User user = userRepository.findByUsernameOrEmail(commentRequest.getUsernameOrEmail(),
                commentRequest.getUsernameOrEmail()).get();
        Optional<BlogPost> optionalBlogPost = blogPostRepository.findById(commentRequest.getBlogPostId());

        if (user == null || optionalBlogPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BlogComment blogComment = new BlogComment();
        blogComment.setName(user.getName());
        blogComment.setText(commentRequest.getText());

        BlogPost blogPost = optionalBlogPost.get();
        blogPost.getBlogComments().add(blogComment);

        blogCommentRepository.save(blogComment);
        blogPostRepository.save(blogPost);

        return new ResponseEntity<>(
                Response.builder()
                        .responseCode(ResponseUtils.SUCCESS_CODE)
                        .responseMessage(ResponseUtils.BLOG_COMMENT_ADDITION_MESSAGE)
                        .build(), HttpStatus.OK
        );

    }

    @Override
    public ResponseEntity<Response> editComment(BlogCommentRequest commentRequest) {
        Optional<BlogComment> optionalBlogComment = blogCommentRepository.findById(commentRequest.getBlogCommentId());

        if (optionalBlogComment.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        BlogComment blogComment = optionalBlogComment.get();
        blogComment.setText(commentRequest.getText());
        blogCommentRepository.save(blogComment);

        return ResponseEntity.ok().body(
                Response.builder()
                        .responseCode(ResponseUtils.SUCCESS_CODE)
                        .responseMessage(ResponseUtils.DETAILS_UPDATE_SUCCESS_MESSAGE)
                        .build()
        );
    }

    @Override
    public ResponseEntity<Response> deleteComment(BlogCommentRequest commentRequest) {
        Optional<BlogComment> optionalBlogComment = blogCommentRepository.findById(commentRequest.getBlogCommentId());

        if (optionalBlogComment.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        BlogComment blogComment = optionalBlogComment.get();
        blogCommentRepository.delete(blogComment);

        return ResponseEntity.ok().body(
                Response.builder()
                        .responseCode(ResponseUtils.SUCCESS_CODE)
                        .responseMessage(ResponseUtils.BLOG_COMMENT_DELETE_MESSAGE)
                        .build()
        );

    }

}
