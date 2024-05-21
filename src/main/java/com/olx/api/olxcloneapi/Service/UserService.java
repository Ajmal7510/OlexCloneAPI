package com.olx.api.olxcloneapi.Service;

import com.olx.api.olxcloneapi.Entity.Users;
import com.olx.api.olxcloneapi.Model.LoginModel;
import com.olx.api.olxcloneapi.Model.UsersModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void createUser(UsersModel usersModel);

    Users login(LoginModel request);
}
