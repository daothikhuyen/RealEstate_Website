package com.example.backend.controller;

import com.example.backend.dto.request.Email.EmailRequest;
import com.example.backend.dto.request.User.CheckOTPRequest;
import com.example.backend.dto.request.User.PasswordRequest;
import com.example.backend.dto.request.User.UserCreationRequest;
import com.example.backend.dto.request.User.UserUpdateRequest;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.enity.User;
import com.example.backend.reponsitory.UserRepository;
import com.example.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin
@Slf4j // log.
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRespository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @PostMapping("/processregister")
    public ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request) throws Exception {
        ApiResponse<UserResponse> response = null;

        String createOTP = userService.createUser(request);

        response = ApiResponse.<UserResponse>builder()
                .code(1000)
                .message(createOTP)
                .build();
        return  response;
    }

    @PostMapping("/check/verifyOTP")
    public ApiResponse<UserResponse> verifyOTP(@RequestBody CheckOTPRequest request) throws Exception{

        System.out.println(request.getEmail() + ":"+ request.getToken());
        String email = request.getEmail();
        String OTP = request.getToken();

        String checkOTP = userService.is_activated(email,OTP);

        if(checkOTP != null){
            return ApiResponse.<UserResponse>builder()
                    .code(1000)
                    .build();
        }else{
            return ApiResponse.<UserResponse>builder()
                    .code(0)
                    .build();
        }

    }

    @PostMapping("/forgot_password")
    public ApiResponse<UserResponse> EmailforgetPassword(@RequestBody String request) throws Exception{

        String email = request.replaceAll("^\"|\"$", "");

        return ApiResponse.<UserResponse>builder()
                .message(String.valueOf(userService.EmailForgetPassword(email)))
                .build();

    }

    @PostMapping("/reset_password/{token}")
    public ApiResponse<UserResponse> ResetPassword(@RequestBody PasswordRequest request,@PathVariable("token") String token) throws Exception{
        System.out.println(request);
        return ApiResponse.<UserResponse>builder()
                .message(String.valueOf(userService.ResetPassword(request,token)))
                .build();

    }

    @GetMapping("/list_user")
    public ApiResponse<List<UserResponse>> getUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("email: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @PostMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable("userId") Long userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/getMyInfo")
    public ApiResponse<UserResponse> getMyInfo(){

        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PostMapping("/update/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request ){

        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId,request))
                .build();
    }

    @PostMapping("/update_password")
    public ApiResponse<String> change_password(@RequestBody PasswordRequest request){

        return ApiResponse.<String>builder()
                .result(userService.update_password(request))
                .build();
    }

    @DeleteMapping("/destroy")
    public ApiResponse<String> deleted_account(){

        return ApiResponse.<String>builder()
                .result(userService.deleted_Account())
                .build();
    }

//    ADMIN

    @PostMapping("/admin/list_user")
    public ApiResponse<Map<String, Object>> list_user(@RequestParam(name = "page", defaultValue = "0") int page){

        return ApiResponse.<Map<String, Object>>builder()
                .result(userService.list_user(page))
                .build();
    }

    @PostMapping("/admin/account_lock/{userId}")
    public ApiResponse<String> account_lock(@PathVariable("userId") Long userId){

        return ApiResponse.<String>builder()
                .result(userService.account_lock(userId))
                .build();
    }

    @PostMapping("/admin/destroyAccount/{userId}")
    public ApiResponse<String> destroyAccount(@PathVariable("userId") Long userId){

        return ApiResponse.<String>builder()
                .result(userService.destroyAccount(userId))
                .build();
    }







}
