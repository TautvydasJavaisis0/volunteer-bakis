package com.tietoevry.backend.session;

import com.tietoevry.backend.authorization.SecurityContextService;
import com.tietoevry.backend.session.model.Session;
import com.tietoevry.backend.session.model.SessionUser;
import com.tietoevry.backend.user.UserRepository;
import com.tietoevry.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SecurityContextService securityContextService;
    private final UserRepository userRepository;

    public Session getSession() {
        return Session.builder()
            .user(getSessionUser())
            .build();
    }

    public Session createSession(HttpServletRequest httpServletRequest, String username, String password) {
        securityContextService.createSession(httpServletRequest, username, password);
        return Session.builder()
            .user(getSessionUser())
            .build();
    }

    public void deleteSession(HttpServletRequest httpServletRequest) {
        securityContextService.deleteSession(httpServletRequest);
    }

    public SessionUser getSessionUser() {
        if(!Optional.ofNullable(securityContextService.getCurrentUser()).isPresent()){
            return null;
        }

        Optional<User> user = userRepository.findByEmail(securityContextService.getCurrentUser().getUsername());
        return Optional.ofNullable(securityContextService.getCurrentUser())
            .map(userDetails -> SessionUser.builder().id(user.get().getId()).fullName(user.get().getFullName()).email(user.get().getEmail()).build())
            .orElse(null);
    }

}
