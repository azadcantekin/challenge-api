package com.techcareer.challenge.service.impl;

import com.techcareer.challenge.data.dto.CardDto;
import com.techcareer.challenge.data.model.CardModel;
import com.techcareer.challenge.exception.BadRequestException;
import com.techcareer.challenge.exception.ResourceExistsException;
import com.techcareer.challenge.exception.ResourceNotFoundException;
import com.techcareer.challenge.repository.CardRepository;
import com.techcareer.challenge.service.CardService;
import com.techcareer.challenge.utilities.mapper.ModelConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ModelConverterService converterService;

    @Override
    public int addCard(CardDto cardDto) {
        //Check if card is null .
        Optional<CardDto> optionalCardDto = Optional.ofNullable(cardDto);
        if (optionalCardDto.isEmpty()){
            throw new BadRequestException("Bad request ");
        }

        CardModel cardModel = converterService.convertToType(optionalCardDto.get(), CardModel.class);
        //check if card already exists
        if (cardDto.getCardDumber().equals(cardModel.getCardNumber())){
            throw new ResourceExistsException("Card already exists",null);
        }
        //save data to db .
        cardRepository.save(cardModel);
        return cardModel.getId();

    }

    @Override
    public CardDto getCard(Integer cardId) {
        //fetch card or throw an exception
        Optional<CardModel> cardModel = Optional.ofNullable(cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found", null)));
        //mapping model to dto
        return converterService.convertToType(cardModel, CardDto.class);
    }

    @Override
    public void deleteCard(Integer cardId) {
        try{
            cardRepository.deleteById(cardId);
        }
        catch (ResourceNotFoundException exception){
            throw new ResourceNotFoundException("Card not found",null);
        }
    }
}
