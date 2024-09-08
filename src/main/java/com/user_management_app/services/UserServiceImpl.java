package com.user_management_app.services;

import com.user_management_app.dtos.LoginFormDTO;
import com.user_management_app.dtos.RegisterFormDTO;
import com.user_management_app.dtos.ResetPwdFormDTO;
import com.user_management_app.dtos.UserDTO;
import com.user_management_app.entities.CityEntity;
import com.user_management_app.entities.CountryEntity;
import com.user_management_app.entities.StateEntity;
import com.user_management_app.entities.UserEntity;
import com.user_management_app.repositories.CityRepository;
import com.user_management_app.repositories.CountryRepository;
import com.user_management_app.repositories.StateRepository;
import com.user_management_app.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService{

    private CityRepository cityRepo;
    private CountryRepository countryRepo;

    private EmailService emailService;

    public UserServiceImpl(CityRepository cityRepo, CountryRepository countryRepo, EmailService emailService, StateRepository stateRepo, UserRepository userRepo) {
        this.cityRepo = cityRepo;
        this.countryRepo = countryRepo;
        this.emailService = emailService;
        this.stateRepo = stateRepo;
        this.userRepo = userRepo;
    }

    private StateRepository stateRepo;
    private UserRepository userRepo;

    @Override
    public Map<Integer, String> getCountries() {

        Map<Integer,String> countryMap = new HashMap<>();

        List<CountryEntity> allCountry = countryRepo.findAll();

        allCountry.forEach(c-> {countryMap.put(c.getCountryId(),c.getCountryName());});

        return countryMap;
    }

    @Override
    public Map<Integer, String> getStates(Integer countryId) {
        Map<Integer,String> stateMap = new HashMap<>();
        List<StateEntity> stateList = stateRepo.findByCountryId(countryId);

        stateList.forEach(s->{stateMap.put(s.getStateId(),s.getStateName());});

        return stateMap;
    }

    @Override
    public Map<Integer, String> getCities(Integer stateId) {
        Map<Integer,String> cityMap = new HashMap<>();
        List<CityEntity> cityList = cityRepo.findByStateId(stateId);

        cityList.forEach(c->{cityMap.put(c.getCityId(),c.getCityName());});

        return cityMap;
    }

    @Override
    public boolean duplicateEmailCheck(String email) {
        UserEntity emailFound = userRepo.findByEmail(email);
        if(emailFound!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean saveUser(RegisterFormDTO regFormDTO) {
        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(regFormDTO,userEntity);

        CountryEntity country = countryRepo.findById(regFormDTO.getCountryId()).orElse(null);
        userEntity.setCountry(country);

        StateEntity state = stateRepo.findById(regFormDTO.getStateId()).orElse(null);
        userEntity.setState(state);

        CityEntity city = cityRepo.findById(regFormDTO.getCityId()).orElse(null);
        userEntity.setCity(city);

        String password = randomPassword();

        userEntity.setPwd(password);
        userEntity.setPwdUpdated("No");

        UserEntity savedUser = userRepo.save(userEntity);

        if(null!=savedUser.getUserId()){
            String subject="Your Account Created";
            String body="Your Password To Login : "+password;
            String to=regFormDTO.getEmail();
            emailService.sendEmail(subject,body,to);
            return true;
        }
        return false;
    }

    @Override
    public UserDTO login(LoginFormDTO loginFormDTO) {
        UserEntity userEntity = userRepo.findByEmailAndPwd(loginFormDTO.getEmail(), loginFormDTO.getPwd());

        if(userEntity!=null){
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userEntity,userDTO);
            return userDTO;
        }
        return null;
    }

    @Override
    public boolean resetPwd(ResetPwdFormDTO resetPwdDTO) {
        String email = resetPwdDTO.getEmail();
        UserEntity userEntity = userRepo.findByEmail(email);

        //setting new password
        userEntity.setPwd(resetPwdDTO.getNewPwd());
        userEntity.setPwdUpdated("Yes");

        userRepo.save(userEntity);

        return true;
    }

    public String randomPassword(){

        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters ="abcdefghijklmnopqrstuvwxyz";

        String alphabets =upperCaseLetters+lowerCaseLetters;

        Random random = new Random();

        StringBuffer generatedPassword = new StringBuffer();

        for(int i=0; i<5; i++){
            //give any number 0 to 51
            int randoNum = random.nextInt(alphabets.length());
            generatedPassword.append(alphabets.charAt(randoNum));
        }
        return generatedPassword.toString();
    }
}
