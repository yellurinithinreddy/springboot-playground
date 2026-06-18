package com.nithin.SessionBasedSecurity.repositories;

import com.nithin.SessionBasedSecurity.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    boolean existsByUser_Id(Long id);

    @Modifying
    @Transactional
    @Query("delete from Session s where s.user.id = :id")
    long deleteByUser_Id(Long id);

    Optional<Session> findByUser_Id(Long userId);
}
