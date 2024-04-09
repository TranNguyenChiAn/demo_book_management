package com.example.demo_book_management.database;

import com.example.demo_book_management.entity.Book;
import com.example.demo_book_management.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository) {

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

            }
        };
    }
}
