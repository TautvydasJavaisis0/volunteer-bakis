package com.tietoevry.backend.authorization;

import com.tietoevry.backend.authorization.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SecurityContextService {

    private final AuthenticationManager authenticationManager;

    public CustomUserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return principal instanceof CustomUserDetails ? (CustomUserDetails) principal : null;
    }

    public CustomUserDetails createSession(HttpServletRequest httpServletRequest, String username, String password) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
            username,
            password
        );
        var authentication = authenticationManager.authenticate(authenticationToken);
        var securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        return (CustomUserDetails) authentication.getPrincipal();
    }

    public void deleteSession(HttpServletRequest httpServletRequest) {
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();

        HttpSession session = httpServletRequest.getSession();

        if (session != null) {
            session.invalidate();
        }
    }

}
