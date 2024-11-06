package com.mayorista.nadir.controladores;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayorista.nadir.autenticacion.PaymentRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PaypalControlador {


  
    @PostMapping("/getToken")
    public String getToken() throws JsonMappingException, JsonProcessingException {
        String clientId = "AcQysfW08pF6iFCj65-H1b61UIPEuQTVoZ5_3fDaOPRdFq65LmOBnDn1fz3YlD0JFNii-JKYHy-MZqZE";
        String clientSecret = "EOFNCj0SemE10DE6MzVICHWi4aSy9ZrLsmTpOTLVUuXMy-p3xvpwNkm6L1-yv8UHNSQv05og7K0x8JBv";
        String paypalApiUrl = "https://api-m.sandbox.paypal.com/v1/oauth2/token";

        // Construir la cadena de autorización en formato Base64
        String authString = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        String requestBody = "grant_type=client_credentials";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + authString);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Realizar la solicitud HTTP para obtener el token de acceso
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(paypalApiUrl, request, String.class);

        // Verificar si la solicitud fue exitosa
        if (response.getStatusCode() == HttpStatus.OK) {
            // Extraer el token de acceso de la respuesta
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            String accessToken = root.get("access_token").asText();
            return accessToken; 
        } else {
            // Manejar errores de solicitud
            return "Error al obtener el token de acceso";
        }
    }


    @PostMapping("/realizarPagoPaypal")
public String realizarPagoPaypal(@RequestBody PaymentRequest paymentRequest, @RequestParam String accessToken) {
    // Construir la URL de la API de PayPal
    String paypalApiUrl = "https://api-m.sandbox.paypal.com/v2/checkout/orders";

    // Construir los headers con el token de acceso
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(accessToken);

    // Construir el cuerpo de la solicitud con los datos de pago
    Map<String, Object> requestBody = new HashMap<>();
    
    requestBody.put("name", paymentRequest.getCardHolderName());
    Map<String, Object> fundingInstrument = new HashMap<>();
    Map<String, Object> creditCard = new HashMap<>();
    creditCard.put("number", paymentRequest.getCardNumber());
    creditCard.put("expiration_date", paymentRequest.getCardExpiry());
    creditCard.put("cvv2", paymentRequest.getCardCvc());
    fundingInstrument.put("credit_card", creditCard);
    List<Map<String, Object>> fundingInstruments = new ArrayList<>();
    fundingInstruments.add(fundingInstrument);
    requestBody.put("funding_instruments", fundingInstruments);    
    // Agregar otros detalles del pago si es necesario
    
    // Realizar la solicitud HTTP a PayPal
    HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> responseEntity = restTemplate.exchange(paypalApiUrl, HttpMethod.POST, requestEntity,
            String.class);

    // Verificar la respuesta de PayPal y devolverla
    return responseEntity.getBody();
}
}
