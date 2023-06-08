package com.techcareer.challenge.service;

import com.techcareer.challenge.data.dto.CardDto;

public interface CardService {
    int addCard(CardDto cardDto);
    CardDto getCard(Integer cardId);
    void deleteCard(Integer cardId);
}
