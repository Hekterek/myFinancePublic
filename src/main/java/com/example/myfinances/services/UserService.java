package com.example.myfinances.services;

import com.example.myfinances.models.User;
import com.example.myfinances.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    final UserRepository userRepository;
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Integer getLoggedUserId(Principal principal) {
        Optional<User> optionalUser = findByEmail(principal.getName());
        return optionalUser.map(User::getUserId).orElse(null);
    }
}
