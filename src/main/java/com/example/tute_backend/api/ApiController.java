package com.example.tute_backend.api;

import com.example.tute_backend.entity.User;
import com.example.tute_backend.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.example.tute_backend.config.constant.routes.apiv1.MainRoutes.PREFIX_API_V1;

@RestController
@RequestMapping(PREFIX_API_V1)
@RequiredArgsConstructor
public class ApiController {
    @Autowired
    UserServiceImpl userService;
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<?> myTest() {

        return  ResponseEntity.ok("Hello Test");
    }

}
