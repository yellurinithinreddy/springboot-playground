package com.nithin.SessionBasedSecurity.services.impl;

import com.nithin.SessionBasedSecurity.dto.PostDTO;
import com.nithin.SessionBasedSecurity.entities.PostEntity;
import com.nithin.SessionBasedSecurity.exceptions.ResourceNotFoundException;
import com.nithin.SessionBasedSecurity.repositories.PostRepository;
import com.nithin.SessionBasedSecurity.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        PostEntity postEntity = modelMapper.map(postDTO, PostEntity.class);

        return modelMapper.map(postRepository.save(postEntity),PostDTO.class);
    }

    @Override
    public PostDTO getPost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: "+postId));
        return modelMapper.map(postEntity,PostDTO.class);
    }

    @Override
    public List<PostDTO> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity,PostDTO.class))
                .toList();
    }


}
