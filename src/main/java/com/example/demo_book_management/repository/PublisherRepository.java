package com.example.demo_book_management.repository;

import com.example.demo_book_management.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    List<Publisher> findByName(String name);
}
