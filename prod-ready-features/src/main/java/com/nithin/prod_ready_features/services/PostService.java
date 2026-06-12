package com.nithin.prod_ready_features.services;

import com.nithin.prod_ready_features.dto.PostDTO;

import java.util.List;
import java.util.UUID;

public interface PostService {
    
    List<PostDTO> getAllPosts();
    
    PostDTO createPost(PostDTO postDTO);


    PostDTO getPostById(Long postId);

    PostDTO updatePost(Long postId, PostDTO postDTO);
}
