package com.example.demo.service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.response.ResponseModel;

public interface UserService {
    ResponseModel register(UserRequestDto user);
}
