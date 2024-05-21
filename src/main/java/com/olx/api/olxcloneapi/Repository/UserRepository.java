package com.olx.api.olxcloneapi.Repository;

import com.olx.api.olxcloneapi.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByEmail(String email);
}
