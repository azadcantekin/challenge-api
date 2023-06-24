package com.techcareer.challenge.service.impl;

import com.techcareer.challenge.config.security.JwtService;
import com.techcareer.challenge.data.dto.UserDto;
import com.techcareer.challenge.data.model.ConfirmationToken;
import com.techcareer.challenge.data.model.TokenModel;
import com.techcareer.challenge.data.model.UserModel;
import com.techcareer.challenge.data.request.SignInRequest;
import com.techcareer.challenge.exception.BadRequestException;
import com.techcareer.challenge.exception.MaxLoginAttemptsExceededException;
import com.techcareer.challenge.exception.ResourceExistsException;
import com.techcareer.challenge.exception.ResourceNotFoundException;
import com.techcareer.challenge.repository.TokenRepository;
import com.techcareer.challenge.repository.UserRepository;
import com.techcareer.challenge.service.ConfirmationTokenService;
import com.techcareer.challenge.service.UserService;
import com.techcareer.challenge.utilities.mail.MailService;
import com.techcareer.challenge.utilities.mapper.ModelConverterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelConverterService converterService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final MailService mailService;
    private final ConfirmationTokenService confirmationTokenService;

    private static final String URI = "http://localhost:8080/api/v1/user/confirm?token=";



    @Override
    public UserDto signUp(UserDto userDto) {
        boolean existsUser = userRepository.existsByEmail(userDto.getEmail());
        if (existsUser) {
            log.error("User exists");
            throw new ResourceExistsException("User exists", null);

        }
        UserModel userModel = converterService.convertToType(userDto, UserModel.class);
        userModel.setPassword(passwordEncoder.encode(userDto.getPassword()));
        //Create and save token
        TokenModel tokenModel = new TokenModel();
        tokenModel.setToken(jwtService.generateToken(userModel));
        tokenRepository.save(tokenModel);
        log.info("New token created: {}",tokenModel);

        //Set user token and save User
        userModel.setTokenList(List.of(tokenModel));
        UserModel savedUser =  userRepository.saveAndFlush(userModel);
        log.info("New user registered: {}", userDto);

        //Create and save confirmation token .
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                savedUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        log.info("New confirmation token saved: {}", confirmationToken);

        //Send code to user .
        mailService.sendEmail(userDto.getEmail(),URI + token);

        return userDto;
    }



    @Override
    public UserDto signIn(SignInRequest signInRequest) {
        //Fetch data from db
        Optional<UserModel> optionalUserModel = Optional.ofNullable(userRepository.findByEmail(signInRequest.getEmail()));
        //check if null
        UserModel userModel = optionalUserModel

                .orElseThrow(() -> new ResourceNotFoundException("User not found", null));

        //check if password wrong and set fail login attempt
        if (!signInRequest.getPassword().equals(userModel.getPassword())) {
            log.info("User login failed: {}", signInRequest);
            userModel.setFailedLoginAttempt(userModel.getFailedLoginAttempt() + 1);
            userRepository.saveAndFlush(userModel);
        }
        //check fail number and set account activity
        if (userModel.getFailedLoginAttempt() >= 5) {
            userModel.setActive(false);
            log.error("Failed max login attempt");
            throw new MaxLoginAttemptsExceededException("Maximum login attempts exceeded. Your account is now inactive.", null);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(), signInRequest.getPassword()
        ));

        TokenModel tokenModel = new TokenModel();
        tokenModel.setToken(jwtService.generateToken(userModel));
        userModel.setTokenList(List.of(tokenModel));

        return converterService.convertToType(userModel, UserDto.class);
    }


    @Override
    public UserDto updateUser(Integer id, UserDto updatedUserDto) {
        //fetch data from db or throw exception
        Optional<UserModel> userOptional = Optional.ofNullable(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found", null)));

        if (userOptional.isPresent()) {
            // update and save model
            UserModel userModel = userOptional.get();
            userModel.setEmail(updatedUserDto.getEmail());
            userModel.setPassword(updatedUserDto.getPassword());
            userModel.setFirstName(updatedUserDto.getFirstName());
            userModel.setLastName(updatedUserDto.getLastName());
            userModel.setActive(updatedUserDto.isActive());
            userRepository.save(userModel);
            log.info("User updated: {}", updatedUserDto);
            return updatedUserDto;

        }
        log.error("Bad request");
        throw new BadRequestException("Bad request");

    }

    @Override
    public String confirmUser(String confirmationToken) {
        ConfirmationToken token = confirmationTokenService
                .getToken(confirmationToken)
                .orElseThrow(() ->
                        new ResourceNotFoundException("token not found",null));

        if (token.getConfirmedAt() != null) {
            throw new ResourceExistsException("email already confirmed",null);
        }

        LocalDateTime expiredAt = token.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("token expired");
        }

        confirmationTokenService.setConfirmedAt(confirmationToken);
        userRepository.enableAppUser(
                token.getUserModel().getEmail());
        return "confirmed";
    }

    @Override
    public void deleteUser(Integer userId) {
        try {
            userRepository.deleteById(userId);
            log.info("User deleted with ID: {}", userId);
        } catch (ResourceNotFoundException exception) {
            log.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found", exception.getCause());
        }
    }



}
