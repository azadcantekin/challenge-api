package com.techcareer.challenge.config.security;

import com.techcareer.challenge.data.model.UserModel;
import com.techcareer.challenge.exception.ResourceNotFoundException;
import com.techcareer.challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(()-> new ResourceNotFoundException("User not found",null));


        return new CustomUserDetail(userModel);
    }
}
