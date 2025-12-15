package com.globalkart.Service;

import com.globalkart.Repository.UserRepository;
import com.globalkart.model.User;
import org.springframework.stereotype.Service;

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

}
