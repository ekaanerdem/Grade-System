package com.kaan.gradesystem.controller;

import com.kaan.gradesystem.dto.LoginRequest;
import com.kaan.gradesystem.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                                              JwtService jwtService){
            this.authenticationManager = authenticationManager;
            this.jwtService = jwtService;
    }

@PostMapping("/login")
public String login(@RequestBody LoginRequest request){
    authenticationManager.authenticate( 
        new UsernamePasswordAuthenticationToken(  //Buradaki “token”, JWT değil. 
             request.getUsername(),         //Spring Security'nin username/password bilgisini 
             request.getPassword()        //taşıyan kendi authentication nesnesi.
    ));

    return jwtService.generateToken(request.getUsername()); //Bilgiler doğruysa jwt oluşturuyor.
}



}