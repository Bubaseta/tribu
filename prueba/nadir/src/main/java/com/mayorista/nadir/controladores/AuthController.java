package com.mayorista.nadir.controladores;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.mayorista.nadir.autenticacion.AuthResponse;
import com.mayorista.nadir.autenticacion.LoginRequest;
import com.mayorista.nadir.autenticacion.RegisterRequest;
import com.mayorista.nadir.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    public Logger log;

    @GetMapping(value = "login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("loguear");
        LoginRequest loginRequest = new LoginRequest();
        modelAndView.addObject("loginRequest", loginRequest);
        return modelAndView;

    }

    @PostMapping(value = "logueo")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping(value = "registrar")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("registrar");
        RegisterRequest registerRequest = new RegisterRequest();
        modelAndView.addObject("registerRequest", registerRequest);
        return modelAndView;
    }

    @PostMapping(value = "registro")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity.ok().body(authResponse);
    }

    @GetMapping("/index")
    public ModelAndView getUserInfo() {
        ModelAndView modelAndView = new ModelAndView("inicio");
        return modelAndView;
    }
}
