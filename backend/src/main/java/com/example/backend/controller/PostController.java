package com.example.backend.controller;

import com.example.backend.dto.request.PostRequest;
import com.example.backend.dto.request.Search.LocationRequest;
import com.example.backend.dto.request.Search.PriceAreaRequest;
import com.example.backend.dto.request.Search.SearchRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.PostDetailResponse;
import com.example.backend.service.Admin.Admin_PostService;
import com.example.backend.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    Admin_PostService adminPostService;

    @GetMapping("/getall")
    public ApiResponse<List<PostDetailResponse>> getAll(){

        return ApiResponse.<List<PostDetailResponse>>builder()
                .result(postService.getAll())
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<Boolean> create_post(@RequestBody PostRequest request){

        return ApiResponse.<Boolean>builder()
                .message("Tạo bài đăng thành công")
                .result(postService.create_post(request))
                .build();
    }

    @GetMapping("/getMyPosts")
    public ApiResponse<Map<String, Object>> getMyPosts(@RequestParam(name = "page", defaultValue = "0") int page){
        return ApiResponse.<Map<String, Object>>builder()
                .message("Lấy bài đăng thành công")
                .result(postService.getMyPosts(page))
                .build();
    }

    @DeleteMapping("/destroy/{postId}")
    public ApiResponse<String> deletedPost(@PathVariable Long postId){

        return  ApiResponse.<String>builder()
                .message(postService.deletedPost(postId))
                .build();

    }

    @GetMapping("edit/{postId}")
    public ApiResponse<PostDetailResponse> showPost(@PathVariable("postId") Long postId){
        return ApiResponse.<PostDetailResponse>builder()
                .result(postService.showPosts(postId))
                .build();
    }

    @PutMapping("edit/{postId}")
    public ApiResponse<String> editPost(@PathVariable Long postId,@RequestBody PostRequest request){
        return ApiResponse.<String>builder()
                .result(postService.editPost(postId,request))
                .build();
    }

    @PostMapping("/search/searchByLocation_Id")
    public ApiResponse<List<PostDetailResponse>> searchByLocation_Id(@RequestBody LocationRequest request) {

        return ApiResponse.<List<PostDetailResponse>>builder()
                .result(postService.searchByLocation_Id(request))
                .build();
    }

    @PostMapping("/search/searchInput_All")
    public ApiResponse<List<PostDetailResponse>> searchInput_All(@RequestBody SearchRequest request) {

        return ApiResponse.<List<PostDetailResponse>>builder()
                .result(postService.searchInput_All(request))
                .build();
    }

    @PostMapping("/search/list_searchSeggestion")
    public ApiResponse<Map<String, Object>> list_SearchSuggestion(@RequestBody SearchRequest request) {

        return ApiResponse.<Map<String, Object>>builder()
                .result(postService.list_SearchSuggestion(request))
                .build();
    }

    @PostMapping("/search/searchPriceOrArea")
    public ApiResponse<List<PostDetailResponse>> searchPriceOrArea(@RequestBody PriceAreaRequest request) {

        return ApiResponse.<List<PostDetailResponse>>builder()
                .result(postService.searchPriceOrArea(request))
                .build();
    }

// Admin

    @GetMapping("admin/list_posts_user")
    public ApiResponse<Map<String, Object>> getAllPosts(@RequestParam(name = "page", defaultValue = "0") int page){
        return ApiResponse.<Map<String, Object>>builder()
                .message("Lấy bài đăng thành công")
                .result(adminPostService.getListPosts(page))
                .build();
    }

    @GetMapping("admin/detail/{postId}")
    public ApiResponse<PostDetailResponse> showPostForAdmin(@PathVariable("postId") Long postId){
        return ApiResponse.<PostDetailResponse>builder()
                .result(postService.showPosts(postId))
                .build();
    }

    @PostMapping("admin/destroy_posts/{postId}")
    public ApiResponse<String> destroyPosts(@PathVariable("postId") Long postId){
        return ApiResponse.<String>builder()
                .result(postService.deletedPost(postId))
                .build();
    }


}
