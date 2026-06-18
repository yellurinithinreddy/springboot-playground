package com.nithin.SessionBasedSecurity.services;

import com.nithin.SessionBasedSecurity.dto.PostDTO;

import java.util.List;

public interface PostService {
    
    PostDTO createPost(PostDTO postDTO);

    PostDTO getPost(Long postId);

    List<PostDTO> getPosts();
}
