package com.ukim.finki.onlineshopseminarska.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.ukim.finki.onlineshopseminarska.model.dto.ChargeRequest;

public interface PaymentService {
    Charge pay(ChargeRequest chargeRequest) throws StripeException;
}
