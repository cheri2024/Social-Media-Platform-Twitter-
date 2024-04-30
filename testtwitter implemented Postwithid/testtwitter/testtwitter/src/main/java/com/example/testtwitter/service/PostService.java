package com.example.testtwitter.service;


import com.example.testtwitter.Entity.Post;
import com.example.testtwitter.Repo.PostRepository;
import com.example.testtwitter.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.testtwitter.JsonConverter.convertToJson;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private com.example.testtwitter.Repo.UserRepository userRepository;
    public String createPost(String postBody, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        int f=optionalUser.isPresent()?1:0;

        switch (f)
        {
            case 0:return convertToJson("User not found");
            default:
                User user = optionalUser.get();
                // Create the post
                Post post = new Post(postBody, LocalDate.now(), user);
                postRepository.save(post);
                return "Post created successfully";

        }
    }
    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    public String editPost(Long postId, String postBody) {
        Post post = postRepository.findById(postId).orElse(null);
        int f=(post != null)?1:0;

        switch(f){
            case 0:return convertToJson("Post does not exist") ;
            default:
                post.setPostBody(postBody);
                postRepository.save(post);
                return "Post edited successfully";
        }
    }

    public String deletePost(Long postId) {
        int c=(postRepository.existsById(postId))?1:0;

        switch (c){
            case 0:return convertToJson("Post does not exist");
            default:
                postRepository.deleteById(postId);
                return "Post deleted";
        }
    }
}
