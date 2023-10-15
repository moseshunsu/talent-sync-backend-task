package talentsync.moseshunsu.service;

import org.springframework.http.ResponseEntity;
import talentsync.moseshunsu.dto.BlogPostRequest;
import talentsync.moseshunsu.dto.Response;
import talentsync.moseshunsu.entity.BlogPost;

import java.util.List;
import java.util.Set;

public interface BlogPostService {
    ResponseEntity<Response> createBlog(BlogPostRequest blogRequest);
    ResponseEntity<BlogPost> readBlog(String author, String title);
    ResponseEntity<Set<BlogPost>> searchByAuthor(String author);
    ResponseEntity<List<BlogPost>> searchByTitle(String title);
    ResponseEntity<Response> updateBlog(BlogPostRequest blogRequest);
    ResponseEntity<Response> deleteBlog(BlogPostRequest blogRequest);
    //List of all your post
}
