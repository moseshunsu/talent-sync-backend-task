package talentsync.moseshunsu.service;

import org.springframework.http.ResponseEntity;
import talentsync.moseshunsu.dto.BlogCommentRequest;
import talentsync.moseshunsu.dto.Response;

public interface BlogCommentService {
    ResponseEntity<Response> addComment(BlogCommentRequest commentRequest);
    ResponseEntity<Response> editComment(BlogCommentRequest commentRequest);
    ResponseEntity<Response> deleteComment(BlogCommentRequest commentRequest);
}
