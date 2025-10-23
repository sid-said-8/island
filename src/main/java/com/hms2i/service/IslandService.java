package com.hms2i.service;


import com.hms2i.entity.Islanduser;
import com.hms2i.payload.LoginDto;
import com.hms2i.repository.IslanduserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IslandService {

    private IslanduserRepository islanduserRepository;
    private JWTService jwtService;

    public IslandService(IslanduserRepository islanduserRepository, JWTService jwtService) {
        this.islanduserRepository = islanduserRepository;
        this.jwtService = jwtService;
    }

    public Islanduser signUpNewUser(Islanduser islanduser) {

        String encryptedPassword = BCrypt.hashpw(islanduser.getPassword(), BCrypt.gensalt(5));
        islanduser.setPassword(encryptedPassword);
        Islanduser savedNewUser = islanduserRepository.save(islanduser);
        return savedNewUser;
    }

    public String verifyLogin(LoginDto loginDto) {
        Optional<Islanduser> opUsername = islanduserRepository.findByUsername(loginDto.getUsername());
        if(opUsername.isPresent()){
            Islanduser islanduser = opUsername.get();
            if(BCrypt.checkpw(loginDto.getPassword(),islanduser.getPassword())){
                //generate token

                String token = jwtService.generateToken(islanduser.getUsername());
                return token;
            }
            return null;
        }
            return null;
    }
}
