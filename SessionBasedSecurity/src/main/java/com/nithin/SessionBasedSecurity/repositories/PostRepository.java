package com.nithin.SessionBasedSecurity.repositories;

import com.nithin.SessionBasedSecurity.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
}
