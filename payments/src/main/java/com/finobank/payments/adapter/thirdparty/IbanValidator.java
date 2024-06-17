package com.finobank.payments.adapter.thirdparty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class IbanValidator {
    private final static String VALIDATOR_URI = "https://openiban.com/validate/";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public IbanValidator() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public boolean isValid(String iban) {
        if (iban == null || iban.isEmpty()) return false;
        
        ResponseEntity<String> response = restTemplate.getForEntity(VALIDATOR_URI + iban, String.class);
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            return rootNode.path("valid").asBoolean();
        } catch (Exception e) {
            log.warn("Error validating IBAN: {}", iban, e);
            return false;
        }
    }
}
