package com.techcareer.challenge.service.impl;

import com.techcareer.challenge.data.model.ConfirmationToken;
import com.techcareer.challenge.repository.ConfirmationTokenRepository;
import com.techcareer.challenge.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.saveAndFlush(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String confirmationToken) {
        return confirmationTokenRepository.findByConfirmationToken(confirmationToken);
    }

    @Override
    public int setConfirmedAt(String confirmationToken) {
        return confirmationTokenRepository.updateConfirmedAt(
                confirmationToken, LocalDateTime.now());
    }
}
