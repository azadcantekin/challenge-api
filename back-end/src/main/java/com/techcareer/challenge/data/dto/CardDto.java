package com.techcareer.challenge.data.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private int id ;
    private String cardDumber;
    private String expirationDate;
    private String cvv;
    private String cardType;

}
