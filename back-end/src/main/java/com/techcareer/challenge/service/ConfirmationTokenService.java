package com.techcareer.challenge.service;

import com.techcareer.challenge.data.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);
    Optional<ConfirmationToken> getToken(String confirmationToken);
    int setConfirmedAt(String confirmationToken);
}
