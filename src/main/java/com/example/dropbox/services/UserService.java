package com.example.dropbox.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.dropbox.dtos.RegisterDto;
import com.example.dropbox.models.User;
import com.example.dropbox.repositories.UserRepository;
import com.example.dropbox.dtos.AuthenticationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public AuthenticationResponseDto register(RegisterDto registerDto) {
        Optional<User> EmailAlreadyExists = userRepository.findByEmail(registerDto.getEmail());
        Optional<User> UsernameAlreadyExists = userRepository.findByUsername(registerDto.getUsername());


        if ( EmailAlreadyExists.isPresent()) {
            AuthenticationResponseDto response = new AuthenticationResponseDto();
            response.setError(true);
            response.setToken(null);
            response.setMessage("Email is already in use.");

            return response;

        } else if ( UsernameAlreadyExists.isPresent()) {
            AuthenticationResponseDto response = new AuthenticationResponseDto();
            response.setError(true);
            response.setToken(null);
            response.setMessage("Username is already in use.");

            return response;

        } else {
            User user = new User();
            user.setUsername(registerDto.getUsername());
            user.setEmail(registerDto.getEmail());
            user.setPassword(encoder.encode(registerDto.getPassword()));
            userRepository.save(user);
            var algorithm = Algorithm.HMAC256("Hej William");
            var token = JWT.create()
                    .withSubject(registerDto.getUsername())
                    .withIssuer("auth0")
                    .sign(algorithm);
            AuthenticationResponseDto response = new AuthenticationResponseDto();
            response.setError(false);
            response.setMessage("User created.");
            response.setToken(token);
            return response;
        }
    }
}
