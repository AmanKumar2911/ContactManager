package com.first.contactmanager.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.first.contactmanager.Entites.User;

@Repository
public interface UserRepositry extends JpaRepository<User,Integer> {
    
}
