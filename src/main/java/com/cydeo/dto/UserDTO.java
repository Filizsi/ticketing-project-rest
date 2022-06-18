package com.cydeo.dto;

import com.cydeo.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passWord;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //when we get we don't see it, we can post it
    private String confirmPassWord;
    private boolean enabled;
    private String phone;
    private RoleDTO role;
    private Gender gender;

}


