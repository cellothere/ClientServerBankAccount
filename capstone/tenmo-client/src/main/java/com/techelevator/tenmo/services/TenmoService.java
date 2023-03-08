package com.techelevator.tenmo.services;

import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


public class TenmoService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    public BigDecimal getBalance(int accountId) {
        Double balance = null;
        BigDecimal bigDecimalBalance = null;
        try {
            balance = restTemplate.getForObject(API_BASE_URL + "accounts/" + accountId + "/balance", Double.class);
            bigDecimalBalance = BigDecimal.valueOf(balance);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return bigDecimalBalance;
    }
}
