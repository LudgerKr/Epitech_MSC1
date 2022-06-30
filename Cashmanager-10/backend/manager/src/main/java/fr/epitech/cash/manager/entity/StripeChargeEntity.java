package fr.epitech.cash.manager.entity;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

// Create automatically all getters
@Getter
// Create automatically all setter
@Setter
public class StripeChargeEntity {
    private long amount;
    private String receipt_email;
    private String source;
    private String currency;

    public StripeChargeEntity(long amount, String receipt_email) {
        this.amount = amount;
        this.source = "tok_visa";
        this.currency = "eur";
        this.receipt_email = receipt_email;
    }

    public Map<String, Object> getCharge() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", this.amount);
        params.put("receipt_email", this.receipt_email);
        params.put("currency", this.currency);
        // source should obtained with Stripe.js
        params.put("source", this.source);
        return params;
    }
}