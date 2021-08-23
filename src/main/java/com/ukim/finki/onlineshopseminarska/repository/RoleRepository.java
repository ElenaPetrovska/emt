package com.ukim.finki.onlineshopseminarska.repository;

import com.ukim.finki.onlineshopseminarska.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
