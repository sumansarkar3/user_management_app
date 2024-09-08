package com.user_management_app.dtos;

import lombok.Data;

@Data
public class ResetPwdFormDTO {

    private String email;
    private String oldPwd;
    private String newPwd;
    private String confirmPwd;
}
