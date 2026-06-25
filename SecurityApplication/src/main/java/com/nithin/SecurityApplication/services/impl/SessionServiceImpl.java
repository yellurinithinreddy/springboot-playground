package com.nithin.SecurityApplication.services.impl;

import com.nithin.SecurityApplication.entities.Session;
import com.nithin.SecurityApplication.entities.User;
import com.nithin.SecurityApplication.exceptions.ResourceNotFoundException;
import com.nithin.SecurityApplication.repositories.SessionRepository;
import com.nithin.SecurityApplication.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 2;

    @Override
    public void generateSession(User user, String refreshToken) {
        List<Session> sessions = sessionRepository.findByUser(user);
        if(sessions.size() == SESSION_LIMIT){
            Collections.sort(sessions,(a,b) -> a.getLastUsedAt().compareTo(b.getLastUsedAt()));
            Session lastUsedSession = sessions.stream().findFirst().get();
            sessionRepository.delete(lastUsedSession);
        }

        Session session = Session.builder()
                .refreshToken(refreshToken)
                .user(user)
                .build();

        sessionRepository.save(session);
    }

    @Override
    @Transactional
    public void validateSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("There is no current session for this token"+refreshToken));
        System.out.println(session);
        session.setLastUsedAt(LocalDateTime.now());
        System.out.println(session);
        sessionRepository.save(session);
    }
}
