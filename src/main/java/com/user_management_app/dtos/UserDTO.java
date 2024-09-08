package com.user_management_app.dtos;

import lombok.Data;

@Data
public class UserDTO {

    private Integer userId;
    private String email;
    private String pwd;
    private String pwdUpdated;

}
