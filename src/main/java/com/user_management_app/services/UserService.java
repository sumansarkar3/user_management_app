package com.user_management_app.services;

import com.user_management_app.dtos.LoginFormDTO;
import com.user_management_app.dtos.RegisterFormDTO;
import com.user_management_app.dtos.ResetPwdFormDTO;
import com.user_management_app.dtos.UserDTO;

import java.util.Map;

public interface UserService {

    public Map<Integer,String> getCountries();

    public Map<Integer,String> getStates(Integer countryId);

    public Map<Integer,String> getCities(Integer stateId);

    public boolean duplicateEmailCheck(String email);

    public boolean saveUser(RegisterFormDTO regFormDTO);

    public UserDTO login(LoginFormDTO loginFormDTO);

    public boolean resetPwd(ResetPwdFormDTO resetPwdDTO);

}
