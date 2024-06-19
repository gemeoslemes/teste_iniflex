package com.teste.iniflex.services;

import com.teste.iniflex.records.AccountCredentialsVO;
import com.teste.iniflex.records.TokenVO;
import com.teste.iniflex.repositories.UserRepository;
import com.teste.iniflex.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository repository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsVO data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByUsername(username);

            var tokenResponse = new TokenVO();
            if (user != null) {
                tokenResponse = jwtTokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Nome de usuário:  " +username+ " não encontrado!");
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Inválido username/password fornecido!");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {
        var user = repository.findByUsername(username);

        var tokenResponse = new TokenVO();
        if (user != null) {
            tokenResponse = jwtTokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " +username+ ".");
        }
        return ResponseEntity.ok(tokenResponse);
    }
}
