package com.techcareer.challenge.controller;

import com.techcareer.challenge.data.dto.CardDto;
import com.techcareer.challenge.exception.BadRequestException;
import com.techcareer.challenge.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/card/")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("add-card")
    public ResponseEntity<Integer> addCard(@RequestBody CardDto cardDto){
        return ResponseEntity.ok(cardService.addCard(cardDto));
    }

    @GetMapping("get-card")
    public ResponseEntity<CardDto> getCard(@RequestParam Integer cardId){
            if (cardId < 0 ){
                throw new BadRequestException("Invalid card ID");
            }
            return ResponseEntity.ok(cardService.getCard(cardId));
    }


    @DeleteMapping("delete-card")
    public void deleteCard(@RequestParam Integer cardId){
        if (cardId < 0 ){
            throw new BadRequestException("Invalid card ID");
        }
         cardService.deleteCard(cardId);
    }

}
