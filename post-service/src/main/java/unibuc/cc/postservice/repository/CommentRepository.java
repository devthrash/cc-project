package unibuc.cc.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibuc.cc.postservice.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
