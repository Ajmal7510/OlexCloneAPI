package com.olx.api.olxcloneapi.Service.Imp;

import com.olx.api.olxcloneapi.Entity.Users;
import com.olx.api.olxcloneapi.Model.LoginModel;
import com.olx.api.olxcloneapi.Model.UsersModel;
import com.olx.api.olxcloneapi.Repository.UserRepository;
import com.olx.api.olxcloneapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void createUser(UsersModel usersModel) {
        Users user = new Users();
        user.setUserName(usersModel.getUsername());
        user.setEmail(usersModel.getEmail());
        user.setPhoneNumber(usersModel.getPhoneNumber());
        user.setPassword(usersModel.getPassword());
        userRepository.save(user);

    }

    @Override
    public Users login(LoginModel request) {
        System.out.println("Stage 1 worked");

        Optional<Users> userOptional = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));

        if (userOptional.isPresent()) {
            Users user = userOptional.get();

            if (user.getEmail().equals(request.getEmail()) && user.getPassword().equals(request.getPassword())) {
                System.out.println(user);
                System.out.println(request);
                return user;
            } else {
                System.out.println("It's not working");
                System.out.println(user);
                return null; // or handle the case when login fails appropriately
            }
        } else {
            System.out.println("User not found");
            return null; // or handle the case when user is not found appropriately
        }
    }


}
