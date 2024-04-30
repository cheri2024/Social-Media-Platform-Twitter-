/*package com.example.testtwitter.controller;

import com.example.testtwitter.Entity.Comment;
import com.example.testtwitter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<String> createComment(@RequestParam String commentBody, @RequestParam Long postId) {
        String result = commentService.createComment(commentBody, postId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<?> getCommentDetails(@RequestBody Long commentId) {
        Comment comment = commentService.getCommentDetails(commentId);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
    }

    @PatchMapping("/")
    public ResponseEntity<String> editComment(@RequestParam Long commentId, @RequestParam String commentBody) {
        String result = commentService.editComment(commentId, commentBody);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteComment(@RequestParam Long commentId) {
        String result = commentService.deleteComment(commentId);
        return ResponseEntity.ok(result);
    }
}
*/
package com.example.testtwitter.controller;

import com.example.testtwitter.DAO.CreateCommentRequest;
import com.example.testtwitter.DAO.EditCommentRequest;
import com.example.testtwitter.Entity.Comment;
import com.example.testtwitter.Entity.User;
import com.example.testtwitter.Repo.UserRepository;
import com.example.testtwitter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.testtwitter.JsonConverter.convertToJsonResponse;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/comment")
    public ResponseEntity<String> createComment(@RequestBody CreateCommentRequest request) {
        String result = commentService.createComment(request.getCommentBody(), request.getPostID(), request.getUserID());
        return ResponseEntity.ok(result);
    }


    @GetMapping("/comment")
    public ResponseEntity<?> getCommentDetails(@RequestParam Long commentID) {
        Comment comment = commentService.getCommentDetails(commentID);
        if (comment != null) {
            Map<String, Object> commentMap = constructCommentMap(comment);
            return ResponseEntity.ok(commentMap);
        } else {

            return convertToJsonResponse(HttpStatus.NOT_FOUND, "Comment does not exist");
        }
    }

    private Map<String, Object> constructCommentMap(Comment comment) {
        Map<String, Object> commentMap = new LinkedHashMap<>();
        commentMap.put("commentID", comment.getId());
        commentMap.put("commentBody", comment.getCommentBody());

        // Construct comment creator object
        Map<String, Object> commentCreator = new LinkedHashMap<>();
        commentCreator.put("userID", comment.getUserID());
        User user = userRepository.findById(comment.getUserID()).orElse(null);
        commentCreator.put("name", user != null ? user.getName() : "Unknown User");
        commentMap.put("commentCreator", commentCreator);

        return commentMap;
    }


    @PatchMapping("/comment")
    public ResponseEntity<String> editComment(@RequestBody EditCommentRequest request) {
        String result = commentService.editComment(request.getCommentID(), request.getCommentBody());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<String> deleteComment(@RequestParam Long commentID) {
        String result = commentService.deleteComment(commentID);
        return ResponseEntity.ok(result);
    }

}
