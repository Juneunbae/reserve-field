package com.example.reservefield.domain.authenticate;

import com.example.reservefield.domain.user.Role;
import com.example.reservefield.domain.user.User;
import com.example.reservefield.domain.user.UserRepository;
import com.example.reservefield.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    public User getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();
        log.info("getLoginUser: {}", email);
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public User getAdmin() {
        User user = this.getLoginUser();

        if (user.getRole() != Role.ADMIN) {
            throw new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");
        }

        return user;
    }
}