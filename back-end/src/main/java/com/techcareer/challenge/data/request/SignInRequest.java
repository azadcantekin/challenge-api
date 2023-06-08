package com.techcareer.challenge.data.request;

import com.techcareer.challenge.utilities.validators.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    @UniqueEmail
    private String email;
    private String password;
}
