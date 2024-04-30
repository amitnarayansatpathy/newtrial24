package com.example.newtrial24;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User getUserByEmail(String email);

    User getUserByName(String name);

    User findByUserID(int userID);


}

