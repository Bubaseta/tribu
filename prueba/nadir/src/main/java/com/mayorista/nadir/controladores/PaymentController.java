package com.mayorista.nadir.controladores;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mayorista.nadir.autenticacion.PaymentRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/form")
@RequiredArgsConstructor
public class PaymentController<HttpServletRequest, HttpServletResponse> {

    private PaypalControlador paypalControlador;

    @GetMapping("/payment")
    public ModelAndView realizarPago(HttpServletResponse response)
     {

        ModelAndView modelAndView = new ModelAndView("formularioPago");
        modelAndView.addObject("paymentRequest", new PaymentRequest());
        return modelAndView;
    }


    @PostMapping("/proceso")
    public String realizarPago(HttpServletRequest request,
            String cardHolderName,
            String cardNumber,
            String cardExpiry,
            String cardCvc,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader("Authorization") String token) throws JsonMappingException, JsonProcessingException {

        if (userDetails == null) {
            // Usuario no autenticado, devolver un error 401
            return "Usuario no autenticado";
        }

        // Verificar si se proporcionó el token JWT en el encabezado
        if (token == null || !token.startsWith("Bearer ")) {
            // El token no es válido, devolver un error 401
            return "Token no válido";
        }

        String accessToken = token.substring(7); // Eliminar "Bearer " del token

        // Construir un objeto PaymentRequest con los datos del formulario
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCardHolderName(cardHolderName);
        paymentRequest.setCardNumber(cardNumber);
        paymentRequest.setCardExpiry(cardExpiry);
        paymentRequest.setCardCvc(cardCvc);

        String response = paypalControlador.realizarPagoPaypal(paymentRequest, accessToken);

        // Manejar la respuesta de PayPal
        return response;
    }

}