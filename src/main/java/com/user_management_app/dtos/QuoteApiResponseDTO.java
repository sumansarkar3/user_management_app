package com.user_management_app.dtos;

import lombok.Data;

@Data
public class QuoteApiResponseDTO {

    private Integer id;
    private String quote;
    private String author;
}
