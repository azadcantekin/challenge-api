package com.techcareer.challenge.repository;

import com.techcareer.challenge.data.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken , Long> {

    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
    @Query(value = "UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.confirmation_token = ?1" , nativeQuery = true)
    int updateConfirmedAt(String confirmationToken,
                          LocalDateTime confirmedAt);
}
