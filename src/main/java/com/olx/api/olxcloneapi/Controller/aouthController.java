package com.olx.api.olxcloneapi.Controller;

import com.olx.api.olxcloneapi.Entity.Users;
import com.olx.api.olxcloneapi.Model.LoginModel;
import com.olx.api.olxcloneapi.Model.UsersModel;
import com.olx.api.olxcloneapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class aouthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UsersModel usersModel){
        try {
            userService.createUser(usersModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully registered user");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("User already exists");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Please try again later");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginModel request) {

            Users user = userService.login(request);
            if (user != null) {
                // Login successful
                System.out.println("mot kdsfldsfj");
                return ResponseEntity.ok().body(user);
            } else {

                System.out.println("body not equal null");

                return ResponseEntity.ok().body(null);
            }



        }
    @PostMapping("/register")
    public ResponseEntity<String> registor(){
        return ResponseEntity.ok("its work");
    }
    }



