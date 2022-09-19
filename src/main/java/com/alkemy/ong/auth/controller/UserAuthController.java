package com.alkemy.ong.auth.controller;


import com.alkemy.ong.domain.dto.*;
import com.alkemy.ong.auth.service.UserDetailsCustomService;
import com.alkemy.ong.domain.response.ErrorResponse;
import com.alkemy.ong.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api(tags = "Authentication Endpoints", value = "AuthenticationEndpoints")
public class UserAuthController {
    @Autowired
    UserService userService;
    @Autowired
    UserDetailsCustomService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/register")
    @ApiResponse(code = 403, message = "PERMISSION_DENIED - Forbidden.",
            response = ErrorResponse.class)
    public ResponseEntity<BasicUserDTO> signup(@Valid @RequestBody UserDTO user)throws Exception {
        BasicUserDTO result =  this.userDetailsService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest authRequest) throws Exception
    {
        UserDetails userDetails;
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            userDetails = (UserDetails) auth.getPrincipal();
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }
        String username = userDetails.getUsername();

        return ResponseEntity.ok(new AuthenticationResponse(username));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getProfile(HttpServletRequest request) {

        UserProfileDTO dto = userService.getUserProfile(request);
        return ResponseEntity.ok().body(dto);
    }


}
