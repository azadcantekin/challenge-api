package com.techcareer.challenge.service;

import com.techcareer.challenge.data.dto.UserDto;
import com.techcareer.challenge.data.request.SignInRequest;


public interface UserService {
    UserDto signUp(UserDto userDto);
    UserDto signIn(SignInRequest signInRequest);
    UserDto updateUser( Integer id, UserDto updatedUserDto);
    String confirmUser(String confirmationToken);
    void deleteUser(Integer userId);

}
