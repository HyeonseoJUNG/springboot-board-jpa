package com.programmers.springbootboardjpa.controller;

import com.programmers.springbootboardjpa.dto.Response;
import com.programmers.springbootboardjpa.dto.post.request.PostCreationRequest;
import com.programmers.springbootboardjpa.dto.post.request.PostUpdateRequest;
import com.programmers.springbootboardjpa.dto.post.response.PostResponse;
import com.programmers.springbootboardjpa.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public Response<Map<String, Long>> createPost(@Valid @RequestBody PostCreationRequest request) {
        Long savedPostId = postService.savePost(request);
        return Response.ok(Collections.singletonMap("id", savedPostId), "Post created successfully.");
    }

    @GetMapping("/posts/{id}")
    public Response<PostResponse> getPost(@PathVariable Long id) {
        PostResponse postById = postService.findPostById(id);
        return Response.ok(postById, "ok");
    }

    @GetMapping("/posts")
    public Response<Page<PostResponse>> getAllPosts(@RequestParam("page") Long page,
                                                    @RequestParam("size") Long size) {
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        Page<PostResponse> posts = postService.findAllPosts(pageable);
        return Response.ok(posts, "ok");
    }

    @PatchMapping("/posts/{id}")
    public Response<Map<String, Long>> updatePost(@PathVariable Long id,
                                                  @Valid @RequestBody PostUpdateRequest request) {
        Long updatedPostId = postService.updatePost(id, request);
        return Response.ok(Collections.singletonMap("id", updatedPostId), "Post updated successfully.");
    }

}