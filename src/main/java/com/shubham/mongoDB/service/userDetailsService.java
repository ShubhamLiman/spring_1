package com.shubham.mongoDB.service;

import com.shubham.mongoDB.Entities.User;
import com.shubham.mongoDB.repository.UserEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class userDetailsService implements UserDetailsService {

    @Autowired
    private UserEntryRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByName(username);
        if (user != null) {
            return org.springframework.security.core.userdetails
                    .User.builder()
                    .username(user.getName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();


        }
        throw new UsernameNotFoundException("User not found" + username);
    }
}
