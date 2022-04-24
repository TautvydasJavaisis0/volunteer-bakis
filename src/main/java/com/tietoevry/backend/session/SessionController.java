package com.tietoevry.backend.session;

import com.tietoevry.backend.session.model.LoginData;
import com.tietoevry.backend.session.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    @GetMapping
    public Session getSession() {
        return sessionService.getSession();
    }

    @PostMapping
    public Session createSession(HttpServletRequest httpServletRequest, @RequestBody LoginData sessionRequest) {
        return sessionService.createSession(
            httpServletRequest,
            sessionRequest.getUsername(),
            sessionRequest.getPassword()
        );
    }

    @DeleteMapping
    public void deleteSession(HttpServletRequest httpServletRequest) {
        sessionService.deleteSession(httpServletRequest);
    }
}
