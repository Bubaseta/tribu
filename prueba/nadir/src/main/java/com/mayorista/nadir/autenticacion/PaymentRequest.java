package com.mayorista.nadir.autenticacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String cardHolderName;
    private String cardNumber;
    private String cardExpiry;
    private String cardCvc;
    
}
