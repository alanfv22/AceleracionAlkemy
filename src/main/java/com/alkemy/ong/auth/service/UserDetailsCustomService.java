package com.alkemy.ong.auth.service;

import com.alkemy.ong.auth.jwt.JwtUtils;
import com.alkemy.ong.auth.security.RoleType;
import com.alkemy.ong.domain.dto.AuthenticationRequest;
import com.alkemy.ong.domain.dto.BasicUserDTO;
import com.alkemy.ong.domain.dto.UserDTO;
import com.alkemy.ong.domain.mapper.UserMapper;
import com.alkemy.ong.domain.entity.UserEntity;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.EmailServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsCustomService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
    private EmailServiceInterface emailService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(userName);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Username or password not found");
        }
        return new User(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
    }

    public BasicUserDTO save(UserDTO userDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setRoleEntities(List.of(roleRepository.findByName(RoleType.USER.getFullRoleName())));
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        BasicUserDTO userResponse = userMapper.entity2BasicDTO(userRepository.save(userEntity));
        userResponse.setToken(jwtUtil.generateToken(userEntity));
        userEntity = this.userRepository.save(userEntity);
        if (userEntity != null) {
            emailService.sendEmailTo(userEntity.getEmail());
        }

        BasicUserDTO result = userMapper.entity2BasicDTO(userEntity);
        return result;

    }
    public String getUsername(AuthenticationRequest authRequest)throws Exception{
        UserDetails userDetails;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            userDetails = (UserDetails) auth.getPrincipal();
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        String username = userDetails.getUsername();
        return username;
    }
}
