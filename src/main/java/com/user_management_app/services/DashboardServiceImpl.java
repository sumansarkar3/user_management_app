package com.user_management_app.services;

import com.user_management_app.dtos.QuoteApiResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DashboardServiceImpl implements DashboardService{

    private String quoteApiURL="https://dummyjson.com/quotes/random";
    @Override
    public QuoteApiResponseDTO getQuote() {

        RestTemplate rt = new RestTemplate();
        //send HTTP get request and store response into QuoteApiResponseDTO object
        ResponseEntity<QuoteApiResponseDTO> forEntity = rt.getForEntity(quoteApiURL, QuoteApiResponseDTO.class);
        QuoteApiResponseDTO quoteBody = forEntity.getBody();

        return quoteBody;
    }
}
