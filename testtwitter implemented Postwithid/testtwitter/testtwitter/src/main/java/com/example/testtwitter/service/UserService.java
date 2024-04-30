package com.example.testtwitter.service;


import com.example.testtwitter.Repo.PostRepository;
import com.example.testtwitter.Repo.UserRepository;
import com.example.testtwitter.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.testtwitter.JsonConverter.convertToJson;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserDetails(Long userID) {
        return userRepository.findById(userID).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        int flag=user!=null?1:0;

        switch (flag)
        {
            case 1:
                if (user.getPassword().equals(password)) {
                    return "Login Successful";
                } else {
                    return convertToJson("Username/Password Incorrect");
                }
            default:return convertToJson("User does not exist");
        }
    }

    public String signup(String email, String name, String password) {
        int f=(userRepository.existsByEmail(email))?1:0;
        switch (f)
        {
            case 1:return convertToJson("Forbidden, Account already exists");
            default:
                User newUser = new User(email, name, password);
                userRepository.save(newUser);
                return "Account Creation Successful";
        }

    }
}
