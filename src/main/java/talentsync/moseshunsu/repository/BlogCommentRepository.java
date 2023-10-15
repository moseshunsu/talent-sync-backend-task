package talentsync.moseshunsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talentsync.moseshunsu.entity.BlogComment;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {
}
