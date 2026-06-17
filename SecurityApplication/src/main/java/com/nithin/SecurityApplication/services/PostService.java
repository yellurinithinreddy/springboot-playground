package com.nithin.SecurityApplication.services;


import com.nithin.SecurityApplication.dto.PostDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface PostService {
    PostDTO create(PostDTO postDTO);

    PostDTO getPost(Long postId);

    List<PostDTO> getAllPosts();
}
