package com.nithin.SecurityApplication.services.impl;

import com.nithin.SecurityApplication.dto.PostDTO;
import com.nithin.SecurityApplication.entities.PostEntity;
import com.nithin.SecurityApplication.entities.User;
import com.nithin.SecurityApplication.exceptions.ResourceNotFoundException;
import com.nithin.SecurityApplication.repositories.PostRepository;
import com.nithin.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PostDTO create(PostDTO postDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostEntity toBeSaved = modelMapper.map(postDTO,PostEntity.class);
        toBeSaved.setAuthor(user);
        return modelMapper.map(postRepository.save(toBeSaved),PostDTO.class);
    }

    @Override
    public PostDTO getPost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with Id:"+postId));
        return modelMapper.map(postEntity,PostDTO.class);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity,PostDTO.class))
                .toList();
    }
}
