package com.teste.iniflex.controllers;

import com.teste.iniflex.records.AccountCredentialsVO;
import com.teste.iniflex.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Endpoint de Autenticação")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Autentica um usuário e retorna um token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
        System.out.println(data);
        if (checkIfParamIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solicitação inválida");
        var token = authService.signin(data);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solicitação inválida");
        return token;
    }

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Atualizar token para usuário autenticado e retornar um token")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username,
                                       @RequestHeader("Authorization") String refreshToken) {
        if (checkIfParamIsNotNull(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solicitação inválida!");
        var token = authService.refreshToken(username, refreshToken);
        if (token == null) return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solicitação inválida!");
        return token;
    }

    private boolean checkIfParamIsNotNull(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() ||
                username == null || username.isBlank();
    }

    private boolean checkIfParamIsNotNull(AccountCredentialsVO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }
}
