package com.globalkart.Service;

import com.globalkart.Repository.UserRepository;
import com.globalkart.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

        public User Register(User user){
        user.setRole("User");
        return  userRepository.save(user);
        }
        public Optional<User> getEmails(String email){
        userRepository.findByEmail(email);
        return userRepository.findByEmail(email);
        }

}
