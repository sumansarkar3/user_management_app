package com.user_management_app.controllers;

import com.user_management_app.dtos.*;
import com.user_management_app.services.DashboardService;
import com.user_management_app.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class UserController {

    private UserService userService;
    private DashboardService dashboardService;

    public UserController(UserService userService, DashboardService dashboardService) {
        this.userService = userService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/register")
    public String loadRegisterPage(Model model){
        Map<Integer, String> countriesMap = userService.getCountries();
        model.addAttribute("countries",countriesMap);

        RegisterFormDTO registerFormDTO = new RegisterFormDTO();
        model.addAttribute("registerForm",registerFormDTO);


        return "register";
    }

    @GetMapping("/states/{countryId}")
    @ResponseBody
    public Map<Integer, String> getStates(@PathVariable Integer countryId){
        Map<Integer, String> statesMap = userService.getStates(countryId);
        return statesMap;
    }

    @GetMapping("/cities/{stateId}")
    @ResponseBody
    public Map<Integer, String> getCities(@PathVariable Integer stateId){
        Map<Integer, String> citiesMap = userService.getCities(stateId);
        return citiesMap;
    }

    @PostMapping("/register")
    public String handleRegistration(RegisterFormDTO registerFormDTO,Model model){
        boolean status = userService.duplicateEmailCheck(registerFormDTO.getEmail());
        if (status){
            model.addAttribute("emsg","Duplicate Email Found");
        }else{
            boolean userSave = userService.saveUser(registerFormDTO);
            if(userSave){
                model.addAttribute("smsg","Registration Success!, Please check your email...");
            }else{
                model.addAttribute("emsg","Registration Failed!");
            }
        }
        model.addAttribute("registerForm",new RegisterFormDTO());
        model.addAttribute("countries",userService.getCountries());

        return "register";
    }

    @GetMapping("/")
    public String index(Model model){
        LoginFormDTO loginFormDTO = new LoginFormDTO();

        model.addAttribute("loginForm",loginFormDTO);
        return "login";
    }

    @PostMapping("/login")
    public String handleUserLogin(LoginFormDTO loginFormDTO,Model model){

        UserDTO userDTO = userService.login(loginFormDTO);
        if(userDTO==null){
            model.addAttribute("emsg","Invalid Credentials");
            model.addAttribute("loginForm",new LoginFormDTO());
        }else{
            String pwdUpdated = userDTO.getPwdUpdated();
            if("yes".equalsIgnoreCase(pwdUpdated)){
                //Display dashboard
                return "redirect:dashboard";
            }else{
                //Display reset password page
                return "redirect:rest-pwd-page?email="+userDTO.getEmail();
            }
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){

        QuoteApiResponseDTO quoteResponse = dashboardService.getQuote();
        model.addAttribute("quote",quoteResponse);
        return "dashboard";
    }

    @GetMapping("/rest-pwd-page")
    public String loadResetPwdPage(@RequestParam("email") String email,Model model){

        ResetPwdFormDTO resetPwdFormDTO = new ResetPwdFormDTO();
        resetPwdFormDTO.setEmail(email);

        model.addAttribute("resetPwd",resetPwdFormDTO);

        return "resetPwd";
    }

    @PostMapping("/resetPwd")
    public String handleResetPwdPage(ResetPwdFormDTO resetPwdFormDTO,Model model){

        boolean password = userService.resetPwd(resetPwdFormDTO);
        if(password){
            return "redirect:dashboard";
        }
        return "resetPwd";
    }
}
