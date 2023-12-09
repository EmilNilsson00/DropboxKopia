package com.example.dropbox.services;

import com.example.dropbox.models.User;
import com.example.dropbox.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder encoder;

 @Autowired
     public UserService(
             UserRepository userRepository,
             PasswordEncoder encoder) {
         this.userRepository = userRepository;
         this.encoder = encoder;

         var existing = this.userRepository.findByUsername("admin");
         if(existing.isEmpty()) {
             this.userRepository.save(new User("admin", "admin@admin.se", encoder.encode("admin")));
         }
     }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        var user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user '" + username + "'."));
        return user;
    }

    public AuthenticationResponse {

    }
}
