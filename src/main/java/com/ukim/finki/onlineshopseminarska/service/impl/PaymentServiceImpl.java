package com.ukim.finki.onlineshopseminarska.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.ukim.finki.onlineshopseminarska.model.dto.ChargeRequest;
import com.ukim.finki.onlineshopseminarska.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${STRIPE_S_KEY}")
    private String secretKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey = this.secretKey;
    }

    @Override
    public Charge pay(ChargeRequest chargeRequest) throws StripeException {
        Map<String, Object> chargeMap =new HashMap<>();
        chargeMap.put("amount",chargeRequest.getAmount());
        chargeMap.put("currency",chargeRequest.getCurrency());
        chargeMap.put("source",chargeRequest.getStripeToken());
        chargeMap.put("description",chargeRequest.getDescription());
        return Charge.create(chargeMap);
    }
}
