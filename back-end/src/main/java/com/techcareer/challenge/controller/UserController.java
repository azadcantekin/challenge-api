package com.techcareer.challenge.controller;

import com.techcareer.challenge.data.dto.UserDto;
import com.techcareer.challenge.data.request.SignInRequest;
import com.techcareer.challenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.signUp(userDto));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(userService.signIn(signInRequest));
    }

    @PatchMapping("/update-user")
    public ResponseEntity<?> updateUser( @RequestParam Integer userId ,@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(userId, userDto ));
    }

    @DeleteMapping("/delete-user")
    public void deleteUser(@RequestParam Integer userId){
       userService.deleteUser(userId);
    }

}
