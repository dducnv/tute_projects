package com.example.tute_backend.api;

import com.example.tute_backend.config.TokenProvider;
import com.example.tute_backend.dto.*;
import com.example.tute_backend.entity.User;
import com.example.tute_backend.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import static com.example.tute_backend.config.constant.routes.apiv1.AuthRoutes.*;
import static com.example.tute_backend.config.constant.routes.apiv1.MainRoutes.PREFIX_API_V1;

@RestController
@RequestMapping(PREFIX_API_V1)
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = PREFIX_MY_INFO, method = RequestMethod.GET)
    public ResponseEntity<?> myInfo() {
        User user = userService.getUserFromToken();
        return  ResponseEntity.ok(user);
    }

}
