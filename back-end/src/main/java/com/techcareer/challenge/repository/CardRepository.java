package com.techcareer.challenge.repository;

import com.techcareer.challenge.data.model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CardRepository extends JpaRepository<CardModel, Integer> {

}
