package com.mayorista.nadir.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mayorista.nadir.autenticacion.LoginRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class Favicon {



    @GetMapping(value = "favicon.ico")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("loguear");
        LoginRequest loginRequest = new LoginRequest();
        modelAndView.addObject("loginRequest", loginRequest);
        return modelAndView;

    }
    
}



