package com.example.testtwitter.controller;

import com.example.testtwitter.Entity.User;
import com.example.testtwitter.Entity.Post;

import com.example.testtwitter.service.PostService;
import com.example.testtwitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.*;


import static com.example.testtwitter.JsonConverter.convertToJsonResponse;
import static com.example.testtwitter.controller.PostResponse.getBody;


@RestController
//@RequestMapping("/")
public class UserController {


    @Autowired
    private PostService postService;
    @Autowired
    private PostController postController;
    @Autowired
    private UserService userService;
    /*@Autowired
    private CommentService commentService;*/



    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam Long userID) {
        com.example.testtwitter.Entity.User user = userService.getUserDetails(userID);
        if (user != null) {
            Map<String, Object> userDetails = buildUserDetailsMap(user);
            return ResponseEntity.ok(userDetails);
        } else {

            return convertToJsonResponse(HttpStatus.NOT_FOUND, "User does not exist");
        }
    }

    private Map<String, Object> buildUserDetailsMap(com.example.testtwitter.Entity.User user) {
        Map<String, Object> userDetails = new LinkedHashMap<>();
        userDetails.put("name", user.getName());
        userDetails.put("userID", user.getId());
        userDetails.put("email", user.getEmail());
        return userDetails;
    }




  @GetMapping("/")
  public ResponseEntity<?> getUserFeed() {
      List<Post> allPosts = postService.getAllPosts();
      List<Map<String, Object>> responsePosts = new ArrayList<>();
      processPostsInReverseOrder(allPosts, responsePosts);
      return ResponseEntity.ok(responsePosts);
  }

    private void processPostsInReverseOrder(List<Post> allPosts, List<Map<String, Object>> responsePosts) {
        int j = allPosts.size() - 1;
        while (j >= 0) {
            addPostToResponse(allPosts, j, responsePosts);
            j--;
        }
    }

    private void addPostToResponse(List<Post> allPosts, int index, List<Map<String, Object>> responsePosts) {
        Post post = allPosts.get(index);
        long postId = post.getId();
        ResponseEntity<?> postResponseEntity = postController.getPost(postId);
        Map<String, Object> postMap = getBody(postResponseEntity);
        responsePosts.add(postMap);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String password = request.get("password");
        String email = request.get("email");
        String result = userService.login(email, password);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value ="/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");
        String password = request.get("password");
        String result = userService.signup(email, name, password);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        List<Map<String, Object>> userDetailsList = new ArrayList<>();
        processUserDetails(allUsers, userDetailsList);
        return ResponseEntity.ok(userDetailsList);
    }

    private void processUserDetails(List<User> allUsers, List<Map<String, Object>> userDetailsList) {
        Iterator<User> iterator = allUsers.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("name", user.getName());
            userDetails.put("userID", user.getId());
            userDetails.put("email", user.getEmail());
            userDetailsList.add(userDetails);
        }
    }

}


