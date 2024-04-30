package com.example.testtwitter.service;


import com.example.testtwitter.Entity.Comment;
import com.example.testtwitter.Entity.Post;


import com.example.testtwitter.Entity.User;
import com.example.testtwitter.Repo.CommentRepository;
import com.example.testtwitter.Repo.PostRepository;
import com.example.testtwitter.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.testtwitter.JsonConverter.convertToJson;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public String createComment(String commentBody, Long postId,Long userID) {
        User user = userRepository.findById(userID).orElse(null);
        int f=(user == null)?0:1;

        switch(f){
            case 0:return convertToJson("User not found");
        }
        Post post = postRepository.findById(postId).orElse(null);
        int flag=(post != null)?1:0;

        switch(flag){
            case 0:return convertToJson("Post does not exist");
            default:
                Comment comment = new Comment(commentBody, post,userID);
                commentRepository.save(comment);
                return "Comment created successfully";
        }
    }

    public Comment getCommentDetails(Long commentId) {


        return commentRepository.findCommentDetailsById(commentId).orElse(null);
    }

    public String editComment(Long commentId, String commentBody) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        int flag=(comment != null)?1:0;
        switch(flag){
            case 1:
                comment.setCommentBody(commentBody);
                commentRepository.save(comment);
                return "Comment edited successfully";
            default:
                return convertToJson("Comment does not exist");
        }
    }
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
    public String deleteComment(Long commentId) {

        int flag=(commentRepository.existsById(commentId))?1:0;
        switch(flag){
            case 0:return convertToJson("Comment does not exist");

            default:
                commentRepository.deleteById(commentId);
                return "Comment deleted";

        }
    }
}
