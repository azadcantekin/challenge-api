package com.techcareer.challenge.repository;

import com.techcareer.challenge.data.model.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenModel , Integer> {
}
