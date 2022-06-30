package fr.epitech.cash.manager.controller;

import fr.epitech.cash.manager.entity.StripeChargeEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;

@RestController
// All request in this class begin with the requestMapping value
@RequestMapping("/api/auth")
class PaymentController {

    // Get the stripe api secret key present in application.properties
    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    @PostMapping("/charges")
    public ResponseEntity<String> createCharge(@RequestBody StripeChargeEntity stripeChargeEntity) {
        try {
            // creating the charge
            Stripe.apiKey = API_SECRET_KEY;
            Charge charge = Charge.create(stripeChargeEntity.getCharge());
            return new ResponseEntity<String>("Success", HttpStatus.CREATED);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all charges from stripe account
    @GetMapping("/charges")
    public String getCharges() {
        final String uri = "https://api.stripe.com/v1/charges ";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("X-COM-PERSIST", "NO");
        headers.set("X-COM-LOCATION", "USA");
        // Sent the api key in base64
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(API_SECRET_KEY.getBytes()));

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}