package com.nithin.SecurityApplication.utils;

import com.nithin.SecurityApplication.dto.PostDTO;
import com.nithin.SecurityApplication.entities.User;
import com.nithin.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO postDTO = postService.getPost(postId);
        return user.getId().equals(postDTO.getAuthor().getId());
    }
}
