package com.nithin.SessionBasedSecurity.services;

import com.nithin.SessionBasedSecurity.entities.Session;
import com.nithin.SessionBasedSecurity.entities.User;
import com.nithin.SessionBasedSecurity.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public void generateSession(User user,String refreshToken){
        List<Session> sessions = sessionRepository.findByUser(user);
        if(sessions.size() == user.getSessionCount()){
           Collections.sort(sessions,(a,b) -> a.getLastUsedAt().compareTo(b.getLastUsedAt()));
           Session sessionToBeDeleted = sessions.stream().findFirst().get();
           sessionRepository.delete(sessionToBeDeleted);
        }

        Session session = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        sessionRepository.save(session);
    }



    @Transactional
    public void validateSession(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("Session with id is not found"+refreshToken));

        session.setLastUsedAt(LocalDateTime.now());
    }

    @Transactional
    public void removeSessions(Long userId) {
        List<Session> sessions = sessionRepository.findByUser_Id(userId);
        sessionRepository.deleteAll(sessions);
    }
}
