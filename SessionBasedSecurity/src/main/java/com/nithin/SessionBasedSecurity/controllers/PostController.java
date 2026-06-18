package com.nithin.SessionBasedSecurity.controllers;

import com.nithin.SessionBasedSecurity.dto.PostDTO;
import com.nithin.SessionBasedSecurity.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postDTO));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPosts());
    }
}
