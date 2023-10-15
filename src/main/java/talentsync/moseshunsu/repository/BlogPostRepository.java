package talentsync.moseshunsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talentsync.moseshunsu.entity.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    boolean existsByTitleContainsIgnoreCase(String title);
    BlogPost findByTitleContainsIgnoreCase(String title);
    boolean existsByAuthorAndTitleAllIgnoreCase(String author, String title);
}
