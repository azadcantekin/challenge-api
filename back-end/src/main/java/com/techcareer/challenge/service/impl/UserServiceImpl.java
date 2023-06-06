package com.techcareer.challenge.service.impl;

import com.techcareer.challenge.data.dto.UserDto;
import com.techcareer.challenge.data.model.UserModel;
import com.techcareer.challenge.data.request.SignInRequest;
import com.techcareer.challenge.repository.UserRepository;
import com.techcareer.challenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto signUp(UserDto userDto) {
        UserModel userModel = new UserModel();
        userModel.setEmail(userDto.getEmail());
        userModel.setPassword(userDto.getPassword());
        userModel.setFirstName(userDto.getFirstName());
        userModel.setLastName(userDto.getLastName());
        userRepository.save(userModel);
        return userDto;
    }

    @Override
    public UserDto signIn(SignInRequest signInRequest) {
        UserModel userModel = userRepository.findByEmail(signInRequest.getEmail());
        UserDto userDto = new UserDto();
        userDto.setEmail(userModel.getEmail());
        userDto.setPassword(userDto.getPassword());
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
