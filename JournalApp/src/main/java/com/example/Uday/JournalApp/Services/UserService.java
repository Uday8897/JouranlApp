package com.example.Uday.JournalApp.Services;

import com.example.Uday.JournalApp.DTOS.LogInResponseDTO;
import com.example.Uday.JournalApp.Entities.User;
import com.example.Uday.JournalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service public class UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    JWTService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    private static  final Logger logger= LoggerFactory.getLogger(UserService.class);
    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
    public List<User> findAll(){
        return userRepo.findAll();
    }
    public Optional<User> findUserById(ObjectId userId){
        return userRepo.findById(userId);}
    public void saveUser(User user){
        user.setPassWord(encoder.encode(user.getPassWord()));
        userRepo.save(user);

    }
    public void saveOldUser(User user){
        userRepo.save(user);
    }
    public void deleteUser(ObjectId id){
        userRepo.deleteById(id);
    }
    public User findByUserName(String userName){
        return userRepo.findByuserName(userName);
    }

    public LogInResponseDTO verify(User user) throws Exception {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassWord()));
//        return authentication.isAuthenticated()?jwtService.generateToken(user.getUserName()):"fail";
        if(authentication.isAuthenticated()){
            LogInResponseDTO responseDTO=new LogInResponseDTO();
            responseDTO.setToken(jwtService.generateToken(user.getUserName()));
            responseDTO.setRole(user.getRoles());
            return responseDTO;
        }
        throw  new Exception();
    }

}
