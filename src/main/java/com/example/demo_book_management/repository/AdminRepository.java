package com.example.demo_book_management.repository;

import com.example.demo_book_management.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin>  findByName(String name);
}
