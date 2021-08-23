package com.ukim.finki.onlineshopseminarska.repository;

import com.ukim.finki.onlineshopseminarska.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
