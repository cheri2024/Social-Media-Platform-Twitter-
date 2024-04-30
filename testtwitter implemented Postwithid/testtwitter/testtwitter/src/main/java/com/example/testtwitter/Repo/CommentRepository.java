package com.example.testtwitter.Repo;
import com.example.testtwitter.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);
    @Query("SELECT new Comment(c.id, c.commentBody, c.userID) FROM Comment c WHERE c.id = ?1")
    Optional<Comment> findCommentDetailsById(Long commentId);
}