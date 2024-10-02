//package com.example.demo.service.serviceImpl;
//
//
//import com.example.demo.entity.UserEntity;
//import com.example.demo.exception.CustomServiceException;
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private UserRepository repository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<UserEntity> user = repository.findByEmail(email);
//        if (user != null) {
//            return org.springframework.security.core.userdetails.User.builder()
//                    .username(user.get().getEmail())
//                    .password(user.get().getPassword())
//                    .build();
//        }
//        throw new UsernameNotFoundException("User not found with Email: " + email);
//    }
//}
//
//
