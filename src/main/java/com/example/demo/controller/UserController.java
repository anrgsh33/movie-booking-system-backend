//package com.example.demo.controller;
//
//import com.example.demo.dto.UserLoginDto;
//import com.example.demo.dto.UserRequestDto;
//import com.example.demo.response.ResponseModel;
//import com.example.demo.service.UserService;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Validated
//@Slf4j
//@RestController
//@RequestMapping("/api/auth")
//public class UserController {
//    @Autowired
//    private UserService userService;
//
//    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResponseModel> registerUser(@RequestBody @Valid UserRequestDto user){
//        log.info("password:"+user.getPassword());
//        ResponseModel response=userService.register(user);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
////    @PostMapping(path="/login",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
////    public ResponseEntity<ResponseModel>login(@RequestBody @Valid UserLoginDto user){
////
////    }
//}
