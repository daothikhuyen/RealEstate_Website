package com.example.backend.controller;


import com.example.backend.dto.request.AuthenticationRequest;
import com.example.backend.dto.request.IntrospectRequest;
import com.example.backend.dto.request.LogoutRequest;
import com.example.backend.dto.request.RefreshRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.AuthenticationResponse;
import com.example.backend.dto.response.IntrospectResponse;
import com.example.backend.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@Slf4j // .log
public class AuthenticationController {

    @Autowired
    private  AuthenticationService authenticationService;
    @PostMapping("/log-in") // login
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        log.info(request.getEmail());
        log.info(request.getPassword());

        var result = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect") // validate token còn hiệu lực không
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {

        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        log.info("Vào logout");

        var result = authenticationService.refreshToken(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/log-out")
     public ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        log.info("Vào logout");

        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }
}
