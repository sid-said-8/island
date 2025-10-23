package com.hms2i.controller;


import com.hms2i.entity.Islanduser;
import com.hms2i.payload.LoginDto;
import com.hms2i.payload.TokenDto;
import com.hms2i.repository.IslanduserRepository;
import com.hms2i.service.IslandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/island")
public class IslanduserController {

    private IslandService islandService;

    private IslanduserRepository islanduserRepository;

    public IslanduserController(IslandService islandService, IslanduserRepository islanduserRepository) {
        this.islandService = islandService;
        this.islanduserRepository = islanduserRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?>SigInUser(@RequestBody Islanduser islanduser){
        if(islanduserRepository.findByUsername(islanduser.getUsername()).isPresent()){
            return new ResponseEntity<>("Username Already Taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(islanduserRepository.findByEmail(islanduser.getEmail()).isPresent()){
            return new ResponseEntity<>("Email Already exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Islanduser savedNewUser = islandService.signUpNewUser(islanduser);
        return new ResponseEntity<>(savedNewUser,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginDto loginDto){
        String token = islandService.verifyLogin(loginDto);
        if(token!=null){
            TokenDto tokenDto=new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("invalid username/password",HttpStatus.FORBIDDEN);
        }
    }
}
