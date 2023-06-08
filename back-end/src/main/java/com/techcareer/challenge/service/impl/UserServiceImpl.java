package com.techcareer.challenge.service.impl;

import com.techcareer.challenge.data.dto.UserDto;
import com.techcareer.challenge.data.model.UserModel;
import com.techcareer.challenge.data.request.SignInRequest;
import com.techcareer.challenge.exception.BadRequestException;
import com.techcareer.challenge.exception.MaxLoginAttemptsExceededException;
import com.techcareer.challenge.exception.ResourceExistsException;
import com.techcareer.challenge.exception.ResourceNotFoundException;
import com.techcareer.challenge.repository.UserRepository;
import com.techcareer.challenge.service.UserService;
import com.techcareer.challenge.utilities.mapper.ModelConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelConverterService converterService;

    @Override
    public UserDto signUp(UserDto userDto) {
        boolean existsUser = userRepository.existsByEmail(userDto.getEmail());
        if (existsUser) {
            throw new ResourceExistsException("User exists",null);
        }
        UserModel savedUser = userRepository.save(converterService.convertToType(userDto , UserModel.class));
        return userDto;
    }

    @Override
    public UserDto signIn(SignInRequest signInRequest) {
        //Fetch data from db
        Optional<UserModel> optionalUserModel = Optional.ofNullable(userRepository.findByEmail(signInRequest.getEmail()));
        //check if null
        UserModel userModel = optionalUserModel
                .orElseThrow(() -> new ResourceNotFoundException("User not found",null));
        UserDto userDto = converterService.convertToType(userModel, UserDto.class);
        //check if password wrong and set fail login attempt
        if (!signInRequest.getPassword().equals(userModel.getPassword())){
            userModel.setFailedLoginAttempt(userModel.getFailedLoginAttempt() + 1);
            userRepository.saveAndFlush(userModel);
        }
        //check fail number and set account activity
        if (userModel.getFailedLoginAttempt() >= 5 ){
            userModel.setActive(false);
            throw new MaxLoginAttemptsExceededException("Maximum login attempts exceeded. Your account is now inactive.", null);
        }
        return userDto;
    }


    @Override
    public UserDto updateUser(Integer id,UserDto updatedUserDto) {
        //fetch data from db or throw exception
        Optional<UserModel> userOptional = Optional.ofNullable(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found", null)));

        if (userOptional.isPresent()){
            // update and save model
            UserModel userModel = userOptional.get();
            userModel.setEmail(updatedUserDto.getEmail());
            userModel.setPassword(updatedUserDto.getPassword());
            userModel.setFirstName(updatedUserDto.getFirstName());
            userModel.setLastName(updatedUserDto.getLastName());
            userModel.setActive(updatedUserDto.isActive());
            userRepository.save(userModel);
            return updatedUserDto;

        }
        throw new BadRequestException("Bad request");

    }

    @Override
    public void deleteUser(Integer userId) {
      try{
          userRepository.deleteById(userId);
      }
      catch (ResourceNotFoundException exception){
          throw new ResourceNotFoundException("User not found" ,exception.getCause());
      }
    }
}
