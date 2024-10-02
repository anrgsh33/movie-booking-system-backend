//package com.example.demo.service.serviceImpl;
//
//import com.example.demo.dto.UserRequestDto;
//import com.example.demo.entity.UserEntity;
//import com.example.demo.exception.CustomServiceException;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.response.ResponseModel;
//import com.example.demo.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;
//
//    public ResponseModel register(UserRequestDto user){
//        if(userRepository.existsByEmail(user.getEmail())){
//            throw new CustomServiceException("User Already Exists");
//        }
//
//        UserEntity userEntity = UserEntity.builder()
//                .name(user.getName())
//                .email(user.getEmail())
//                .password(passwordEncoder.encode(user.getPassword()))
//                .build();
//        log.info("password"+userEntity.getPassword());
//
//        userRepository.save(userEntity);
//
//        ResponseModel response=new ResponseModel();
//        response.setMessage("User Registered successfully");
//        response.setStatusCode(HttpStatus.CREATED);
//
//        return response;
//
//    }
//}
