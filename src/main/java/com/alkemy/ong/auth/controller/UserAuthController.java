package com.alkemy.ong.auth.controller;


import com.alkemy.ong.auth.service.UserDetailsCustomService;
import com.alkemy.ong.domain.dto.*;
import com.alkemy.ong.domain.request.AuthenticationRequest;
import com.alkemy.ong.domain.response.AuthenticationResponse;
import com.alkemy.ong.exception.InvalidCredentialsException;
import com.alkemy.ong.exception.UserAlreadyExistException;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsCustomService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody UserDTO user) {
        UserDTO result = this.userDetailsService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (
            @RequestBody @Valid AuthenticationRequest authenticationRequest)  {
        return ResponseEntity.ok(userDetailsService.login(authenticationRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getProfile(Principal request) {
        UserProfileDTO dto = userService.getUserProfile(request.getName());
        return ResponseEntity.ok().body(dto);
    }


}
