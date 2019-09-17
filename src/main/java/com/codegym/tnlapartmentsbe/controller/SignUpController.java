package com.codegym.tnlapartmentsbe.controller;

import com.codegym.tnlapartmentsbe.form.request.SignUpForm;
import com.codegym.tnlapartmentsbe.form.response.StandardResponse;
import com.codegym.tnlapartmentsbe.model.Role;
import com.codegym.tnlapartmentsbe.model.RoleName;
import com.codegym.tnlapartmentsbe.model.User;
import com.codegym.tnlapartmentsbe.repository.RoleRepository;
import com.codegym.tnlapartmentsbe.repository.UserRepository;
import com.codegym.tnlapartmentsbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class SignUpController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    //sign-up with ROLE HOST
    @RequestMapping(value = "/host/sign-up", method = RequestMethod.POST)
    public ResponseEntity<StandardResponse> registerHost(@RequestBody SignUpForm signUpRequest) throws Exception {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false,"Fail -> Username is already token!",null),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false,"Fail -> Email is already in use!",null),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()),signUpRequest.getAvatar());

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(RoleName.ROLE_HOST);
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true,"User registered with HOST successfully!",null),
                HttpStatus.OK);
    }

    //sign-up with ROLE GUEST
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<StandardResponse> registerUser(@RequestBody SignUpForm signUpRequest) throws Exception {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false,"Fail -> Username already exists!",null),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(false,"Fail -> Email already uses!",null),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()),signUpRequest.getAvatar());

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(RoleName.ROLE_GUEST);
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(true,"User registered with ROLE_GUEST successfully!",null),
                HttpStatus.OK);
    }
}
