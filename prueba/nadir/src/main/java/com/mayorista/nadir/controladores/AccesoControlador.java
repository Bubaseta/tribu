package com.mayorista.nadir.controladores;

import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccesoControlador {

    @GetMapping("/send-pago")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView enviarDatosDeUsuario() {

        ModelAndView modelAndView = new ModelAndView("permiso");
        return modelAndView;
    }
}
