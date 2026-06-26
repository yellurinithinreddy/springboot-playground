package com.nithin.SecurityApplication.controllers;

import com.nithin.SecurityApplication.dto.PostDTO;
import com.nithin.SecurityApplication.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CREATOR','ADMIN')")
    public ResponseEntity<PostDTO> create(@RequestBody @Valid PostDTO postDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(postDTO));
    }

    @GetMapping("{postId}")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId){
        return  ResponseEntity.status(HttpStatus.OK).body(postService.getPost(postId));
    }

    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }
}
