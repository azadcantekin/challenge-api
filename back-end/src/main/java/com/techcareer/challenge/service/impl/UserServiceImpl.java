package com.techcareer.challenge.service.impl;

import com.techcareer.challenge.data.dto.UserDto;
import com.techcareer.challenge.data.model.UserModel;
import com.techcareer.challenge.data.request.SignInRequest;
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
        UserModel userModel = converterService.convertToType(userDto, UserModel.class);
        userRepository.save(userModel);
        return userDto;
    }

    @Override
    public UserDto signIn(SignInRequest signInRequest) {
        UserModel userModel = userRepository.findByEmail(signInRequest.getEmail());
        UserDto userDto = converterService.convertToType(userModel, UserDto.class);
        return userDto;
    }


    @Override
    public UserDto updateUser(Integer id,UserDto updatedUserDto) {
        Optional<UserModel> userModel = userRepository.findById(id);
        UserModel userOptional = userModel.get();
        userOptional.setEmail(updatedUserDto.getEmail());
        userOptional.setPassword(updatedUserDto.getPassword());
        userRepository.save(userOptional);
        return updatedUserDto;
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}
