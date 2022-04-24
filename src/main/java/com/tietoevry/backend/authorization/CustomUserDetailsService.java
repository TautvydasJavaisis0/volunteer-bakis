package com.tietoevry.backend.authorization;

import com.tietoevry.backend.authorization.model.CustomUserDetails;
import com.tietoevry.backend.user.UserRepository;
import com.tietoevry.backend.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).map(user -> new CustomUserDetails(
            user.getId(),
            user.getEmail(),
            user.getOrganisation(),
            user.getFullName(),
            user.getPassword(),
            user.getRole().stream().map(Role::getName).collect(Collectors.toList())
        ))
            .orElseThrow(() -> new UsernameNotFoundException("User was not found by email = " + email));
    }

}
