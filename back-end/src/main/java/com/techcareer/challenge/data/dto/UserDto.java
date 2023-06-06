package com.techcareer.challenge.data.dto;

import com.techcareer.challenge.data.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<Role> roleList;
}
