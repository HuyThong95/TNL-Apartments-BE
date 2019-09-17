package com.codegym.tnlapartmentsbe.controller;

import com.codegym.tnlapartmentsbe.form.request.LogInForm;
import com.codegym.tnlapartmentsbe.form.response.JwtResponse;
import com.codegym.tnlapartmentsbe.form.response.StandardResponse;
import com.codegym.tnlapartmentsbe.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<StandardResponse> createAuthenticationToken(@RequestBody LogInForm loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenUtil.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return new  ResponseEntity<StandardResponse>(
                    new StandardResponse(true,"succsess"+loginRequest.getPassword(),new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities())),
                    HttpStatus.OK);
        } catch (DisabledException e) {
            e.printStackTrace();
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
